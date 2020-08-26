package com.apptunix.main

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.apptunix.R
import com.apptunix.databinding.MainBinding
import com.apptunix.utils.Util.getPathFromUri


class MainActivity : AppCompatActivity() {
    private var mainBinding: MainBinding? = null
    private var mainVM: MainVM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this,
            R.layout.main
        )
        val factory = MainVMFactory(this, this@MainActivity)
        mainVM = ViewModelProvider(this, factory).get(MainVM::class.java)
        mainBinding!!.mainVM = mainVM
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                var count = 0
                for (i in grantResults.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        count++
                    }
                }
                if (count == permissions.size) {
                    if (mainVM!!.isCapture) {
                        mainVM!!.captureVideo()
                    } else {
                        mainVM!!.pickVideo()
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            100 -> {
                if (resultCode == Activity.RESULT_OK) {
                    mainVM!!.cameraFileUri = data!!.data
                    val path = getPathFromUri(this, mainVM!!.cameraFileUri)
                    mainVM!!.captureText.set(path)
                }
            }
            200 -> {
                if (resultCode == Activity.RESULT_OK) {
                    val fileUri = data!!.data
                    val path = getPathFromUri(this, fileUri)
                    mainVM!!.pickText.set(path)
                }
            }
        }
    }

}