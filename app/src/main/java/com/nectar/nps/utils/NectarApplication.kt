package com.nectarinfotel.utils

import android.app.Application
import com.nectar.nps.retrofit.RetroAPIInterface
import com.nectar.nps.retrofit.RetrofitClient
import com.nectarinfotel.retrofit.Retrofit


class NectarApplication : Application(){

    companion object {
        var mRetroClient: RetroAPIInterface? = null
        var userID : String = ""
        var userName : String = ""
        var token : String = ""
        var Password : String = ""

    }

    override fun onCreate() {
        super.onCreate()
     //  mRetroClient = RetrofitClient.getClient().create(RetroAPIInterface::class.java)
      mRetroClient = Retrofit.run { getClient()!!.create(RetroAPIInterface::class.java) }
    }
}