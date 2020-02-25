package com.nectar.nps

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.mikephil.charting.data.BarEntry
import com.google.gson.JsonArray

import com.nectar.nps.Adapter.AlarmAdapter
import com.nectar.nps.data.ActiveAlarm
import com.nectar.nps.data.AlarmDashboard
import com.nectarinfotel.utils.NectarApplication
import kotlinx.android.synthetic.main.alarm_item_layout.*
import kotlinx.android.synthetic.main.alarm_item_layout.view.*
import kotlinx.android.synthetic.main.alarm_item_layout.view.progressbar_alarm
import kotlinx.android.synthetic.main.livealarmdashboard_layout.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActiveAlarmFragment : Fragment(){
    internal lateinit var data: ActiveAlarm
    private var animals: MutableList<String> = mutableListOf()
    private var alarmlist: MutableList<ActiveAlarm> = mutableListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View  = inflater.inflate(R.layout.alarm_item_layout, container, false)
        addAnimals()
        // Creates a vertical Layout Manager
        view.alarm_list.layoutManager = LinearLayoutManager(activity)


        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        // rv_animal_list.layoutManager = GridLayoutManager(this, 2)
        // Access the RecyclerView Adapter and load the data into it
        view.alarm_list.adapter= activity?.let { AlarmAdapter(it,animals) }
      //  getactivealarm()
        view. active_back_layout.setOnClickListener { view: View ->
            activity?.finish()
        }
        return view
    }

    private fun getactivealarm() {
        val call = NectarApplication.mRetroClient!!.callActiveAlarmAPI( "Bearer "+ NectarApplication.token)
        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                Log.d("ddfgf", response.body().toString())
                progressbar_alarm.visibility= View.GONE
                var str_response = response.body()!!.toString()
                val rsp: JsonArray? = response.body() ?: return
                //   val leagueArray = JSONArray(jsonArray)

                var jsonArray = JSONArray(response.body().toString())
                // var jsonArray1 = JSONArray("data")
                // val json_contact:JSONObject = JSONObject(str_response)

                //   var jsonarray_contacts:JSONArray= json_contact.getJSONArray("contacts")


                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    var  EventTime=jsonArray.getJSONObject(jsonIndex).getString("EventTime")
                    var ProbableCause=jsonArray.getJSONObject(jsonIndex).getString("ProbableCause")
                    var ManagedElement=jsonArray.getJSONObject(jsonIndex).getString("ManagedElement")
                    var SpecificProblem=jsonArray.getJSONObject(jsonIndex).getString("SpecificProblem")
                    var AlarmHour=jsonArray.getJSONObject(jsonIndex).getString("AlarmHour")
                    Log.d("EventTime", EventTime)
                    Log.d("ProbableCause", ProbableCause)
                    Log.d("ManagedElement", ManagedElement)
                    Log.d("SpecificProblem", SpecificProblem)
                    Log.d("AlarmHour", AlarmHour)
                    data = ActiveAlarm()


                    data.setEventTime(EventTime)
                    data.setProbableCause(jsonArray.getJSONObject(jsonIndex).getString("ProbableCause"))
                    data.setManagedElement(jsonArray.getJSONObject(jsonIndex).getString("ManagedElement"))
                    data.setSpecificProblem(jsonArray.getJSONObject(jsonIndex).getString("SpecificProblem"))
                    data.setAlarmHour(jsonArray.getJSONObject(jsonIndex).getString("AlarmHour"))

                    alarmlist.add(data)
                    //{"flag":true,"msg":"Data found","info":[{"total":"49","status":"assigned"},{"total":"6","status":"escalated_tto"},{"total":"7","status":"escalated_ttr"},{"total":"21","status":"new"},{"total":"16","status":"resolved"}]}
                    //D/responbse: {"flag":true,"msg":"Data found","info":[{"total":"49","status":"assigned"},{"total":"6","status":"escalated_tto"},{"total":"7","status":"escalated_ttr"},{"total":"21","status":"new"},{"total":"16","status":"resolved"}]}
                }

                Log.d("alarmlist",""+alarmlist.size)
                view?.alarm_list!!.adapter?.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("ddfgf", "LoginFailed"+t)
                Toast.makeText(activity, "Login Failed"+t, Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun addAnimals() {
        animals.add("dog")
        animals.add("cat")
        animals.add("owl")
        animals.add("cheetah")
        animals.add("raccoon")
        animals.add("bird")
        animals.add("snake")
        animals.add("lizard")
    }
}