package com.bigstep.hiltmindorks.data.api

import com.bigstep.whatsappredesign.data.model.call.MainCallResponse
import com.bigstep.whatsappredesign.data.model.message.MainMessageApiResponse
import com.bigstep.whatsappredesign.data.model.story.MainStoryApiResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getMessages(): Response<MainMessageApiResponse> = apiService.getMessages()

    override suspend fun getStories(): Response<MainStoryApiResponse> = apiService.getStories()

    override suspend fun getCallList(): Response<MainCallResponse> = apiService.getCallList()
}