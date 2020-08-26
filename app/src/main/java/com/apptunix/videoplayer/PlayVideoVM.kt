package com.apptunix.videoplayer

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class PlayVideoVM(val context: Context, private val path: String) : ViewModel() {
    var player = SimpleExoPlayer.Builder(context, DefaultRenderersFactory(context)).build()

    init {
        initPlayer()
    }

    private fun initPlayer() {

        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.applicationInfo.name)
            )

        val uri = Uri.parse(path)
        val mediaSource: MediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri)
        player.prepare(mediaSource, true, false)

        // PREPARE MEDIA PLAYER
        player.playWhenReady = true

        player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(
                playWhenReady: Boolean,
                playbackState: Int
            ) {
            }

            override fun onPlayerError(error: ExoPlaybackException) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
                (context as Activity).finish()
            }
        })
    }

}