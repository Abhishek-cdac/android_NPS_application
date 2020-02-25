package com.nectar.nps

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.JsonArray
import com.nectar.nps.data.AlarmDashboard
import com.nectarinfotel.utils.NectarApplication
import kotlinx.android.synthetic.main.alarmdashboard_layout.*
import kotlinx.android.synthetic.main.alarmdashboard_layout.view.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmDashboardFragment : Fragment() {

    var VenderID: String? = ""
    internal lateinit var data: AlarmDashboard
    private var alatmgraph3: MutableList<String> = mutableListOf()
    private var alatmgraph: MutableList<AlarmDashboard> = mutableListOf()
    private var alatmgraph1: MutableList<BarEntry> = mutableListOf()
    private var alatmgraph2: MutableList<String> = mutableListOf()
   // val alatmgraph: ArrayList<AlarmDashboard> = ArrayList()
    lateinit var faqsView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        faqsView = inflater?.inflate(R.layout.alarmdashboard_layout, container, false)
        val call = NectarApplication.mRetroClient!!.callDashboardAPI( "Bearer "+ NectarApplication.token)
        Log.d("1111","1111")
        setBarChartsitebarchrt(alatmgraph1 as ArrayList<BarEntry>, alatmgraph2 as ArrayList<String>)
        setBarChart(alatmgraph1 as ArrayList<BarEntry>, alatmgraph2 as ArrayList<String>)
      /*  alatmgraph3.add("0")

        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                Log.d("ddfgf", response.body().toString())
                var str_response = response.body()!!.toString()
                val rsp: JsonArray? = response.body() ?: return
                //   val leagueArray = JSONArray(jsonArray)

                var jsonArray = JSONArray(response.body().toString())
               // var jsonArray1 = JSONArray("data")
               // val json_contact:JSONObject = JSONObject(str_response)

             //   var jsonarray_contacts:JSONArray= json_contact.getJSONArray("contacts")


                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                     VenderID=jsonArray.getJSONObject(jsonIndex).getString("VenderID")
                    var VenderName=jsonArray.getJSONObject(jsonIndex).getString("VenderName")
                    var NetworkTypeID=jsonArray.getJSONObject(jsonIndex).getString("NetworkTypeID")
                    var NetworkType=jsonArray.getJSONObject(jsonIndex).getString("NetworkType")
                    var TotalCount=jsonArray.getJSONObject(jsonIndex).getString("TotalCount")
                    Log.d("VenderID", VenderID)
                    Log.d("VenderName", VenderName)
                    Log.d("NetworkTypeID", NetworkTypeID)
                    Log.d("NetworkType", NetworkType)
                    Log.d("TotalCount", TotalCount)
                    data = AlarmDashboard()
                    alatmgraph2.add(VenderName)

                    data.setVenderID(VenderID)
                    data.setVenderName(jsonArray.getJSONObject(jsonIndex).getString("VenderName"))
                    data.setNetworkTypeID(jsonArray.getJSONObject(jsonIndex).getString("NetworkTypeID"))
                    data.setNetworkType(jsonArray.getJSONObject(jsonIndex).getString("NetworkType"))
                    data.setTotalCount(jsonArray.getJSONObject(jsonIndex).getString("TotalCount"))


                    //{"flag":true,"msg":"Data found","info":[{"total":"49","status":"assigned"},{"total":"6","status":"escalated_tto"},{"total":"7","status":"escalated_ttr"},{"total":"21","status":"new"},{"total":"16","status":"resolved"}]}
                    //D/responbse: {"flag":true,"msg":"Data found","info":[{"total":"49","status":"assigned"},{"total":"6","status":"escalated_tto"},{"total":"7","status":"escalated_ttr"},{"total":"21","status":"new"},{"total":"16","status":"resolved"}]}
                }
                alatmgraph.add(data)
               // alatmgraph1.add(BarEntry(data))
             //  setBarValuesForDepartment( alatmgraph2 as ArrayList<String>)
                setBarChart(alatmgraph1 as ArrayList<BarEntry>, alatmgraph2 as ArrayList<String>)
                setBarChartsitebarchrt(alatmgraph1 as ArrayList<BarEntry>, alatmgraph2 as ArrayList<String>)
                Log.d("ddfgf", "alatmgraph "+alatmgraph.size)
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("ddfgf", "LoginFailed"+t)
                Toast.makeText(activity, "Login Failed"+t, Toast.LENGTH_SHORT).show()
            }
        })*/
        return faqsView
    }
    private fun setBarValuesForDepartment(info: List<String>) {
        if(info.isEmpty()) return
        var totalAgentTicket : Int = 0

        for((start, departmentObj) in info.withIndex()){


            val total = info.lastIndex.toFloat()
            alatmgraph1.add(BarEntry(total,start))
            totalAgentTicket += total.toInt()


        }}
    fun setBarChart(entries: ArrayList<BarEntry>, labels: ArrayList<String>) {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(8f, 0))
        entries.add(BarEntry(2f, 1))
        entries.add(BarEntry(5f, 2))
        entries.add(BarEntry(20f, 3))
        entries.add(BarEntry(15f, 4))
        entries.add(BarEntry(19f, 5))

        val labels = ArrayList<String>()
        labels.add("18-Jan")
        labels.add("19-Jan")
        labels.add("20-Jan")
        labels.add("21-Jan")
        labels.add("22-Jan")
        labels.add("23-Jan")

        val barDataSet = BarDataSet(entries, "")
      //  val data1 = BarData(labels, barDataSet)
        val data = BarData(labels, barDataSet)
        faqsView.  barChart.data = data // set the data and list of lables into chart

        faqsView.barChart.setDescription("")  // set the description

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        // barDataSet.color = Color.BLUE

        faqsView. barChart.animateY(5000)
        var xaxis = XAxis()
        xaxis= faqsView. barChart.xAxis
     //   xaxis.setLabelRotationAngle(90F);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // barChart.data.setValueFormatter( PercentFormatter());
        xaxis.spaceBetweenLabels=1
        faqsView. barChart.getLegend().setEnabled(false);
        //    barChart.setOnChartValueSelectedListener(this)
        // barChart.setPinchZoom(true)
    }

    fun setBarChartsitebarchrt(entries: ArrayList<BarEntry>, labels: ArrayList<String>) {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(150f, 0))
        entries.add(BarEntry(180f, 1))
        entries.add(BarEntry(190f, 2))
        entries.add(BarEntry(200f, 3))
        entries.add(BarEntry(220f, 4))
        entries.add(BarEntry(240f, 5))

        val labels = ArrayList<String>()
        labels.add("18-Jan")
        labels.add("19-Jan")
        labels.add("20-Jan")
        labels.add("21-Jan")
        labels.add("22-Jan")
        labels.add("23-Jan")

        val barDataSet = BarDataSet(entries, "")
        //  val data1 = BarData(labels, barDataSet)
        val data = BarData(labels, barDataSet)
        faqsView. barChart_sitedown.data = data // set the data and list of lables into chart

       // faqsView.barChart.setDescription("")  // set the description

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        // barDataSet.color = Color.BLUE

        faqsView.  barChart_sitedown.animateY(5000)
        var xaxis = XAxis()
        xaxis= faqsView. barChart_sitedown.xAxis
    //    xaxis.setLabelRotationAngle(90F);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // barChart.data.setValueFormatter( PercentFormatter());
        xaxis.spaceBetweenLabels=1
        faqsView.  barChart_sitedown.getLegend().setEnabled(false);
        //    barChart.setOnChartValueSelectedListener(this)
        // barChart.setPinchZoom(true)
    }



}

private operator fun String.invoke(string: String?) {

}
