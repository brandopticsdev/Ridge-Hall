package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Booking Queries
    @Query("SELECT * FROM booking_requests ORDER BY timestamp DESC")
    fun getAllBookingsFlow(): Flow<List<BookingRequest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingRequest)

    @Query("DELETE FROM booking_requests WHERE id = :id")
    suspend fun deleteBooking(id: Int)

    // Favorite Vendor Queries
    @Query("SELECT * FROM favorite_vendors")
    fun getAllFavoritesFlow(): Flow<List<FavoriteVendor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(fav: FavoriteVendor)

    @Query("DELETE FROM favorite_vendors WHERE vendorId = :vendorId")
    suspend fun removeFavorite(vendorId: String)

    // User Rewards Queries
    @Query("SELECT * FROM user_rewards WHERE id = 1 LIMIT 1")
    fun getUserRewardFlow(): Flow<UserReward?>

    @Query("SELECT * FROM user_rewards WHERE id = 1 LIMIT 1")
    suspend fun getUserReward(): UserReward?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserReward(reward: UserReward)
}
