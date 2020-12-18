package com.bigstep.hiltmindorks.data.api

import com.bigstep.whatsappredesign.data.model.call.MainCallResponse
import com.bigstep.whatsappredesign.data.model.message.MainMessageApiResponse
import com.bigstep.whatsappredesign.data.model.story.MainStoryApiResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getMessages(): Response<MainMessageApiResponse>

    suspend fun getStories() : Response<MainStoryApiResponse>

    suspend fun getCallList() : Response<MainCallResponse>
}