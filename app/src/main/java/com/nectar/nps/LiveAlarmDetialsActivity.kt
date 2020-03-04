package com.nectar.nps

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nectar.nps.data.Dashboard1
import com.nectarinfotel.utils.NectarApplication
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONArray






class LiveAlarmDetialsActivity : AppCompatActivity() {
    val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_details_layout)
        Log.d("access_token", NectarApplication.token)
     //  val call = NectarApplication.mRetroClient!!.callDashboardAPI()
     //   "Bearer "+
     /*  val call = NectarApplication.mRetroClient!!.callDashboardAPI( "Bearer "+ NectarApplication.token)

        
        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                Log.d("ddfgf", response.body().toString())
                val rsp: JsonArray? = response.body() ?: return
             //   val leagueArray = JSONArray(jsonArray)
                var jsonArray = JSONArray(response.body().toString())
                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    var VenderID=jsonArray.getJSONObject(jsonIndex).getString("VenderID")
                    var VenderName=jsonArray.getJSONObject(jsonIndex).getString("VenderName")
                    var NetworkTypeID=jsonArray.getJSONObject(jsonIndex).getString("NetworkTypeID")
                    var NetworkType=jsonArray.getJSONObject(jsonIndex).getString("NetworkType")
                    var TotalCount=jsonArray.getJSONObject(jsonIndex).getString("TotalCount")
                    Log.d("VenderID", VenderID)
                    Log.d("VenderName", VenderName)
                    Log.d("NetworkTypeID", NetworkTypeID)
                    Log.d("NetworkType", NetworkType)
                    Log.d("TotalCount", TotalCount)
                }

            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("ddfgf", "LoginFailed"+t)
                Toast.makeText(applicationContext, "Login Failed"+t, Toast.LENGTH_SHORT).show()
            }
        })
*/
       /* call.enqueue(object : Callback<List<Dashboard1>> {
            override fun onResponse(call: Call<List<Dashboard1>>, response: Response<List<Dashboard1>>) {
                //Log.i("onResponse", response.message());
                Log.i("autolog", "onResponse")

                Log.d("ddfgf", response.body().toString())

            }

            override fun onFailure(call: Call<List<Dashboard1>>, t: Throwable) {
                Log.i("autolog", t.message)
            }
        })*/


    }
}




