package com.bigstep.whatsappredesign.data.model.message

import com.squareup.moshi.Json

data class Message (

    @field:Json(name = "message_id")
    val messageId: Int = 0,

    @field:Json(name = "unread_count")
    val unreadCount: Int = 0,

    @field:Json(name = "date")
    val name: String = "",

    @field:Json(name = "title")
    val title: String = "",

    @field:Json(name = "body")
    val body: String = "",

    @field:Json(name = "message_status")
    val messageStatus: String = ""
)