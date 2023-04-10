package com.izelhatipoglu.videoappfirebase.model

data class InfoUser(
    var id: String? = null,
    var name: String? = null,
    var mail:String? = null,
    var userVideoInfo: ArrayList<Map<String, Any>>? = null
)