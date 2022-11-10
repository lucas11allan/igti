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
import com.projectigti.irontime.ui.subscriberlist.SubscriberListViewModel
import kotlinx.coroutines.launch

class SubscriberDetailViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {

}