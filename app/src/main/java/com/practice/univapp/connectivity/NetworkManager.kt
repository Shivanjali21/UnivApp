package com.practice.univapp.connectivity

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData

class NetworkManager(context: Context) : LiveData<Boolean>() {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @SuppressLint("WrongConstant", "InlinedApi")
    private val networkRequest = NetworkRequest.Builder().apply {
      addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
      addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
      addCapability(NetworkCapabilities.TRANSPORT_ETHERNET)
      addCapability(NetworkCapabilities.TRANSPORT_CELLULAR)
      addCapability(NetworkCapabilities.TRANSPORT_WIFI)
    }.build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }
    }

    override fun onActive() {
      super.onActive()
      checkNetworkConnectivity()
    }

    override fun onInactive() {
      super.onInactive()
      releaseCheckingInternetConnectivity()
    }

    @SuppressLint("NewApi")
    private fun checkNetworkConnectivity() {
      if(connectivityManager.activeNetwork == null)postValue(false)
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
         connectivityManager.registerDefaultNetworkCallback(networkCallback)
       }else {
         connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
       }
    }

    private fun releaseCheckingInternetConnectivity() {
      connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}