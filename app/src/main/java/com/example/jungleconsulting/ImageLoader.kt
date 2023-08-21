package com.example.jungleconsulting

import android.content.Context
import com.example.jungleconsulting.model.UserDataModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ImageLoader(private val context: Context) {

    suspend fun saveImageToCache(user: UserDataModel) = suspendCoroutine { continuation ->
        val imageFileName = "${user.login}.jpg"
        val imagesCacheDir = File(context.filesDir, "images_cache")
        if (!imagesCacheDir.exists()) {
            imagesCacheDir.mkdir()
        }

        val okHttpClient = OkHttpClient()
        val request = Request.Builder().url(user.image).build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) { continuation.resume(null) }
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val imageFile = File(imagesCacheDir, imageFileName)
                    val inputStream = responseBody.byteStream()
                    val outputStream = FileOutputStream(imageFile)
                    inputStream.copyTo(outputStream)
                    outputStream.close()
                    continuation.resume(imageFile.name)
                }
            }
        })
    }
}