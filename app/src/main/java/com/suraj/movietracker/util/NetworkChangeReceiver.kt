package com.suraj.movietracker.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.suraj.movietracker.MainActivity


class NetworkChangeReceiver(mainActivity: MainActivity) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val connManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val networkInfo: NetworkInfo? = connManager?.activeNetworkInfo

            val isConnected = networkInfo?.isConnectedOrConnecting == true

            try {
                (context as MainActivity).updateUIOnNetworkChange(isConnected)
            } catch (e: ClassCastException) {
                Log.e("NetworkChangeReceiver", "Error casting context to MainActivity: ${e.message}")
            }
        }
    }
}
