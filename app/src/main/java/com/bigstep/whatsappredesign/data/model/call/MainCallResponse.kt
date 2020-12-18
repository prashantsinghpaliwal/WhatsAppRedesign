package com.bigstep.whatsappredesign.data.model.call

import com.bigstep.whatsappredesign.data.model.story.StoryObject
import com.squareup.moshi.Json

class MainCallResponse (

    @field:Json(name = "status_code")
    val status_code: Int = 0,

    @field:Json(name = "body")
    val body: BodyResponse

) {
    data class BodyResponse(
        @field:Json(name = "response")
        val callList: List<CallObject>
    )

}
