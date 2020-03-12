package com.nectar.nps.utils

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.JsonArray
import com.nectar.nps.R
import com.nectar.nps.data.AlarmDashboard
import com.nectarinfotel.utils.NectarApplication
import kotlinx.android.synthetic.main.alarmdashboard_layout.*
import kotlinx.android.synthetic.main.alarmdashboard_layout.view.*
import kotlinx.android.synthetic.main.kpidashboard_layoit.*
import kotlinx.android.synthetic.main.kpidashboard_layoit.view.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class KPIDashboardFragment  : Fragment() {
    internal var totalAttendance: Float = 0.toFloat()
    internal var presentCount: Float = 0.toFloat()
    internal var weekendcount: Float = 0.toFloat()
    internal var absentCount: Float = 0.toFloat()
    var VenderID: String? = ""
    internal lateinit var data: AlarmDashboard
    private var alatmgraph3: MutableList<String> = mutableListOf()
    private var alatmgraph: MutableList<AlarmDashboard> = mutableListOf()
    private var alatmgraph1: MutableList<BarEntry> = mutableListOf()
    private var alatmgraph2: MutableList<String> = mutableListOf()
    // val alatmgraph: ArrayList<AlarmDashboard> = ArrayList()
    lateinit var faqsView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        faqsView = inflater?.inflate(R.layout.kpidashboard_layoit, container, false)
        val call = NectarApplication.mRetroClient!!.callDashboardAPI("Bearer " + NectarApplication.token)

        alatmgraph3.add("0")
        totalAttendance = 40.0.toFloat()
        presentCount = 40.0.toFloat()
        weekendcount = 40.0.toFloat()
        absentCount = 40.0.toFloat()
        //drawpiechart()
        /*  alatmgraph2.add("1")
          alatmgraph2.add("2")
          alatmgraph2.add("3")
          alatmgraph2.add("4")*/


        /* call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                Log.d("ddfgf", response.body().toString())
                val rsp: JsonArray? = response.body() ?: return
                //   val leagueArray = JSONArray(jsonArray)
                var jsonArray = JSONArray(response.body().toString())
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

                    totalAttendance=40.0.toFloat()
                    presentCount=40.0.toFloat()
                    weekendcount=40.0.toFloat()
                    absentCount=40.0.toFloat()
                }
                alatmgraph.add(data)
                drawpiechart()
                // alatmgraph1.add(BarEntry(data))
                //  setBarValuesForDepartment( alatmgraph2 as ArrayList<String>)
              //  setBarChart(alatmgraph1 as ArrayList<BarEntry>, alatmgraph2 as ArrayList<String>)
              //  setBarChartsitebarchrt(alatmgraph1 as ArrayList<BarEntry>, alatmgraph2 as ArrayList<String>)
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


        /* private fun drawpiechart() {
        faqsView. pieChartAttendance.setVisibility(View.VISIBLE)
        faqsView. pieChartAttendance.setPressed(false)
        faqsView.pieChartAttendance.setUsePercentValues(true)
        faqsView. pieChartAttendance.setRotationEnabled(false)
        faqsView. pieChartAttendance.setHoleRadius(0f)
        // pieChartAttendance.getDescription().setText("  ");
        faqsView. pieChartAttendance.animateXY(1000, 1000)
        faqsView. pieChartAttendance.setDrawHoleEnabled(false)
        faqsView. pieChartAttendance.invalidate()
        val pieLegend = faqsView.pieChartAttendance.getLegend()
        pieLegend.setEnabled(false)
        val arrayList = java.util.ArrayList<Entry>()
        val arrayListXAxis = java.util.ArrayList<String>()
        val NoOfEmp = ArrayList<BarEntry>()
        //Percentage of Attendance
        val presentPercentage = presentCount * 100 / totalAttendance
        val absentPercentage = absentCount * 100 / totalAttendance
        val weekendPercentage = weekendcount * 100 / totalAttendance

        if (presentPercentage.toDouble() != 0.0) {
            NoOfEmp.add(BarEntry(presentPercentage, 0))
        }

        NoOfEmp.add(BarEntry(absentPercentage, 1))
        NoOfEmp.add(BarEntry(weekendPercentage, 2))

        arrayListXAxis.add("")
        arrayListXAxis.add("")
        arrayListXAxis.add("")
        val dataSet = PieDataSet(NoOfEmp as List<Entry>?, "Number Of Employees")
        // PieDataSet dataSet = new PieDataSet(arrayList, "");
        dataSet.sliceSpace = 2f
        dataSet.selectionShift = 2f

        //Color Array
        val colors = java.util.ArrayList<Int>()
        if (presentPercentage.toDouble() != 0.0) {
            colors.add(Color.rgb(155, 192, 28))
            colors.add(Color.rgb(250, 103, 117))
            colors.add(Color.rgb(51, 204, 204))
        } else {
            colors.add(Color.rgb(250, 103, 117))
            colors.add(Color.rgb(51, 204, 204))
        }
        dataSet.colors = colors

        val piedata = PieData(arrayListXAxis, dataSet)
        piedata.setValueFormatter(PercentFormatter())
        piedata.setValueTextSize(11f)
        piedata.setValueTextColor(Color.WHITE)
        faqsView. pieChartAttendance.setData(piedata)
    }*/
    }

}
