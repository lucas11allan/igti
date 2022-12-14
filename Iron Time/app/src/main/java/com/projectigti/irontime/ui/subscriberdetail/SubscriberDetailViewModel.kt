package com.projectigti.irontime.ui.subscriberdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectigti.irontime.R
import com.projectigti.irontime.data.db.model.SubscriberEntity
import com.projectigti.irontime.repository.SubscriberRepository
import com.projectigti.irontime.ui.subscriber.SubscriberViewModel
import kotlinx.coroutines.launch

class SubscriberDetailViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {
    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    private val _subscriberEvent = MutableLiveData<SubscriberEntity>()
    val subscriberEvent: LiveData<SubscriberEntity>
        get() = _subscriberEvent

    fun getSubscriber(id: Long) = viewModelScope.launch {
        _subscriberEvent.postValue(repository.getStudent(id))
    }

    fun insertClasses(
        id: Long,
        classes: Int
    ) = viewModelScope.launch {
        try {
            repository.insertClasses(id, classes)
            _messageEventData.value = R.string.classes_added
            getSubscriber(id)

        } catch (ex: Exception) {
            _messageEventData.value = R.string.error
            Log.e(TAG, ex.toString())
        }
    }

    companion object {
        private val TAG = SubscriberViewModel::class.java.simpleName
    }
}