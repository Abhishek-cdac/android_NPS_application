package com.nectar.nps

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nectar.nps.data.AlarmDashboard
import com.nectar.nps.retrofit.RetroAPIInterface
import com.nectar.nps.utils.MyValueFormatter
import com.nectarinfotel.utils.NectarApplication
import kotlinx.android.synthetic.main.alarmdashboard_layout.*
import kotlinx.android.synthetic.main.alarmdashboard_layout.view.*
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmDashboardFragment : Fragment(), OnChartValueSelectedListener {
    companion object {
        var xvalue: String = ""
    }

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
    val labels1 = ArrayList<String>()
    lateinit var faqsView: View
    val client = OkHttpClient()
    var isred: Boolean = false
    var isgreen: Boolean = false
    var situp: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        barWidth = 0.70f

        // draw the graph
        var barDataSet1: BarDataSet

        barDataSet1 = BarDataSet(entries, "")
        barDataSet1.setColors(
            Color.RED,
            ContextCompat.getColor(requireContext(), R.color.light_green)
        )
        /* if(isred==false)
        {
            barDataSet1.setColors(Color.RED, Color.GREEN)
        }else
        {
            barDataSet1.setColors(Color.RED, Color.RED)
        }*/

        barDataSet1.label = "2016"
        barDataSet1.setDrawIcons(false)
        faqsView.barChart.setDrawValueAboveBar(false);
        barDataSet1.setDrawValues(true);
        var barData = BarData(barDataSet1)

        faqsView.barChart.description.isEnabled = false
        faqsView.barChart.description.textSize = 0f
        barData.setValueFormatter(MyValueFormatter())
        barData.setValueTextColor(Color.WHITE)
        faqsView.barChart.setData(barData)
        faqsView.barChart.getBarData().setBarWidth(barWidth)
        faqsView.barChart.getXAxis().setAxisMinimum(0f)
        faqsView.barChart.getXAxis().setAxisMaximum(7f)
        faqsView.barChart.getData().setHighlightEnabled(true)
        faqsView.barChart.invalidate()

        // set bar label
        var legend = faqsView.barChart.legend

        var legenedEntries = arrayListOf<LegendEntry>()

        legenedEntries.add(
            LegendEntry(
                "Site Down",
                Legend.LegendForm.SQUARE,
                8f,
                8f,
                null,
                Color.RED
            )
        )
        legenedEntries.add(
            LegendEntry(
                "Site Up",
                Legend.LegendForm.SQUARE,
                8f,
                8f,
                null,
                Color.GREEN
            )
        )

        legend.setCustom(legenedEntries)

        legend.setYOffset(2f)
        legend.setXOffset(2f)
        legend.setYEntrySpace(0f)
        legend.setTextSize(5f)
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        legend.setDrawInside(false)

        val xAxis = faqsView.barChart.getXAxis()
        xAxis.setGranularity(1f)
        xAxis.setGranularityEnabled(true)
        xAxis.setCenterAxisLabels(false)
        xAxis.setDrawGridLines(false)
        xAxis.textSize = 9f

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setValueFormatter(IndexAxisValueFormatter(labels))
        //  xAxis.setValueFormatter(IAxisValueFormatter(40,labels))
        xAxis.setLabelCount(7)
        xAxis.mAxisMaximum = 7f
        // xAxis.mAxisMinimum=-1f
        xAxis.setAvoidFirstLastClipping(false)
        xAxis.setDrawLabels(true)
        xAxis.spaceMin = 0f
        xAxis.spaceMax = 0f

        /* faqsView.barChart.setXAxisRenderer(
            DoubleXLabelAxisRenderer(
                faqsView.barChart.viewPortHandler,
                faqsView.barChart.xAxis,
                faqsView.barChart.getTransformer(YAxis.AxisDependency.LEFT),
                IAxisValueFormatter { labels, axis -> labels1 })
        )*/
        xAxis.setAxisMinimum(barData.xMin - 0.5f);
        xAxis.setAxisMaximum(barData.xMax + 0.5f);

        xAxis.setLabelRotationAngle(90F);

        xAxis.mLabelRotatedHeight = 100
        xAxis.mLabelRotatedWidth = 15

        faqsView.barChart.setVisibleXRangeMaximum(7f)
        faqsView.barChart.setVisibleXRangeMinimum(7f)
        faqsView.barChart.setDragEnabled(true)

        //Y-axis
        faqsView.barChart.getAxisRight().setEnabled(false)
        faqsView.barChart.setScaleEnabled(true)

        val leftAxis = faqsView.barChart.getAxisLeft()
        //  leftAxis.setValueFormatter(LargeValueFormatter())
        leftAxis.setDrawGridLines(false)
        leftAxis.setSpaceTop(0f)
        leftAxis.setAxisMinimum(0f)
        leftAxis.setAxisMinimum(0f)
        faqsView.barChart.data = barData
        faqsView.barChart.setVisibleXRange(0f, 7f)

        faqsView.barChart.setOnChartValueSelectedListener(this)
    }
    private fun getsiteupdowndata() {
        val call =
            NectarApplication.mRetroClient!!.callDashboardSiteupDownAPI("Bearer " + NectarApplication.token)

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
        val piedata = PieData(dataSet)
        piedata.setValueFormatter(PercentFormatter())
        piedata.setValueTextSize(11f)
        piedata.setValueTextColor(Color.WHITE)
        faqsView.pieChartsiteupdown.setData(piedata)
    }

    override fun onNothingSelected() {

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.i("selected value", e.toString())
        val x:Float= e!!.x
        Log.i(" xvalue", ""+x)
        xvalue=labels.get(x.toInt())
        Log.i(" getvalue", xvalue)
        startActivity(Intent(activity, DashboardAlarmList::class.java))

    }


    private fun getalarmdata() {
        val call =
            NectarApplication.mRetroClient!!.callDashboardAPI("Bearer " + NectarApplication.token)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                loading.visibility = View.GONE
                Log.d("str_response", response.body().toString())
                var str_response = response.body().toString()

                val rsp: JsonObject? = response.body() ?: return

                val json_contact: JsonObject = rsp!!.asJsonObject

                var jsonArray: JsonArray = json_contact.getAsJsonArray("Table")
                var jsonArray1: JsonArray = json_contact.getAsJsonArray("Table1")
                var jsonArray2: JsonArray = json_contact.getAsJsonArray("Table2")
                val sObject = jsonArray2.get(0).toString()
                val mItemObject = JSONObject(sObject)
                val UploadDate = mItemObject.getString("UploadDate")
                datetime.text = "Last updated on " + UploadDate

                for (jsonIndex in 0..(jsonArray.size() - 1)) {
                    val sObject = jsonArray.get(jsonIndex).toString()
                    val mItemObject = JSONObject(sObject)

                    val SiteDetailID = mItemObject.getString("SiteDetailID")
                    val SiteDetailName = mItemObject.getString("SiteDetailName")
                    val TotalAlarm = mItemObject.getString("TotalAlarm")
                    Log.d("SiteDetailID", SiteDetailID)
                    Log.d("SiteDetailName", SiteDetailName)
                    Log.d("TotalAlarm", TotalAlarm)
                    Log.d("jsonArray1", "" + jsonArray1.size())
                    Log.d("jsonArray", "" + jsonArray.size())
                    if (jsonArray1.size() <= jsonArray.size()) {
                        if (jsonIndex < jsonArray1.size()) {
                            val sObject = jsonArray1.get(jsonIndex).toString()
                            val mItemObject = JSONObject(sObject)
                            if (mItemObject.has("SiteDetailID")) {
                                val SiteDetailID1 = mItemObject.getString("SiteDetailID")
                                val SiteDetailName1 = mItemObject.getString("SiteDetailName")
                                val sitedown = mItemObject.getString("ToatlCnt")
                                situp = TotalAlarm.toInt() - sitedown.toInt()
                                Log.d("SiteDetailID1", SiteDetailID1)
                                Log.d("SiteDetailName1", SiteDetailName1)
                                Log.d("TotalAlarm1", sitedown)
                                Log.d("situp", "" + situp)
                                // isred=false
                                entries.add(
                                    BarEntry(
                                        jsonIndex.toFloat(),
                                        floatArrayOf(sitedown.toFloat(), situp.toFloat())
                                    )
                                )
                            } else {

                                //isred=true
                                entries.add(
                                    BarEntry(
                                        jsonIndex.toFloat(),
                                        floatArrayOf(TotalAlarm.toFloat(), situp.toFloat())
                                    )
                                )
                            }
                        } else {

                            // isred=false
                            entries.add(
                                BarEntry(
                                    jsonIndex.toFloat(),
                                    floatArrayOf(TotalAlarm.toFloat(), situp.toFloat())
                                )
                            )
                        }

                    }

                    labels.add(SiteDetailName)
                    labels1.add(TotalAlarm)
                }


                //set bar graph
                populateGraphData(entries, labels)

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                loading.visibility = View.GONE
                Log.d("str_responsefail", "str_responsefail" + t)

            }
        })


}}





