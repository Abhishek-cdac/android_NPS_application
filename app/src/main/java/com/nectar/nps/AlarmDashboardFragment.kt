package com.nectar.nps

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nectar.nps.data.AlarmDashboard
import com.nectarinfotel.utils.NectarApplication
import kotlinx.android.synthetic.main.alarmdashboard_layout.*
import kotlinx.android.synthetic.main.alarmdashboard_layout.view.*
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmDashboardFragment : Fragment() {
    internal var total: Float = 0.toFloat()
    internal var siteupcount: Float = 0.toFloat()
    internal var sitedowncount: Float = 0.toFloat()
    var VenderID: String? = ""
    internal lateinit var data: AlarmDashboard
    private var alatmgraph3: MutableList<String> = mutableListOf()
    private var alatmgraph: MutableList<AlarmDashboard> = mutableListOf()
    private var alatmgraph1: MutableList<BarEntry> = mutableListOf()
    private var alatmgraph2: MutableList<String> = mutableListOf()
    val entries = ArrayList<BarEntry>()
    val labels = ArrayList<String>()
    lateinit var faqsView: View
    val client = OkHttpClient()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        faqsView = inflater?.inflate(R.layout.alarmdashboard_layout, container, false)

      //method for Alarm up down chart
        getsiteupdowndata()

        //method for siteupdown chart
        getalarmdata()


        return faqsView
    }

    fun populateGraphData(
        entries: ArrayList<BarEntry>,
        labels: ArrayList<String>
    ) {


        val barWidth: Float

        barWidth = 0.50f

        // draw the graph
        var barDataSet1: BarDataSet


        barDataSet1 = BarDataSet(entries, "")
        barDataSet1.setColors(Color.RED, Color.GREEN)
        barDataSet1.label = "2016"
        barDataSet1.setDrawIcons(false)
        faqsView.barChart.setDrawValueAboveBar(false);
        barDataSet1.setDrawValues(true);


        var barData = BarData(barDataSet1)

        faqsView.barChart.description.isEnabled = false
        faqsView.barChart.description.textSize = 0f
        barData.setValueFormatter(LargeValueFormatter())
        faqsView.barChart.setData(barData)
        faqsView.barChart.getBarData().setBarWidth(barWidth)
        faqsView.barChart.getXAxis().setAxisMinimum(0f)
        faqsView.barChart.getXAxis().setAxisMaximum(7f)
        faqsView.barChart.getData().setHighlightEnabled(true)
        faqsView.barChart.invalidate()

        // set bar label
        var legend = faqsView.barChart.legend
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        legend.setDrawInside(false)

         val xAxis = faqsView.barChart.getXAxis()
         xAxis.setGranularity(1f)
        xAxis.setGranularityEnabled(true)
        xAxis.setCenterAxisLabels(true)
        xAxis.setDrawGridLines(false)
        xAxis.textSize = 9f

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setValueFormatter(IndexAxisValueFormatter(labels))

        xAxis.setLabelCount(7)
        xAxis.mAxisMaximum = 7f
        xAxis.setCenterAxisLabels(true)
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.spaceMin = 1f
        xAxis.spaceMax = 4f
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(barData.xMin - 0.5f);
        xAxis.setAxisMaximum(barData.xMax + 0.5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        faqsView.barChart.setVisibleXRangeMaximum(6f)
        faqsView.barChart.setVisibleXRangeMinimum(6f)
        faqsView.barChart.setDragEnabled(true)

        //Y-axis
        faqsView.barChart.getAxisRight().setEnabled(false)
        faqsView.barChart.setScaleEnabled(true)

        val leftAxis = faqsView.barChart.getAxisLeft()
      //  leftAxis.setValueFormatter(LargeValueFormatter())
        leftAxis.setDrawGridLines(false)
        leftAxis.setSpaceTop(0f)
        leftAxis.setAxisMinimum(0f)


        faqsView.barChart.data = barData
        faqsView.barChart.setVisibleXRange(0f, 7f)
    }



    private fun getalarmdata() {
        val call = NectarApplication.mRetroClient!!.callDashboardAPI("Bearer " + NectarApplication.token)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                loading.visibility = View.GONE
                Log.d("str_response", response.body().toString())
                var str_response = response.body().toString()

                val rsp: JsonObject? = response.body() ?: return

                val json_contact:JsonObject =  rsp!!.asJsonObject

                var jsonArray:JsonArray= json_contact.getAsJsonArray("Table")
                var jsonArray1:JsonArray= json_contact.getAsJsonArray("Table2")

                for (jsonIndex in 0..(jsonArray.size() - 1)) {
                    val sObject = jsonArray.get(jsonIndex).toString()
                    val mItemObject = JSONObject(sObject)

                    val SiteDetailID = mItemObject.getString("SiteDetailID")
                    val SiteDetailName = mItemObject.getString("SiteDetailName")
                    val TotalAlarm = mItemObject.getString("TotalAlarm")
                    Log.d("SiteDetailID", SiteDetailID)
                    Log.d("SiteDetailName", SiteDetailName)
                    Log.d("TotalAlarm", TotalAlarm)

                    data = AlarmDashboard()
                    entries.add(BarEntry(jsonIndex.toFloat(), floatArrayOf(60.toFloat(), TotalAlarm.toFloat())))
                    labels.add(SiteDetailName)

                }
                //set bar graph
                populateGraphData(entries , labels)

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                loading.visibility = View.GONE
                Log.d("str_responsefail", "str_responsefail" + t)

            }
        })
    }

    private fun getsiteupdowndata() {
        val call = NectarApplication.mRetroClient!!.callDashboardSiteupDownAPI("Bearer " + NectarApplication.token)


        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                loading_siteupdown.visibility = View.GONE
                Log.d("site_response", response.body().toString())
                val rsp: JsonArray? = response.body() ?: return
                var jsonArray = JSONArray(response.body().toString())
                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    var Title = jsonArray.getJSONObject(jsonIndex).getString("Title")
                    var cnt = jsonArray.getJSONObject(jsonIndex).getString("cnt")
                    if (Title.equals("up")) {
                        siteupcount = cnt.toFloat()
                    } else if (Title.equals("down")) {
                        sitedowncount = cnt.toFloat()
                    }
                    total = siteupcount + sitedowncount
                    Log.d("siteupcount", "" + siteupcount)
                    Log.d("sitedowncount", "" + sitedowncount)
                }
                //draw pie chart
                drawpiechart()
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("response", "LoginFailed" + t)
                loading_siteupdown.visibility = View.GONE
                Toast.makeText(activity, "try again" + t, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun drawpiechart() {
        faqsView.pieChartsiteupdown.setVisibility(View.VISIBLE)
        faqsView.pieChartsiteupdown.setPressed(false)
        faqsView.pieChartsiteupdown.setUsePercentValues(true)
        faqsView.pieChartsiteupdown.setRotationEnabled(false)
        faqsView.pieChartsiteupdown.setHoleRadius(0f)
        faqsView.pieChartsiteupdown.animateXY(1000, 1000)
        faqsView.pieChartsiteupdown.setDrawHoleEnabled(false)
        faqsView.pieChartsiteupdown.invalidate()
        val pieLegend = faqsView.pieChartsiteupdown.getLegend()
        pieLegend.setEnabled(false)
        val arrayList = java.util.ArrayList<Entry>()
        val arrayListXAxis = java.util.ArrayList<String>()
         var NoOfEmp: MutableList<PieEntry> = mutableListOf()
        //Percentage of Attendance
        val siteupPercentage = siteupcount * 100 / total
        val sitedownPercentage = sitedowncount * 100 / total
        if (siteupPercentage.toDouble() != 0.0) {
            NoOfEmp.add(PieEntry(siteupPercentage, 0))
        }
        NoOfEmp.add(PieEntry(sitedownPercentage, 1))
        arrayListXAxis.add("")
        arrayListXAxis.add("")
        arrayListXAxis.add("")
        val dataSet = PieDataSet(NoOfEmp as MutableList<PieEntry>?, "Site up Down")
        dataSet.sliceSpace = 2f
        dataSet.selectionShift = 2f

        //Color Array
        val colors = java.util.ArrayList<Int>()
        if (siteupPercentage.toDouble() != 0.0) {
            colors.add(Color.rgb(51, 204, 51))
            colors.add(Color.rgb(255, 51, 0))
            colors.add(Color.rgb(51, 204, 204))
        } else {
            colors.add(Color.rgb(255, 51, 0))
            colors.add(Color.rgb(51, 204, 204))
        }
        dataSet.colors = colors
        val piedata = PieData( dataSet)
        piedata.setValueFormatter(PercentFormatter())
        piedata.setValueTextSize(11f)
        piedata.setValueTextColor(Color.WHITE)
        faqsView.pieChartsiteupdown.setData(piedata)
    }

}



private operator fun String.invoke(string: String?) {

}
