package com.projectigti.irontime.repository

import com.projectigti.irontime.data.db.model.SubscriberEntity
import java.util.Date

interface SubscriberRepository {

    suspend fun insertSubscriber(name: String, email: String, phone: String): Long

    suspend fun updateSubscriber(id: Long, name: String, email: String, phone: String)

    suspend fun deleteSubscriber(id: Long)

    suspend fun getAllSubscribers(): List<SubscriberEntity>

    suspend fun doCheckin(list: List<Date>, id: Long)
}