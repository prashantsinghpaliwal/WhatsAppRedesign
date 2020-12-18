package com.bigstep.whatsappredesign.data.model.call

import com.squareup.moshi.Json

class CallObject (

    @field:Json(name = "owner_title")
    val name: String = "",

    @field:Json(name = "owner_image")
    val imageIcon: String = "",

    @field:Json(name = "call_type")
    val callType: String = "",

    @field:Json(name = "call_status")
    val callStatus: String = "",

    @field:Json(name = "date")
    val date: String = ""
)