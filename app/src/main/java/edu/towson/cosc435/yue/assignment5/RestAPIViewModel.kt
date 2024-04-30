package edu.towson.cosc435.yue.assignment5

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.*
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.SerializedName
import edu.towson.cosc435.yue.assignment5.network.IImageFetcher
import edu.towson.cosc435.yue.assignment5.network.ImagesFetcher
import kotlinx.coroutines.*

data class Image(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerializedName("download_url")
    val downloadUrl: String,
    var icon: MutableState<Bitmap?>
)


class RestAPIViewModel(private val app: Application) : AndroidViewModel(app) {
    private val _images: MutableState<List<Image>>
    val images: State<List<Image>>

    val imagesFetcher: IImageFetcher = ImagesFetcher(getApplication<Application>().cacheDir)
    private var _isOnline = false

    val _selectedImage: MutableState<Bitmap?>
    var selectedImage: State<Bitmap?>

    init {
        _images = mutableStateOf(listOf())
        images = _images

        _selectedImage = mutableStateOf(null)
        selectedImage = _selectedImage

        val cm = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nr = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        cm.requestNetwork(nr, object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                val nc = cm.getNetworkCapabilities(network)
                if(nc != null) {
                    // check that we are connected to WIFI
                    Log.d(RestAPIViewModel::class.java.simpleName, "WIFI: ${nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)}")
                    Log.d(RestAPIViewModel::class.java.simpleName, "CELLULAR: ${nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)}")
                }
                Log.d(RestAPIViewModel::class.java.simpleName, "Network available")
                _isOnline = true
                viewModelScope.launch {
                    fetchImages()
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                val nc = cm.getNetworkCapabilities(network)
                if(nc != null) {
                    // check that we are connected to WIFI
                    Log.d(RestAPIViewModel::class.java.simpleName, "WIFI: ${nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)}")
                    Log.d(RestAPIViewModel::class.java.simpleName, "CELLULAR: ${nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)}")
                }
                Log.d(RestAPIViewModel::class.java.simpleName, "Network unavailable")
                _isOnline = false
                viewModelScope.cancel()
            }
        })
    }

    fun isOnline(): Boolean {
        return _isOnline
    }

    suspend fun fetchImages() {
        if (!isOnline()) return
        val imageDataList = imagesFetcher.fetchImages()
        val images = imageDataList.map { imageData ->
            Image(
                id = imageData.id.toInt(),
                author = imageData.author,
                width = imageData.width,
                height = imageData.height,
                url = imageData.url,
                downloadUrl = imageData.downloadUrl,
                icon = mutableStateOf(null)
            )
        }

        _images.value = images
    }

    fun selectImage(image: Bitmap?) {
        viewModelScope.launch {
            selectedImage = mutableStateOf(image)
        }
    }

    suspend fun fetchIcon(image: Image) {
        if(!isOnline()) return
        image.icon.value = imagesFetcher.fetchIcon(image.downloadUrl)
    }
}