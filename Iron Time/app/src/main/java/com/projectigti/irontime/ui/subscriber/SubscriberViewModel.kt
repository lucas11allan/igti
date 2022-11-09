package com.projectigti.irontime.ui.subscriber

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectigti.irontime.R
import com.projectigti.irontime.repository.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {

    private val _subscriberStateEventData = MutableLiveData<SubscriberState>()
    val subscriberStateEventData: LiveData<SubscriberState>
        get() = _subscriberStateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun addOrUpdateSubscriber(name: String, email: String, phone: String, id: Long = 0) {
        if (id > 0) {
            updateSubscriber(name, email,phone, id)
        } else {
            insertSubscriber(name, email, phone)
        }
    }

    private fun updateSubscriber(name: String, email: String, phone: String, id: Long) = viewModelScope.launch {
        try {
            repository.updateSubscriber(id, name, email, phone)
            _subscriberStateEventData.value = SubscriberState.Updated
            _messageEventData.value =  R.string.updated_successfully

        } catch (ex: Exception) {
            _messageEventData.value = R.string.error
            Log.e(TAG, ex.toString())
        }
    }

    private fun insertSubscriber(name: String, email: String, phone: String) = viewModelScope.launch {
        try {
            val id = repository.insertSubscriber(name, email, phone)
            if (id > 0) {
                _subscriberStateEventData.value = SubscriberState.Inserted
                _messageEventData.value = R.string.inserted_succesfully
            }
        } catch (ex: Exception) {
            _messageEventData.value = R.string.error
            Log.e(TAG, ex.toString())
        }
    }

    fun deleteSubscriber(id: Long) = viewModelScope.launch {
        try {
            if (id > 0) {
                repository.deleteSubscriber(id)
                _subscriberStateEventData.value = SubscriberState.Deleted
                _messageEventData.value = R.string.delete_successfully
            }
        } catch (ex: Exception) {
            _messageEventData.value = R.string.error
            Log.e(TAG, ex.toString())
        }
    }

    sealed class SubscriberState {
        object Inserted : SubscriberState()
        object Updated : SubscriberState()
        object Deleted : SubscriberState()
    }

    companion object {
        private val TAG = SubscriberViewModel::class.java.simpleName
    }
}