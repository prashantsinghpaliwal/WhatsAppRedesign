package com.bigstep.whatsappredesign.data.model.message

import com.squareup.moshi.Json

data class MainMessageApiResponse(

    @field:Json(name = "status_code")
    val status_code: Int = 0,

    @field:Json(name = "body")
    val body: BodyResponse

) {
    data class BodyResponse(
        @field:Json(name = "response")
        val msgList: List<TotalMessage>
    )
}