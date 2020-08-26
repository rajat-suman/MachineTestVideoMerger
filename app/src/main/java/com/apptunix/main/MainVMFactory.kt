package com.apptunix.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainVMFactory(
    private val context: Context,
    private val mainActivity: MainActivity
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainVM::class.java)) {
            return MainVM(context, mainActivity) as T
        }
        throw IllegalArgumentException("")
    }
}