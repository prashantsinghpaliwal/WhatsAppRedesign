package com.bigstep.whatsappredesign.data.model.message

import com.squareup.moshi.Json

data class TotalMessage (

    @field:Json(name = "message")
    val message: Message,

    @field:Json(name = "sender")
    val senderObject: SenderObject
)