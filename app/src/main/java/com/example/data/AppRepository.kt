package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AppRepository(private val appDao: AppDao) {

    val allBookings: Flow<List<BookingRequest>> = appDao.getAllBookingsFlow()
    val allFavorites: Flow<List<FavoriteVendor>> = appDao.getAllFavoritesFlow()
    val userReward: Flow<UserReward?> = appDao.getUserRewardFlow()

    // Bookings
    suspend fun addBooking(fullName: String, eventType: String, approxGuests: Int, additionalIntel: String) {
        val booking = BookingRequest(
            fullName = fullName,
            eventType = eventType,
            approxGuests = approxGuests,
            additionalIntel = additionalIntel
        )
        appDao.insertBooking(booking)
    }

    suspend fun removeBooking(id: Int) {
        appDao.deleteBooking(id)
    }

    // Favorites
    suspend fun addFavorite(vendorId: String, vendorName: String) {
        appDao.addFavorite(FavoriteVendor(vendorId = vendorId, vendorName = vendorName))
    }

    suspend fun removeFavorite(vendorId: String) {
        appDao.removeFavorite(vendorId)
    }

    // Rewards Profile Management
    private suspend fun getOrCreateReward(): UserReward {
        return appDao.getUserReward() ?: UserReward(points = 150, stampsCount = 0).also {
            appDao.insertUserReward(it)
        }
    }

    suspend fun addPoints(amount: Int) {
        val current = getOrCreateReward()
        val updated = current.copy(points = current.points + amount)
        appDao.insertUserReward(updated)
    }

    suspend fun incrementSpins() {
        val current = getOrCreateReward()
        // Spin increases points slightly too! (e.g. 10 points)
        val updated = current.copy(
            totalSpins = current.totalSpins + 1,
            points = current.points + 10
        )
        appDao.insertUserReward(updated)
    }

    suspend fun addStamp(): Boolean {
        val current = getOrCreateReward()
        val nextStamps = current.stampsCount + 1
        return if (nextStamps <= 10) {
            val updated = current.copy(
                stampsCount = nextStamps,
                points = current.points + 20, // Add 20 points per check-in stamp!
                totalCheckIns = current.totalCheckIns + 1
            )
            appDao.insertUserReward(updated)
            nextStamps == 10 // returns true if card has been fully filled!
        } else {
            false
        }
    }

    suspend fun redeemStampsPrize(): Boolean {
        val current = getOrCreateReward()
        return if (current.stampsCount >= 10) {
            // Spend stamps to get reward (like a free local brew/soda or 100 extra points!)
            val updated = current.copy(
                stampsCount = 0,
                points = current.points + 100 // Reward with 100 bonus points!
            )
            appDao.insertUserReward(updated)
            true
        } else {
            false
        }
    }

    suspend fun clearStamps() {
        val current = getOrCreateReward()
        appDao.insertUserReward(current.copy(stampsCount = 0))
    }
}
