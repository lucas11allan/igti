package com.projectigti.irontime.repository

import com.projectigti.irontime.data.db.model.SubscriberEntity

interface SubscriberRepository {

    suspend fun insertSubscriber(name: String, email: String, phone: String): Long

    suspend fun updateSubscriber(id: Long, name: String, email: String, phone: String)

    suspend fun deleteSubscriber(id: Long)

    suspend fun getAllSubscribers(): List<SubscriberEntity>
}