package ricardonuma.com.desafio_android.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by ricardonuma on 12/18/17.
 */

object Utilities {

    fun isOnline(context: Context?): Boolean {

        val conMgr = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (conMgr.getNetworkInfo(0) != null) {
            if (conMgr.getNetworkInfo(0).state == NetworkInfo.State.CONNECTED || conMgr.getNetworkInfo(1).state == NetworkInfo.State.CONNECTED) {
                return true
                // notify user you are online
            } else if (conMgr.getNetworkInfo(0).state == NetworkInfo.State.DISCONNECTED || conMgr.getNetworkInfo(1).state == NetworkInfo.State.DISCONNECTED) {
                // notify user you are not online
                return false
            }
        } else {
            if (conMgr.getNetworkInfo(1).state == NetworkInfo.State.CONNECTED) {
                return true
                // notify user you are online
            } else if (conMgr.getNetworkInfo(1).state == NetworkInfo.State.DISCONNECTED) {
                // notify user you are not online
                return false
            }
        }
        return false
    }

}