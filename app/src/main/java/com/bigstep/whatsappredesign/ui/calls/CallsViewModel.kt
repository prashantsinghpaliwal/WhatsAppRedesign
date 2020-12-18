package com.bigstep.whatsappredesign.ui.calls

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigstep.hiltmindorks.utils.Resource
import com.bigstep.whatsappredesign.data.model.call.CallObject
import com.bigstep.whatsappredesign.data.repository.DataRepository
import com.bigstep.whatsappredesign.utils.NetworkHelper
import kotlinx.coroutines.launch

class CallsViewModel @ViewModelInject constructor(
    private val userRepository: DataRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _calls = MutableLiveData<Resource<List<CallObject>>>()
    val calls: LiveData<Resource<List<CallObject>>>
        get() = _calls

    init {
        fetchCalls()
    }

    private fun fetchCalls() {
        viewModelScope.launch {
            _calls.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                userRepository.getCallList().let {
                    Log.v("ChatsViewModelLogs", it.body().toString())
                    if (it.isSuccessful) {
                        _calls.postValue(Resource.success(it.body()!!.body.callList))
                    } else _calls.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _calls.postValue(Resource.error("No internet connection", null))
        }
    }
}