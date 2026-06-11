package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

sealed class Screen {
    object Hub : Screen()
    object Vendors : Screen()
    data class VendorDetail(val vendorId: String, val fromScreen: Screen = Vendors) : Screen()
    object Decide : Screen()
    object Factory : Screen()
    object Rewards : Screen()
}

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    // Composable state-driven Navigation safe-backstack
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Hub)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val screenHistory = mutableListOf<Screen>()

    fun navigateTo(screen: Screen) {
        screenHistory.add(_currentScreen.value)
        _currentScreen.value = screen
    }

    fun navigateBack(): Boolean {
        if (screenHistory.isNotEmpty()) {
            _currentScreen.value = screenHistory.removeAt(screenHistory.size - 1)
            return true
        }
        return false
    }

    // Room Persistent States
    val bookings: StateFlow<List<BookingRequest>> = repository.allBookings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favorites: StateFlow<List<FavoriteVendor>> = repository.allFavorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userReward: StateFlow<UserReward?> = repository.userReward
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Form fields for event booking state holds
    val bookingFormName = MutableStateFlow("")
    val bookingFormType = MutableStateFlow("WEDDING")
    val bookingFormGuests = MutableStateFlow("")
    val bookingFormIntel = MutableStateFlow("")
    val bookingFormStatus = MutableStateFlow<String?>(null) // feedback to user

    // Decider Spin Wheel properties managed in ViewModel
    private val _isSpinning = MutableStateFlow(false)
    val isSpinning = _isSpinning.asStateFlow()

    private val _wheelRotation = MutableStateFlow(0f)
    val wheelRotation = _wheelRotation.asStateFlow()

    private val _spinWinner = MutableStateFlow<Vendor?>(null)
    val spinWinner = _spinWinner.asStateFlow()

    fun submitBooking() {
        val name = bookingFormName.value.trim()
        val type = bookingFormType.value
        val guestsStr = bookingFormGuests.value.trim()
        val intel = bookingFormIntel.value.trim()

        if (name.isEmpty()) {
            bookingFormStatus.value = "ERROR: PLEASE ENTER FULL NAME"
            return
        }
        val guests = guestsStr.toIntOrNull() ?: 100

        viewModelScope.launch {
            repository.addBooking(
                fullName = name,
                eventType = type,
                approxGuests = guests,
                additionalIntel = intel
            )
            // Reset form fields
            bookingFormName.value = ""
            bookingFormGuests.value = ""
            bookingFormIntel.value = ""
            bookingFormStatus.value = "SUCCESS: ASSEMBLY SUBMITTED!"
        }
    }

    fun deleteBooking(id: Int) {
        viewModelScope.launch {
            repository.removeBooking(id)
        }
    }

    fun toggleFavorite(vendorId: String, vendorName: String) {
        viewModelScope.launch {
            val isCurrentlyFav = favorites.value.any { it.vendorId == vendorId }
            if (isCurrentlyFav) {
                repository.removeFavorite(vendorId)
            } else {
                repository.addFavorite(vendorId, vendorName)
            }
        }
    }

    fun earnCheckInStamp(onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val completed = repository.addStamp()
            onComplete(completed)
        }
    }

    fun claimDrinkReward(onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.redeemStampsPrize()
            onSuccess(success)
        }
    }

    fun addFreePoints(amount: Int) {
        viewModelScope.launch {
            repository.addPoints(amount)
        }
    }

    fun startSpinWheel() {
        if (_isSpinning.value) return

        viewModelScope.launch {
            _isSpinning.value = true
            _spinWinner.value = null
            
            // Randomly spin between 3 and 6 full rotations plus a random offset
            val fullRotations = Random.nextInt(3, 7)
            val randomDegrees = Random.nextInt(0, 360)
            val totalSpinDegrees = (fullRotations * 360f) + randomDegrees
            
            val initialRotation = _wheelRotation.value % 360f
            val targetRotation = _wheelRotation.value + totalSpinDegrees
            
            // Simple animation loop inside a coroutine
            val durationMs = 4000
            val steps = 80
            val delayPerStep = durationMs / steps
            
            for (i in 1..steps) {
                // Easing formula (Cubic ease out)
                val t = i.toFloat() / steps
                val easedT = 1f - (1f - t) * (1f - t) * (1f - t)
                _wheelRotation.value = initialRotation + (targetRotation - initialRotation) * easedT
                delay(delayPerStep.toLong())
            }

            // Determine winner based on rotation
            val finalRotationNormalized = (_wheelRotation.value % 360f + 360f) % 360f
            
            // 10 Segments of 36 degrees each
            // Arrow is at the top (0 degrees / 360 degrees).
            // A clockwise rotation shifts the segment indices counter-clockwise relative to the top arrow.
            // Segment 0 (index 0) starts centered at -18 to +18, so segment centers are at i * 36.
            val segmentIndex = (((360f - finalRotationNormalized + 18f) % 360f) / 36f).toInt() % 10
            
            // Let's grab the actual vendors from segment sequence
            val spinVendorSequence = listOf(
                "twisted-gingers",
                "pa-general", // maps to "PA GENERAL & WANNA SPOON"
                "twisted-gingers", // dummy segment / or top dog
                "pa-general",      // dummy segment
                "top-dog",         // Top Dog Cocktails
                "marys-chicken",   // Mary's Chicken
                "double-down",     // Double Down
                "luckys-roadside", // Lucky's Roadside
                "char-lobster-rolls", // Char Lobster
                "pho-mi-please"    // Pho Mi Please
            )
            
            val winnerId = spinVendorSequence.getOrElse(segmentIndex) { "twisted-gingers" }
            _spinWinner.value = VendorData.getVendorById(winnerId)
            
            repository.incrementSpins()
            _isSpinning.value = false
        }
    }
}

class MainViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
