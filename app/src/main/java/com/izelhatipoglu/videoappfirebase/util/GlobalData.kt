package com.izelhatipoglu.videoappfirebase.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.core.graphics.drawable.toDrawable
import java.lang.Exception

class GlobalData {
    companion object{

        fun checkForInternet(context: Context):Boolean {

            try {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE)as ConnectivityManager
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    val network = connectivityManager.activeNetwork ?:return false

                    val activeNetwork =
                        connectivityManager.getNetworkCapabilities(network) ?: return false

                    return when{
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->true

                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                        else ->false
                    }
                }else{

                    @Suppress("DEPRECATION") val networkInfo =
                        connectivityManager.activeNetworkInfo ?: return false
                    @Suppress("DEPRECATION")
                    return networkInfo.isConnected
                }

            }catch (e: Exception){
                Log.e("hideKeyboardError","error:${e.printStackTrace()}")
                return false
            }
        }
        fun showLoadingDialog(context: Context): Dialog {
            try{
                val progressDialog = Dialog(context)

                progressDialog.let {
                    it.show()
                    it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
                    // it.setContentView(com.chaos.view.R.layout.custom_dialog)
                    it.setCancelable(false)
                    it.setCanceledOnTouchOutside(true)

                    return it
                }
            }catch (e: Exception){
                Log.e("hideKeyboardError","error: ${e.printStackTrace()}")

                return Dialog(context)
            }
        }

    }
}