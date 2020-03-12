package com.nectar.nps

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
import com.google.gson.JsonObject
import com.nectar.nps.data.AlarmDashboard
import com.nectarinfotel.utils.NectarApplication
import kotlinx.android.synthetic.main.alarmdashboard_layout.*
import kotlinx.android.synthetic.main.alarmdashboard_layout.view.*
import kotlinx.android.synthetic.main.kpidashboard_layoit.view.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.math.roundToInt

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

        // setBarChartsitebarchrt(alatmgraph1 as ArrayList<BarEntry>, alatmgraph2 as ArrayList<String>)
        //   setBarChart(alatmgraph1 as ArrayList<BarEntry>, alatmgraph2 as ArrayList<String>)
        //  alatmgraph3.add("0")

        getsiteupdowndata()
        getalarmdata()

        return faqsView
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
                    //   data.setSiteDetailID(VenderID)
                    //  data.setSiteDetailName(SiteDetailName)
                    //   data.setTotalAlarm(TotalAlarm)

                    entries.add(BarEntry(TotalAlarm.toFloat(), jsonIndex))

                    labels.add(SiteDetailName)

                }
                // alatmgraph.add(data)

                //  setBarChart(alatmgraph1 as ArrayList<BarEntry>, alatmgraph2 as ArrayList<String>)
                setBarChart(entries , labels)
                Log.d("ddfgf", "alatmgraph "+alatmgraph.size)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                loading.visibility = View.GONE
                Log.d("str_responsefail", "LoginFailed" + t)
                //    Toast.makeText(activity, "Login Failed"+t, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getsiteupdowndata() {
        val call = NectarApplication.mRetroClient!!.callDashboardSiteupDownAPI("Bearer " + NectarApplication.token)


        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                loading_siteupdown.visibility = View.GONE
                Log.d("response", response.body().toString())
                val rsp: JsonArray? = response.body() ?: return
                //   val leagueArray = JSONArray(jsonArray)
                var jsonArray = JSONArray(response.body().toString())
                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    var Title = jsonArray.getJSONObject(jsonIndex).getString("Title")
                    var cnt = jsonArray.getJSONObject(jsonIndex).getString("cnt")
                    Log.d("Title", Title)
                    Log.d("cnt", cnt)

                    if (Title.equals("up")) {
                        siteupcount = cnt.toFloat()
                    } else if (Title.equals("down")) {
                        sitedowncount = cnt.toFloat()
                    }
                    total = siteupcount + sitedowncount
                    Log.d("siteupcount", "" + siteupcount)
                    Log.d("sitedowncount", "" + sitedowncount)
                }
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
        // faqsView.pieChartsiteupdown.setDescription("")
        faqsView.pieChartsiteupdown.animateXY(1000, 1000)
        faqsView.pieChartsiteupdown.setDrawHoleEnabled(false)
        faqsView.pieChartsiteupdown.invalidate()
        val pieLegend = faqsView.pieChartsiteupdown.getLegend()
        pieLegend.setEnabled(false)
        val arrayList = java.util.ArrayList<Entry>()
        val arrayListXAxis = java.util.ArrayList<String>()
        val NoOfEmp = java.util.ArrayList<BarEntry>()
        //Percentage of Attendance
        val siteupPercentage = siteupcount * 100 / total
        val sitedownPercentage = sitedowncount * 100 / total
        //val weekendPercentage = weekendcount * 100 / totalAttendance
        Log.d("siteupPercentage", "" + siteupPercentage)
        Log.d("sitedownPercentage", "" + sitedownPercentage)
        if (siteupPercentage.toDouble() != 0.0) {
            NoOfEmp.add(BarEntry(siteupPercentage, 0))
        }

        NoOfEmp.add(BarEntry(sitedownPercentage, 1))
        //   NoOfEmp.add(BarEntry(weekendPercentage, 2))

        arrayListXAxis.add("")
        arrayListXAxis.add("")
        arrayListXAxis.add("")
        val dataSet = PieDataSet(NoOfEmp as List<Entry>?, "Site up Down")
        // PieDataSet dataSet = new PieDataSet(arrayList, "");
        dataSet.sliceSpace = 2f
        dataSet.selectionShift = 2f

        //Color Array
        val colors = java.util.ArrayList<Int>()
        if (siteupPercentage.toDouble() != 0.0) {
            colors.add(Color.rgb(51, 204, 51))
            colors.add(Color.rgb(255, 51, 0))
            colors.add(Color.rgb(51, 204, 204))
        } else {
            colors.add(Color.rgb(51, 204, 51))
            colors.add(Color.rgb(255, 51, 0))
        }
        dataSet.colors = colors

        val piedata = PieData(arrayListXAxis, dataSet)
        piedata.setValueFormatter(PercentFormatter())
        piedata.setValueTextSize(11f)
        piedata.setValueTextColor(Color.WHITE)
        faqsView.pieChartsiteupdown.setData(piedata)
    }


    fun setBarChart(entries: ArrayList<BarEntry>, labels: ArrayList<String>) {

        val barDataSet = BarDataSet(entries, "")
        //  val data1 = BarData(labels, barDataSet)
        val data = BarData(labels, barDataSet)
        faqsView.barChart.data = data // set the data and list of lables into chart

        faqsView.barChart.setDescription("")  // set the description

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        // barDataSet.color = Color.GREEN

        faqsView.barChart.animateY(5000)
        var xaxis = XAxis()
        xaxis = faqsView.barChart.xAxis
        xaxis.setLabelRotationAngle(90F);

        xaxis.mLabelRotatedHeight=100
        xaxis.mLabelRotatedWidth=15
        //   xaxis.setLabelRotationAngle(-45F);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.spaceBetweenLabels = 1
        faqsView.barChart.getLegend().setEnabled(false);
        //    barChart.setOnChartValueSelectedListener(this)
        // barChart.setPinchZoom(true)
    }




}



private operator fun String.invoke(string: String?) {

}
