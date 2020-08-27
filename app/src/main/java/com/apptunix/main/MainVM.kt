package com.apptunix.main

import VideoHandle.EpEditor
import VideoHandle.EpVideo
import VideoHandle.OnEditorListener
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.apptunix.utils.PermissionManager
import com.apptunix.utils.Util.dismissProgress
import com.apptunix.utils.Util.openVideoAlert
import com.apptunix.utils.Util.showProgress
import com.apptunix.utils.Util.showToast
import java.io.File

class MainVM(private val context: Context, private val mainActivity: MainActivity) : ViewModel() {
    var isCapture = false
    var cameraFileUri: Uri? = null
    var outputFile: File? = null

    var captureText = ObservableField("")
    var pickText = ObservableField("")

    @RequiresApi(Build.VERSION_CODES.M)
    fun clicks(type: String) {
        when (type) {
            "camera" -> {
                isCapture = true
                checkPermissions()
            }
            "gallery" -> {
                isCapture = false
                checkPermissions()
            }
            "merge" -> {
                if (!captureText.get().isNullOrEmpty() && !pickText.get().isNullOrEmpty()) {
                    val dir = mainActivity.filesDir.absolutePath + "/someFolder/"
                    outputFile = File(dir + "merged_video.mp4")
                    try {
                        if (!outputFile!!.exists()) {
                            File(dir).mkdirs()     // make sure to call mkdirs() when creating new directory
                            outputFile!!.createNewFile()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    mergeVideos()
                } else {
                    showToast(
                        context,
                        "Please capture one video from camera and other from gallery."
                    )
                }

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermissions() {
        try {
            var list = PermissionManager.getPermissionsList(context)

            if (list.isNotEmpty()) {
                mainActivity.requestPermissions(list.toTypedArray(), 101)
                return
            }

            if (isCapture) {
                captureVideo()
                return
            }

            pickVideo()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun captureVideo() {
        try {
            val values = ContentValues()
            cameraFileUri =
                context.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileUri)
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
            mainActivity.startActivityForResult(intent, 100)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pickVideo() {
        try {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            mainActivity.startActivityForResult(intent, 200)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun mergeVideos() {
        try {
            showProgress(context)
            val epVideos = ArrayList<EpVideo>()
            epVideos.add(EpVideo(captureText.get()!!))
            epVideos.add(EpVideo(pickText.get()!!))
            val outputOption = EpEditor.OutputOption(outputFile!!.path)
            outputOption.setWidth(320)
            outputOption.setHeight(560)
            outputOption.frameRate = 25

            EpEditor.merge(epVideos, outputOption, object : OnEditorListener {
                override fun onSuccess() {
                    dismissProgress()

                    mainActivity.runOnUiThread {
                        openVideoAlert(context, outputFile!!.path)
                    }
                    Log.d("onSuccess", "onSuccess")
                }

                override fun onFailure() {
                    dismissProgress()
                    showToast(context, "Some error occured while merging videos!")
                    Log.d("onFailure", "onFailure")
                }

                override fun onProgress(progress: Float) {
                    Log.d("Progress", "$progress")
                }

            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}