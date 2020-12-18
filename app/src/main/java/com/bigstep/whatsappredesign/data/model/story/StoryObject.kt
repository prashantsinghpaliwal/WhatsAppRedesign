package com.bigstep.whatsappredesign.data.model.story

import com.squareup.moshi.Json

data class StoryObject(

    @field:Json(name = "owner_title")
    val title: String = "",

    @field:Json(name = "owner_image")
    val imageIcon: String = "",

    @field:Json(name = "story_count")
    val storyCount: Int = 0

)