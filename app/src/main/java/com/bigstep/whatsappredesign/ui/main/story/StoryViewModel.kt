package com.bigstep.whatsappredesign.ui.main.story

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigstep.hiltmindorks.utils.Resource
import com.bigstep.whatsappredesign.data.model.story.StoryObject
import com.bigstep.whatsappredesign.data.repository.DataRepository
import com.bigstep.whatsappredesign.utils.NetworkHelper
import kotlinx.coroutines.launch

class StoryViewModel@ViewModelInject constructor(
    private val userRepository: DataRepository,
    private val networkHelper: NetworkHelper
) : ViewModel()  {

    private val _stories = MutableLiveData<Resource<List<StoryObject>>>()
    val stories: LiveData<Resource<List<StoryObject>>>
        get() = _stories

    init {
        fetchStories()
    }

    private fun fetchStories() {
        viewModelScope.launch {
            _stories.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                userRepository.getStories().let {
                    Log.v("ChatsViewModelLogs", it.body().toString())
                    if (it.isSuccessful) {
                        _stories.postValue(Resource.success(it.body()!!.body.storyList))
                    } else _stories.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _stories.postValue(Resource.error("No internet connection", null))
        }
    }
}