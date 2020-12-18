package com.bigstep.whatsappredesign.data.model.message

import com.squareup.moshi.Json

class SenderObject (

    @field:Json(name = "displayname")
    val displayName: String = "",

    @field:Json(name = "image_icon")
    val imageIcon: String = ""
)