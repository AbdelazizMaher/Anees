package com.example.anees.utils.media_helper

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

object RadioPlayer {

    private var player: ExoPlayer? = null
    private lateinit var audioAttributes: android.media.AudioAttributes

    fun initializePlayer(context: Context) {
        if (player == null) {
            audioAttributes = android.media.AudioAttributes.Builder()
                .setUsage(android.media.AudioAttributes.USAGE_MEDIA)
                .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

            player = ExoPlayer.Builder(context).build().apply {
                setAudioAttributes(audioAttributes, false)
                playWhenReady = true
            }
        }
    }

    fun getAudioAttributes(): android.media.AudioAttributes {
        return audioAttributes
    }

    fun setMediaItem(url: String) {
        player?.apply {
            stop()
            clearMediaItems()
            val mediaItem = MediaItem.fromUri(url)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    fun play() {
        player?.playWhenReady = true
    }

    fun pause() {
        player?.pause()
    }

    fun isPlaying(): Boolean {
        return player?.isPlaying == true
    }

    fun release() {
        player?.release()
        player = null
    }

    fun setListener(listener: Player.Listener) {
        player?.addListener(listener)
    }

    fun removeListener(listener: Player.Listener) {
        player?.removeListener(listener)
    }

    fun getPlayer(): ExoPlayer? {
        return player
    }

    fun getCurrentPosition(): Long {
        return player?.currentPosition ?: 0L
    }

    fun getDuration(): Long {
        return player?.duration ?: 0L
    }

    fun seekTo(milliseconds: Long) {
        player?.seekTo(milliseconds)
    }
}