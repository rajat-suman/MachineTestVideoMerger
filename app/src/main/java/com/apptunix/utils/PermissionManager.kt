package com.apptunix.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi

object PermissionManager {

    @RequiresApi(Build.VERSION_CODES.M)
    fun getPermissionsList(context: Context):ArrayList<String> {
        var list = ArrayList<String>()
        if ((context as Activity).checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            list.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.CAMERA)
        }
        if (context.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.RECORD_AUDIO)
        }
        if (context.checkSelfPermission(Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.WAKE_LOCK)
        }

        return list
    }

}