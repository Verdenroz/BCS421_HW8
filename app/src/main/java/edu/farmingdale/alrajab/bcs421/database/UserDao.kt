package edu.farmingdale.alrajab.bcs421.database

import androidx.room.*
import androidx.room.OnConflictStrategy.*

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT uid FROM users WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Int

    @Insert
    fun insertAll(vararg users: User)

    @Insert(onConflict = REPLACE)
    fun addUser(user: User): Long

    @Query("SELECT * FROM users WHERE uid = :id")
    fun getUser(id: Long): User?

    @Delete
    fun delete(user: User)

    @Query("UPDATE users SET first_name = :first, last_name = :last WHERE uid = :id")
    fun updateName(first: String, last: String, id: Int)
}