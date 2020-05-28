package com.nectar.nps

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.nectar.nps.Adapter.AlarmAdapter
import com.nectar.nps.Adapter.DashboardAlarmDataAdapter
import com.nectar.nps.data.AlarmDashboard
import com.nectarinfotel.utils.NectarApplication
import kotlinx.android.synthetic.main.alarm_item_layout.*
import kotlinx.android.synthetic.main.dashboardalarm_layout.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardAlarmList: AppCompatActivity() {
    internal var textlength = 0
    internal lateinit var data: AlarmDashboard
    private var filteralarm: MutableList<AlarmDashboard> = mutableListOf()
    private var alarm: MutableList<AlarmDashboard> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.dashboardalarm_layout)
        // Creates a vertical Layout Manager
        alarmdata_list.layoutManager = LinearLayoutManager(applicationContext)
          var value=AlarmDashboardFragment.xvalue
        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        // rv_animal_list.layoutManager = GridLayoutManager(this, 2)
        // Access the RecyclerView Adapter and load the data into it
        alarmdata_list.adapter= applicationContext?.let { DashboardAlarmDataAdapter(it,alarm) }
        getalarmdata(value)
        dashboardalarm_back_layout.setOnClickListener { view: View ->
            finish()
        }
        edittext_alarmdata!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                textlength = edittext_alarmdata!!.text.length
                filteralarm.clear()
                for (i in alarm.indices) {
                    if (textlength <= alarm.size) {

                        if (alarm[i].siteName.toLowerCase().trim().contains(
                                edittext_alarmdata!!.text.toString().toLowerCase().trim { it <= ' ' })
                        )
                        {
                            filteralarm.add(alarm[i])
                        }
                        if (alarm[i].specificProblem.toLowerCase().trim().contains(
                                edittext_alarmdata!!.text.toString().toLowerCase().trim { it <= ' ' })
                        )

                        {
                            filteralarm.add(alarm[i])
                        }

                        if (alarm[i].perceivedSeverity.toLowerCase().trim().contains(
                                edittext_alarmdata!!.text.toString().toLowerCase().trim { it <= ' ' })
                        )

                        {
                            filteralarm.add(alarm[i])
                        }
                    }
                }
                alarmdata_list.adapter= applicationContext?.let { DashboardAlarmDataAdapter(it,filteralarm) }

            }
        })
    }

    private fun getalarmdata(value: String) {
        Log.d("value", value)
        val call = NectarApplication.mRetroClient!!.callAlarmDatatAPI( "Bearer "+ NectarApplication.token,value)
        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                progressbar_alarmdata.visibility= View.GONE
                Log.d("alarm_response", response.body().toString())
                var str_response = response.body()
                val rsp: JsonArray? = response.body() ?: return

                var jsonArray = JSONArray(response.body().toString())
                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    data = AlarmDashboard()

                    data.setPerceivedSeverity(jsonArray.getJSONObject(jsonIndex).getString("PerceivedSeverity"))
                    data.setSpecificProblem(jsonArray.getJSONObject(jsonIndex).getString("SpecificProblem"))
                    data.setSiteName(jsonArray.getJSONObject(jsonIndex).getString("sitename"))
                    alarm.add(data)
                }

                if(alarm.size>0)
                {
                    alarmdata_list.adapter?.notifyDataSetChanged()
                }
                else
                {
                    Toast.makeText(applicationContext, "No records found", Toast.LENGTH_SHORT).show()
                }

                Log.d("ddfgf", "alatmgraph "+alarm.size)
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                progressbar_alarm.visibility= View.GONE
                Log.d("str_responsefail", "str_responsefail"+t)
                Toast.makeText(applicationContext, "please try again"+t, Toast.LENGTH_SHORT).show()
            }
        })
    }
}