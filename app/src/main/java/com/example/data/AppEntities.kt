package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking_requests")
data class BookingRequest(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val eventType: String,
    val approxGuests: Int,
    val additionalIntel: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "PENDING ASSEMBLY"
)

@Entity(tableName = "favorite_vendors")
data class FavoriteVendor(
    @PrimaryKey val vendorId: String,
    val vendorName: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_rewards")
data class UserReward(
    @PrimaryKey val id: Int = 1,
    val points: Int = 150,
    val stampsCount: Int = 0,
    val totalSpins: Int = 0,
    val totalCheckIns: Int = 0
)
