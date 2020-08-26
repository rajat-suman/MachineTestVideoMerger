package com.apptunix.videoplayer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.apptunix.R
import com.apptunix.databinding.PlayVideoLayoutBinding
import kotlinx.android.synthetic.main.play_video_layout.*


class PlayVideo : AppCompatActivity() {

    var playVideoLayoutBinding: PlayVideoLayoutBinding? = null
    private var playVideoVM: PlayVideoVM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var path = ""
        if (intent.hasExtra("path")) {
            path = intent.getStringExtra("path")!!
        }
        fullScreen()
        playVideoLayoutBinding = DataBindingUtil.setContentView(this, R.layout.play_video_layout)
        val factory = PlayVideoVMFactory(this, path)
        playVideoVM = ViewModelProvider(this, factory).get(PlayVideoVM::class.java)
        playVideoLayoutBinding!!.playVideoVM = playVideoVM
    }


    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun fullScreen() {
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    private fun releasePlayer() {
        playVideoVM!!.player.stop()
        playerView.player = null
        playVideoVM!!.player.release()
    }
}