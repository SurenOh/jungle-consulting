package com.example.jungleconsulting.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

@Suppress("DEPRECATION")
fun Context.isInternetAvailable(): Boolean {
    return try {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        }
        false
    } catch (e: Exception) {
        false
    }
}