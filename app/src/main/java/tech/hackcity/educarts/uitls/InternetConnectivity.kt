package tech.hackcity.educarts.uitls

import android.content.Context
import android.net.ConnectivityManager

/**
 *Created by Victor Loveday on 8/19/23
 */


object InternetConnectivity {

    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }
}
