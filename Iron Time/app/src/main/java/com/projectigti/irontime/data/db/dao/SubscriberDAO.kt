package com.projectigti.irontime.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.projectigti.irontime.data.db.model.SubscriberEntity
import java.util.Date

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun insert(subscriber: SubscriberEntity): Long

    @Query("UPDATE students SET name = :name,  email = :email, phone = :phone WHERE id = :id")
    suspend fun update(id: Long, name: String, email: String, phone: String)

    @Query("DELETE FROM students WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM students")
    suspend fun getAll(): List<SubscriberEntity>

    @Query("UPDATE students SET checkins = :list, classes = classes - 1 WHERE id = :id")
    suspend fun doCheckin(list: List<Date>, id: Long)

}