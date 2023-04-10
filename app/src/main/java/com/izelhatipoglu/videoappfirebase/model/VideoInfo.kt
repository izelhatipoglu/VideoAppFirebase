package com.izelhatipoglu.videoappfirebase.model

import java.io.Serializable

data class VideoInfo(
    val videoId: String? = null,
    val videoUrl: String? = null,
    val videoName: String? = null,
    var isCompleted: Boolean? = false
): Serializable