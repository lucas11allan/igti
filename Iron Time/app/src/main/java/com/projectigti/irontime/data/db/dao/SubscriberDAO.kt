package com.projectigti.irontime.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.projectigti.irontime.data.db.model.SubscriberEntity

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun insert(subscriber: SubscriberEntity): Long

    @Update
    suspend fun update(subscriber: SubscriberEntity)

    @Query("DELETE FROM subscriber WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM subscriber")
    suspend fun deleteAll()

    @Query("SELECT * FROM subscriber")
    suspend fun getAll(): List<SubscriberEntity>
}