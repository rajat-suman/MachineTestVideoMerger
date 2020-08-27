package com.apptunix.utils

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.apptunix.R
import com.apptunix.videoplayer.PlayVideo
import kotlinx.android.synthetic.main.video_alert.view.*

object Util {
    fun getPathFromUri(context: Context, uri: Uri?): String? {
        val projection =
            arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(uri!!, projection, null, null, null)
        return if (cursor != null) {
            val columnIndex: Int = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        } else null
    }

    private var progressAlert: AlertDialog? = null

    fun showProgress(context: Context) {
        var customAlertBuilder = AlertDialog.Builder(context)
        val customAlertView =
            LayoutInflater.from(context).inflate(R.layout.progress, null)
        customAlertBuilder.setView(customAlertView)


        progressAlert = customAlertBuilder.create()
        progressAlert!!.show()
        progressAlert!!.setCanceledOnTouchOutside(false)

        progressAlert!!.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }

    fun dismissProgress() {
        if (progressAlert != null && progressAlert!!.isShowing) {
            progressAlert!!.dismiss()
        }
    }


    private var openVideoAlert: AlertDialog? = null

    fun openVideoAlert(context: Context, path: String) {
        var customAlertBuilder = AlertDialog.Builder(context)
        val customAlertView =
            LayoutInflater.from(context).inflate(R.layout.video_alert, null)
        customAlertBuilder.setView(customAlertView)

        val btPlay = customAlertView.btPlay
        val btCancel = customAlertView.btCancel

        btPlay.setOnClickListener {
            var intent = Intent(context, PlayVideo::class.java)
            intent.putExtra("path", path)
            context.startActivity(intent)
        }

        btCancel.setOnClickListener {
            openVideoAlert!!.dismiss()
        }

        openVideoAlert = customAlertBuilder.create()
        openVideoAlert!!.show()
        openVideoAlert!!.setCanceledOnTouchOutside(false)
        openVideoAlert!!.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_LONG
        ).show()

    }

}