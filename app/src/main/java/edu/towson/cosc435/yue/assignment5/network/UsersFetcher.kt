package edu.towson.cosc435.yue.assignment5.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.Gson
import edu.towson.cosc435.yue.assignment5.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

interface IImageFetcher {
    suspend fun fetchImages() : List<Image>
    suspend fun fetchIcon(url: String): Bitmap
}

class ImagesFetcher(private val cacheDir: File) : IImageFetcher {
    override suspend fun fetchImages(): List<Image> {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .get()
                .url("${API_URL}/list?limit=20")
                .build()
            val response = client.newCall(request).execute()
            val body = response.body?.string()
            val imageDataList = Gson().fromJson(body, Array<Image>::class.java)
            imageDataList.toList()
        }
    }


    override suspend fun fetchIcon(url: String): Bitmap {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
                .newBuilder()
                .cache(
                    Cache(
                        directory = cacheDir,
                        maxSize = 100L * 1024L // 100kb
                    )
                )
                .build()
            val request = Request.Builder()
                .get()
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            delay(3000)
            val stream = response.body?.byteStream()
            val bitmap = BitmapFactory.decodeStream(stream)

            bitmap
        }
    }

    companion object {
        val API_URL = "https://picsum.photos/v2"
    }
}