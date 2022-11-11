package com.projectigti.irontime.repository

import com.projectigti.irontime.data.db.dao.SubscriberDAO
import com.projectigti.irontime.data.db.model.SubscriberEntity
import java.util.Date

class DataBaseDataSource(
    private val subscriberDAO: SubscriberDAO
): SubscriberRepository {
    override suspend fun insertSubscriber(name: String, email: String, phone: String): Long {
        val subscriber = SubscriberEntity(
            name = name,
            email = email,
            phone = phone
        )
        return subscriberDAO.insert(subscriber)
    }

    override suspend fun updateSubscriber(id: Long, name: String, email: String, phone: String) {
        val subscriber = SubscriberEntity(
            id = id,
            name = name,
            email = email,
            phone = phone
        )
        subscriberDAO.update(subscriber)
    }

    override suspend fun deleteSubscriber(id: Long) {
        subscriberDAO.delete(id)
    }

    override suspend fun getAllSubscribers(): List<SubscriberEntity> {
        return subscriberDAO.getAll()
    }

    override suspend fun doCheckin(list: List<Date>, id: Long) {
        subscriberDAO.doCheckin(list, id)
    }
}