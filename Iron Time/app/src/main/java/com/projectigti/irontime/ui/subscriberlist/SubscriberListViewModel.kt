package com.projectigti.irontime.ui.subscriberlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectigti.irontime.R
import com.projectigti.irontime.data.db.model.SubscriberEntity
import com.projectigti.irontime.repository.SubscriberRepository
import com.projectigti.irontime.ui.subscriber.SubscriberViewModel
import kotlinx.coroutines.launch
import java.util.*

class SubscriberListViewModel(
    private val repository: SubscriberRepository
): ViewModel() {
    private val _allSubscribersEvent = MutableLiveData<List<SubscriberEntity>>()
    private val _filterSubscribersEvent = MutableLiveData<List<SubscriberEntity>>()
    val allSubscribersEvent: LiveData<List<SubscriberEntity>>
        get() = _filterSubscribersEvent

    private val _subscriberListStateEventData = MutableLiveData<SubscriberListState>()
    val subscriberListStateEventData: LiveData<SubscriberListState>
        get() = _subscriberListStateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun getSubscribers() = viewModelScope.launch {
        val subscribers = repository.getAllSubscribers()
        _filterSubscribersEvent.postValue(subscribers)
        _allSubscribersEvent.postValue(subscribers)
    }

    fun filterSubscribers(filter: String) {
        val filter = _allSubscribersEvent.value?.filter {
            it.name.contains(filter)
        }
        filter?.let {
            _filterSubscribersEvent.postValue(it)
        }
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
        }
    }

    sealed class SubscriberListState {
        object Checkin : SubscriberListState()
    }

    companion object {
        private val TAG = SubscriberViewModel::class.java.simpleName
    }
}