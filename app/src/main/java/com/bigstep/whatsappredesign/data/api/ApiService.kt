package com.bigstep.hiltmindorks.data.api

import com.bigstep.whatsappredesign.data.model.call.MainCallResponse
import com.bigstep.whatsappredesign.data.model.message.MainMessageApiResponse
import com.bigstep.whatsappredesign.data.model.story.MainStoryApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("messages/inbox")
    suspend fun getMessages(): Response<MainMessageApiResponse>

    @GET("stories")
    suspend fun getStories() : Response<MainStoryApiResponse>

    @GET("calls")
    suspend fun getCallList() : Response<MainCallResponse>

}
