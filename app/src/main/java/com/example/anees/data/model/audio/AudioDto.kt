package com.example.anees.data.model.audio

data class AudioDto(
    val id: Long,
    val title: String,
    val artist: String?,
    val album: String?,
    val duration: Long,
    val path: String,
    val size: Long,
    val dateAdded: Long,
)

fun AudioDto.toAudioTrack(index: Int): AudioTrack {
    return AudioTrack(
        index = index,
        title = title,
        description = album ?: "غير معروف",
        reciter = artist ?: "غير معروف",
        reciterImage = "",
        typeIcon = "local",
        reciterBaseUrl = "",
        uri = path,
    )
}