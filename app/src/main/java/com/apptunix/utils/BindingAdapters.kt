package com.apptunix.utils

import androidx.databinding.BindingAdapter
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

object BindingAdapters {

    @BindingAdapter(value = ["setExoPlayer"])
    @JvmStatic
    fun setExoPlayer(playerView: PlayerView, player: SimpleExoPlayer) {
        playerView.player = player
    }

}