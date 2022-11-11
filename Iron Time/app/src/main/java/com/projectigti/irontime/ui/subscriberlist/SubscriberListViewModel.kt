package com.projectigti.irontime.ui.subscriberlist

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
import java.util.Date

class SubscriberListViewModel(
    private val repository: SubscriberRepository
): ViewModel() {
    private val _allSubscribersEvent = MutableLiveData<List<SubscriberEntity>>()
    val allSubscribersEvent: LiveData<List<SubscriberEntity>>
        get() = _allSubscribersEvent

    private val _subscriberListStateEventData = MutableLiveData<SubscriberListViewModel.SubscriberListState>()
    val subscriberListStateEventData: LiveData<SubscriberListViewModel.SubscriberListState>
        get() = _subscriberListStateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun getSubscribers() = viewModelScope.launch {
        _allSubscribersEvent.postValue(repository.getAllSubscribers())
    }

    fun doCheckin(list: List<Date>, id: Long) = viewModelScope.launch {
        try {
            if (id > 0) {
                repository.doCheckin(list, id)
                _subscriberListStateEventData.value = SubscriberListState.Checkin
                _messageEventData.value = R.string.checkin_ok
            }
        } catch (ex: Exception) {
            _messageEventData.value = R.string.error
            Log.e(SubscriberListViewModel.TAG, ex.toString())
        }
    }

    sealed class SubscriberListState {
        object Checkin : SubscriberListState()
    }

    companion object {
        private val TAG = SubscriberViewModel::class.java.simpleName
    }
}