package com.bigstep.whatsappredesign.ui.chats

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigstep.hiltmindorks.utils.Resource
import com.bigstep.whatsappredesign.data.model.message.TotalMessage
import com.bigstep.whatsappredesign.data.repository.DataRepository
import com.bigstep.whatsappredesign.utils.NetworkHelper
import kotlinx.coroutines.launch

class ChatsViewModel @ViewModelInject constructor(
    private val userRepository: DataRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _messages = MutableLiveData<Resource<List<TotalMessage>>>()
    val messages: LiveData<Resource<List<TotalMessage>>>
        get() = _messages

    init {
        fetchMessages()
    }

    private fun fetchMessages() {
        viewModelScope.launch {
            _messages.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                userRepository.getMessages().let {
                    Log.v("ChatsViewModelLogs", it.body().toString())
                    if (it.isSuccessful) {
                        _messages.postValue(Resource.success(it.body()!!.body.msgList))
                    } else _messages.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _messages.postValue(Resource.error("No internet connection", null))
        }
    }
}