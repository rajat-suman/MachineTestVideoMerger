package com.apptunix.videoplayer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlayVideoVMFactory(private val context: Context, private val path: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayVideoVM::class.java)) {
            return PlayVideoVM(context,path) as T
        }
        throw IllegalArgumentException("")
    }
}