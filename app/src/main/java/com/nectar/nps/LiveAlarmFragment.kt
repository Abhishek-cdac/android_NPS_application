package com.nectar.nps

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.tabs.TabLayout
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nectar.nps.Adapter.AlarmAdapter
import com.nectar.nps.Adapter.Alarmtype_Adapter
import com.nectar.nps.data.AlarmDashboard
import com.nectarinfotel.utils.NectarApplication
import kotlinx.android.synthetic.main.alarm_details_layout.*
import kotlinx.android.synthetic.main.alarm_details_layout.view.*
import kotlinx.android.synthetic.main.alarm_details_layout.view.back_layout
import kotlinx.android.synthetic.main.alarm_item_layout.view.*
import kotlinx.android.synthetic.main.livealarm_dashboard.*
import kotlinx.android.synthetic.main.livealarm_dashboard.cleared_alarm_value
import kotlinx.android.synthetic.main.livealarm_dashboard.critical_alarm_value
import kotlinx.android.synthetic.main.livealarm_dashboard.major_alarm_value
import kotlinx.android.synthetic.main.livealarm_dashboard.total_count
import kotlinx.android.synthetic.main.livealarm_dashboard.total_layout
import kotlinx.android.synthetic.main.livealarm_dashboard.view.*
import kotlinx.android.synthetic.main.livealarm_dashboard.view.critical_alarm
import kotlinx.android.synthetic.main.livealarmdashboard_layout.*
import kotlinx.android.synthetic.main.login_layout.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LiveAlarmFragment :AppCompatActivity() {
    var total: Int? = 0
    var totalcount: Int? = 0
    lateinit var faqsView: View
    private lateinit var tabLayout: TabLayout
    var  cnt=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.livealarmdashboard_layout)
        critical_alarm_layout.setOnClickListener { view: View ->

            startActivity(Intent(applicationContext,AlarmDetialActivity::class.java))

        }
        major_alarm_layout.setOnClickListener { view: View ->

            startActivity(Intent(applicationContext,MajorAlarmDetialActivity::class.java))

        }
        minar_alarm_layout.setOnClickListener { view: View ->

            startActivity(Intent(applicationContext,MinarAlarmDetialActivity::class.java))

        }
        cleared_alarm_layout.setOnClickListener { view: View ->

            startActivity(Intent(applicationContext,ClearedlAlarmDetialActivity::class.java))

        }
        callclearedalarmcountapi()
        callalarmcountapi()

        livealarm_back_layout.setOnClickListener { view: View ->
            finish()
        }
    }

    private fun callclearedalarmcountapi() {
        val call = NectarApplication.mRetroClient!!.callAlarmclearedCOuntAPI( "Bearer "+ NectarApplication.token)
        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                Log.d("clearedcount", response.body().toString())
                //  var str_response = response.body()!!.toString()
                val rsp: JsonArray? = response.body() ?: return
                //   val leagueArray = JSONArray(jsonArray)

                var jsonArray = JSONArray(response.body().toString())
                // var jsonArray1 = JSONArray("data")
                // val json_contact:JSONObject = JSONObject(str_response)

                //   var jsonarray_contacts:JSONArray= json_contact.getJSONArray("contacts")


                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                      cnt=jsonArray.getJSONObject(jsonIndex).getString("cnt")

                       Log.d("PerceivedSeverityID", cnt)

                        cleared_alarm_value.text=cnt


                  //  total_count.text=totalcount.toString()
                    //{"flag":true,"msg":"Data found","info":[{"total":"49","status":"assigned"},{"total":"6","status":"escalated_tto"},{"total":"7","status":"escalated_ttr"},{"total":"21","status":"new"},{"total":"16","status":"resolved"}]}
                    //D/responbse: {"flag":true,"msg":"Data found","info":[{"total":"49","status":"assigned"},{"total":"6","status":"escalated_tto"},{"total":"7","status":"escalated_ttr"},{"total":"21","status":"new"},{"total":"16","status":"resolved"}]}
                }


            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("ddfgf", "LoginFailed"+t)
                Toast.makeText(applicationContext, "Login Failed"+t, Toast.LENGTH_SHORT).show()
            }
        })
    }
    /* override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         faqsView = inflater?.inflate(R.layout.livealarmdashboard_layout, container, false)
         faqsView.total_layout.setOnClickListener { view: View ->

             startActivity(Intent(activity,AlarmDetialActivity::class.java))

         }
         callalarmcountapi()

         faqsView.back_layout.setOnClickListener { view: View ->
             activity?.finish()
         }
         return faqsView
     }*/

    private fun callalarmcountapi() {
        val call = NectarApplication.mRetroClient!!.callAlarmCOuntAPI( "Bearer "+ NectarApplication.token)
        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                Log.d("ddfgf", response.body().toString())
              //  var str_response = response.body()!!.toString()
                val rsp: JsonArray? = response.body() ?: return
                //   val leagueArray = JSONArray(jsonArray)

                var jsonArray = JSONArray(response.body().toString())
                // var jsonArray1 = JSONArray("data")
                // val json_contact:JSONObject = JSONObject(str_response)

                //   var jsonarray_contacts:JSONArray= json_contact.getJSONArray("contacts")


                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    var  PerceivedSeverityID=jsonArray.getJSONObject(jsonIndex).getString("PerceivedSeverityID")
                    var PerceivedSeverity=jsonArray.getJSONObject(jsonIndex).getString("PerceivedSeverity")
                    var AlarmsCount=jsonArray.getJSONObject(jsonIndex).getString("AlarmsCount")
                    total=AlarmsCount.toInt()
                    //totalcount= total++
                    totalcount= totalcount!! + total!!
                    Log.d("total", ""+total)
                    Log.d("totalcount", ""+totalcount)
                    Log.d("PerceivedSeverityID", PerceivedSeverityID)
                    Log.d("PerceivedSeverity", PerceivedSeverity)
                    Log.d("AlarmsCount", cnt)

                    if(PerceivedSeverity.equals("Critical"))
                    {
                        critical_alarm_value.text=AlarmsCount
                    }
                    else  if(PerceivedSeverity.equals("Major"))
                    {
                        major_alarm_value.text=AlarmsCount
                    }
                    else  if(PerceivedSeverity.equals("Minor"))
                    {
                        Minar.text=AlarmsCount
                    }
                    /*else  if(PerceivedSeverity.equals("Cleared"))
                    {
                        cleared_alarm_value.text=AlarmsCount
                    }*/


                    //{"flag":true,"msg":"Data found","info":[{"total":"49","status":"assigned"},{"total":"6","status":"escalated_tto"},{"total":"7","status":"escalated_ttr"},{"total":"21","status":"new"},{"total":"16","status":"resolved"}]}
                    //D/responbse: {"flag":true,"msg":"Data found","info":[{"total":"49","status":"assigned"},{"total":"6","status":"escalated_tto"},{"total":"7","status":"escalated_ttr"},{"total":"21","status":"new"},{"total":"16","status":"resolved"}]}
                }
                if(!cnt.equals(""))
                {
                    total=cnt.toInt()
                    totalcount= totalcount!! + total!!
                    total_count.text = totalcount.toString()
                    Log.d("totalcount", totalcount.toString())
                }
                else {
                    total_count.text = totalcount.toString()
                    Log.d("totalcount111", totalcount.toString())
                }

            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("ddfgf", "LoginFailed"+t)
                Toast.makeText(applicationContext, "Login Failed"+t, Toast.LENGTH_SHORT).show()
            }
        })
    }
}