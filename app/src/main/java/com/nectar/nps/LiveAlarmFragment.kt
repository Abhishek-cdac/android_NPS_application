package com.nectar.nps

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.gson.JsonArray
import com.nectarinfotel.utils.NectarApplication
import kotlinx.android.synthetic.main.livealarm_dashboard.cleared_alarm_value
import kotlinx.android.synthetic.main.livealarm_dashboard.critical_alarm_value
import kotlinx.android.synthetic.main.livealarm_dashboard.major_alarm_value
import kotlinx.android.synthetic.main.livealarm_dashboard.total_count
import kotlinx.android.synthetic.main.livealarmdashboard_layout.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LiveAlarmFragment :AppCompatActivity() {
    var total: Int? = 0
    var totalcount: Int? = 0
    var totalcount_critical: Int = 0
    var totalcountcritical: Int? = 0
    var totalcount_major: Int = 0
    var totalcountmajor: Int? = 0
    var totalcount_minar: Int = 0
    var totalcountminar: Int? = 0
    var totalcount_cleared: Int = 0
    var totalcountcleared: Int? = 0
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

        //call api for show alarm count
        callalarmcountapi()

        livealarm_back_layout.setOnClickListener { view: View ->
            finish()
        }
    }


    private fun callalarmcountapi() {
        progressbar_alarmcount.visibility=View.VISIBLE
        val call = NectarApplication.mRetroClient!!.callAlarmCOuntAPI( "Bearer "+ NectarApplication.token)
        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                Log.d("livealarmresponse", response.body().toString())
                progressbar_alarmcount.visibility=View.GONE

                val rsp: JsonArray? = response.body() ?: return

                var jsonArray = JSONArray(response.body().toString())

                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    var  PerceivedSeverityID=jsonArray.getJSONObject(jsonIndex).getString("PerceivedSeverityID")
                    var PerceivedSeverity=jsonArray.getJSONObject(jsonIndex).getString("PerceivedSeverity")
                    var Networktype=jsonArray.getJSONObject(jsonIndex).getString("NetworkType")
                    if(Networktype.equals("2G"))
                    {
                        if(PerceivedSeverity.equals("Critical"))
                        {
                            var totalvalue=jsonArray.getJSONObject(jsonIndex).getString("Total")
                            critical_twog_alarm_value.text="2G : "+totalvalue
                            totalcountcritical=totalvalue.toInt()
                           totalcount_critical= totalcount_critical!! + totalcountcritical!!
                        }
                        else  if(PerceivedSeverity.equals("Major"))
                        {
                            var totalvalue=jsonArray.getJSONObject(jsonIndex).getString("Total")
                            major_twog_alarm_value.text="2G : "+totalvalue
                            totalcountmajor=totalvalue.toInt()
                            totalcount_major= totalcount_major!! + totalcountmajor!!
                        }
                        else  if(PerceivedSeverity.equals("Minor"))
                        {
                            var totalvalue=jsonArray.getJSONObject(jsonIndex).getString("Total")
                            Minar_twog_alarm_value.text="2G : "+totalvalue
                            totalcountminar=totalvalue.toInt()
                            totalcount_minar= totalcount_minar!! + totalcountminar!!
                        }
                        else  if(PerceivedSeverity.equals("Cleared"))
                        {
                            var totalvalue=jsonArray.getJSONObject(jsonIndex).getString("Total")
                            cleared_twog_alarm_value.text="2G : "+totalvalue
                            totalcountcleared=totalvalue.toInt()
                            totalcount_cleared= totalcount_cleared!! + totalcountcleared!!
                        }

                    } else  if(Networktype.equals("3G"))
                    {
                        if(PerceivedSeverity.equals("Critical"))
                        {
                            var totalvalue=jsonArray.getJSONObject(jsonIndex).getString("Total")
                            critical_threeg_alarm_value.text="3G : "+totalvalue
                            total=totalvalue.toInt()
                            totalcount_critical= totalcount_critical!! + total!!
                        }
                        else  if(PerceivedSeverity.equals("Major"))
                        {
                            var totalvalue=jsonArray.getJSONObject(jsonIndex).getString("Total")
                            major_threeg_alarm_value.text="3G : "+totalvalue
                            totalcountmajor=totalvalue.toInt()
                            totalcount_major= totalcount_major!! + totalcountmajor!!
                        }
                        else  if(PerceivedSeverity.equals("Minor"))
                        {
                            var totalvalue=jsonArray.getJSONObject(jsonIndex).getString("Total")
                            Minar_threeg_alarm_value.text="3G : "+totalvalue
                            totalcountminar=totalvalue.toInt()
                            totalcount_minar= totalcount_minar!! + totalcountminar!!
                        }
                        else  if(PerceivedSeverity.equals("Cleared"))
                        {
                            var totalvalue=jsonArray.getJSONObject(jsonIndex).getString("Total")
                            cleared_threeg_alarm_value.text="3G : "+totalvalue
                            totalcountcleared=totalvalue.toInt()
                            totalcount_cleared= totalcount_cleared!! + totalcountcleared!!
                        }

                    }else if(Networktype.equals("4G"))
                    {
                        if(PerceivedSeverity.equals("Critical"))
                        {
                            var totalvalue=jsonArray.getJSONObject(jsonIndex).getString("Total")
                            critical_fourg_alarm_value.text="4G : "+totalvalue
                            total=totalvalue.toInt()
                            totalcount_critical= totalcount_critical!! + total!!
                        }
                        else  if(PerceivedSeverity.equals("Major"))
                        {
                            var totalvalue=jsonArray.getJSONObject(jsonIndex).getString("Total")
                            major_fourg_alarm_value.text="4G : "+totalvalue
                            totalcountmajor=totalvalue.toInt()
                            totalcount_major= totalcount_major!! + totalcountmajor!!
                        }
                        else  if(PerceivedSeverity.equals("Minor"))
                        {
                            var totalvalue=jsonArray.getJSONObject(jsonIndex).getString("Total")
                            Minar_fourg_alarm_value.text="4G : "+totalvalue
                            totalcountminar=totalvalue.toInt()
                            totalcount_minar= totalcount_minar!! + totalcountminar!!
                        }
                        else  if(PerceivedSeverity.equals("Cleared"))
                        {
                            var totalvalue=jsonArray.getJSONObject(jsonIndex).getString("Total")
                            cleared_fourg_alarm_value.text="4G : "+totalvalue
                            totalcountcleared=totalvalue.toInt()
                            totalcount_cleared= totalcount_cleared!! + totalcountcleared!!
                        }

                    }

                }

                critical_alarm_value.text = totalcount_critical.toString()
                major_alarm_value.text = totalcount_major.toString()
                Minar.text = totalcount_minar.toString()
                cleared_alarm_value.text = totalcount_cleared.toString()
                totalcount=totalcount_critical+totalcount_major+totalcount_minar+totalcount_cleared
                total_count.text = totalcount.toString()

            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                progressbar_alarmcount.visibility=View.GONE
                Toast.makeText(applicationContext, "Failed"+t, Toast.LENGTH_SHORT).show()
            }
        })
    }
}