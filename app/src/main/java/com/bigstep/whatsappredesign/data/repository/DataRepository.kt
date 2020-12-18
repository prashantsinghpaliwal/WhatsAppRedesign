package com.bigstep.whatsappredesign.data.repository

import com.bigstep.hiltmindorks.data.api.ApiHelper
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getMessages() =  apiHelper.getMessages()

    suspend fun getStories() = apiHelper.getStories()

    suspend fun getCallList() = apiHelper.getCallList()
}