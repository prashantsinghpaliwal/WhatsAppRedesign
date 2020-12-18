package com.bigstep.whatsappredesign.data.model.story

import com.squareup.moshi.Json

class MainStoryApiResponse(

    @field:Json(name = "status_code")
    val status_code: Int = 0,

    @field:Json(name = "body")
    val body: BodyResponse

) {
    data class BodyResponse(
        @field:Json(name = "response")
        val storyList: List<StoryObject>
    )

}
