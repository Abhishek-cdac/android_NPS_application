package com.nectar.nps

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.nectar.nps.Adapter.AlarmAdapter
import com.nectar.nps.data.AlarmDashboard
import com.nectarinfotel.utils.NectarApplication
import kotlinx.android.synthetic.main.alarm_item_layout.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmDetialActivity:AppCompatActivity() {
    internal var textlength = 0
    internal lateinit var data: AlarmDashboard
    //internal lateinit var data: ActiveAlarm
    val animals: ArrayList<String> = ArrayList()
    val animals1: ArrayList<String> = ArrayList()
    private var alarm: MutableList<AlarmDashboard> = mutableListOf()
    private var filteralarm: MutableList<AlarmDashboard> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.alarm_item_layout)

        // Creates a vertical Layout Manager
        alarm_list.layoutManager = LinearLayoutManager(applicationContext)
        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        // rv_animal_list.layoutManager = GridLayoutManager(this, 2)
        // Access the RecyclerView Adapter and load the data into it
        alarm_list.adapter= applicationContext?.let { AlarmAdapter(it,alarm) }

         active_back_layout.setOnClickListener { view: View ->
            finish()
        }
       edittext!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                textlength = edittext!!.text.length
                filteralarm.clear()
                for (i in alarm.indices) {
                    if (textlength <= alarm.size) {

                        if (alarm[i].siteName.toLowerCase().trim().contains(
                                edittext!!.text.toString().toLowerCase().trim { it <= ' ' })
                        )
                        {
                            filteralarm.add(alarm[i])
                        }
                            if (alarm[i].specificProblem.toLowerCase().trim().contains(
                                edittext!!.text.toString().toLowerCase().trim { it <= ' ' })
                        )

                        {
                            filteralarm.add(alarm[i])
                        }
                        if (alarm[i].probableCause.toLowerCase().trim().contains(
                                edittext!!.text.toString().toLowerCase().trim { it <= ' ' })
                        )

                        {
                            filteralarm.add(alarm[i])
                        }
                        if (alarm[i].venderName.toLowerCase().trim().contains(
                                edittext!!.text.toString().toLowerCase().trim { it <= ' ' })
                        )

                        {
                            filteralarm.add(alarm[i])
                        }
                        if (alarm[i].managedElement.toLowerCase().trim().contains(
                                edittext!!.text.toString().toLowerCase().trim { it <= ' ' })
                        )

                        {
                            filteralarm.add(alarm[i])
                        }
                    }
                }
                alarm_list.adapter= applicationContext?.let { AlarmAdapter(it,filteralarm) }

            }
        })

        callcriticalalarmapi()
    }

    private fun callcriticalalarmapi() {
        val call = NectarApplication.mRetroClient!!.callCriticalAlarmcountAPI( "Bearer "+ NectarApplication.token)
        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                progressbar_alarm.visibility=View.GONE
                Log.d("str_response", response.body().toString())
                var str_response = response.body()
                val rsp: JsonArray? = response.body() ?: return

                var jsonArray = JSONArray(response.body().toString())
                  for (jsonIndex in 0..(jsonArray.length() - 1)) {

                       data = AlarmDashboard()

                     //  data.setVenderID(VenderID)
                       data.setVenderName(jsonArray.getJSONObject(jsonIndex).getString("VenderName"))
                       data.setProbableCause(jsonArray.getJSONObject(jsonIndex).getString("ProbableCause"))
                       data.setManagedElement(jsonArray.getJSONObject(jsonIndex).getString("ManagedElement"))
                       data.setSpecificProblem(jsonArray.getJSONObject(jsonIndex).getString("SpecificProblem"))
                       data.setSiteName(jsonArray.getJSONObject(jsonIndex).getString("SiteName"))
                       data.setEventTime(jsonArray.getJSONObject(jsonIndex).getString("EventTime"))
                       alarm.add(data)
                   }

                if(alarm.size>0)
                {
                    alarm_list.adapter?.notifyDataSetChanged()
                }
                else
                {
                    Toast.makeText(applicationContext, "" +
                            " records found", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                progressbar_alarm.visibility=View.GONE
                Log.d("str_responsefail", "str_responsefail"+t)
                Toast.makeText(applicationContext, "please try again"+t, Toast.LENGTH_SHORT).show()
            }
        })
    }
}