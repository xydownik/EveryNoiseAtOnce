package com.example.everynoiseatonce.utils

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.net.toUri

@Singleton
class ExoPlayerHolder @Inject constructor(context: Context) {
    private val player = ExoPlayer.Builder(context).build()

    fun play(url: String) {
        val mediaItem = MediaItem.fromUri(url.toUri())
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    fun stop() {
        player.stop()
    }

    fun getPlayer(): ExoPlayer = player
}
