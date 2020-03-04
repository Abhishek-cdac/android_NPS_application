package com.nectar.nps

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ExpandableListAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nectar.nps.Adapter.CustomExpandableListAdapter
import com.nectarinfotel.utils.NectarApplication
import kotlinx.android.synthetic.main.filterkpi_layout.*
import kotlinx.android.synthetic.main.livekpi_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject




class LiveKPIFragment : AppCompatActivity() {
  //  internal var expandableListView: ExpandableListView? = null
    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: List<String> ? = null
    lateinit var faqsView: View
    var isdialog: Boolean? =false


    private fun getlivekpilist(KPIDate: String, FromTime: String, ToTime: String) {

        var hashMap : HashMap<String, String>
                = HashMap<String, String> ()
       /* hashMap.put("FromTime" , "19")
        hashMap.put("KPIDate" , "2020-02-17")
        hashMap.put("ToTime" , "20")*/

        hashMap.put("FromTime" ,FromTime)
        hashMap.put("KPIDate" , KPIDate)
        hashMap.put("ToTime" , ToTime)
        val call = NectarApplication.mRetroClient!!.calllivekpiAPI("Bearer " + NectarApplication.token,hashMap)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                progressbar_kpi.visibility=View.GONE
                Log.d("str_responsesssssssss", response.body().toString())
                var str_response = response.body().toString()

                val rsp: JsonObject? = response.body() ?: return

                val json_contact:JsonObject =  rsp!!.asJsonObject

                var jsonArray: JsonArray = json_contact.getAsJsonArray("Table")

                val listData = HashMap<String, List<String>>()

                val Traffc = ArrayList<String>()
                for (jsonIndex in 0..(jsonArray.size() - 1)) {
                    val sObject = jsonArray.get(jsonIndex).toString()
                    val mItemObject = JSONObject(sObject)

                    val TrafficCS = mItemObject.getString("TrafficCS")
                    val Cnt = mItemObject.getString("Cnt")
                    val OrderNo = mItemObject.getString("OrderNo")

                    Traffc.add(TrafficCS)
                    Log.d("TrafficCS", TrafficCS)
                    Log.d("Cnt", Cnt)
                    Log.d("OrderNo", OrderNo)

                }

                /* val TrafficPS = ArrayList<String>()
                 var jsonArray1: JsonArray = json_contact.getAsJsonArray("Table1")
                 for (jsonIndex in 0..(jsonArray1.size() - 1)) {
                     val sObject = jsonArray1.get(jsonIndex).toString()
                     val mItemObject = JSONObject(sObject)

                     val TrafficPSvalue = mItemObject.getString("TrafficPS")
                     val Cnt = mItemObject.getString("Cnt")
                     val OrderNo = mItemObject.getString("OrderNo")

                     TrafficPS.add(TrafficPSvalue)
                     Log.d("TrafficPS", TrafficPSvalue)
                     Log.d("Cnt", Cnt)
                     Log.d("OrderNo", OrderNo)
                     listData["RRC Success (%)  Cell Count"] = TrafficPS
                 }*/

                val RRCSuccessRate = ArrayList<String>()
                var jsonArray2: JsonArray = json_contact.getAsJsonArray("Table2")
                for (jsonIndex in 0..(jsonArray2.size() - 1)) {
                    val sObject = jsonArray2.get(jsonIndex).toString()
                    val mItemObject = JSONObject(sObject)

                    val TrafficPSvalue = mItemObject.getString("RRCSuccessRate")
                    val Cnt = mItemObject.getString("Cnt")
                    val OrderNo = mItemObject.getString("OrderNo")

                    RRCSuccessRate.add(TrafficPSvalue)
                    Log.d("TrafficPS", TrafficPSvalue)
                    Log.d("Cnt", Cnt)
                    Log.d("OrderNo", OrderNo)

                }


                val Avibility = ArrayList<String>()
                var jsonArray3: JsonArray = json_contact.getAsJsonArray("Table3")
                for (jsonIndex in 0..(jsonArray3.size() - 1)) {
                    val sObject = jsonArray3.get(jsonIndex).toString()
                    val mItemObject = JSONObject(sObject)

                    val TrafficPSvalue = mItemObject.getString("Avibility")
                    val Cnt = mItemObject.getString("Cnt")
                    val OrderNo = mItemObject.getString("OrderNo")

                    Avibility.add(TrafficPSvalue)
                    Log.d("TrafficPS", TrafficPSvalue)
                    Log.d("Cnt", Cnt)
                    Log.d("OrderNo", OrderNo)

                }


                val CSSRCS = ArrayList<String>()
                var jsonArray5: JsonArray = json_contact.getAsJsonArray("Table4")
                for (jsonIndex in 0..(jsonArray5.size() - 1)) {
                    val sObject = jsonArray5.get(jsonIndex).toString()
                    val mItemObject = JSONObject(sObject)

                    val CSSRCSvalue = mItemObject.getString("CSSRCS")
                    val Cnt = mItemObject.getString("Cnt")
                    val OrderNo = mItemObject.getString("OrderNo")

                    CSSRCS.add(CSSRCSvalue)
                    Log.d("TrafficPS", CSSRCSvalue)
                    Log.d("Cnt", Cnt)
                    Log.d("OrderNo", OrderNo)

                }


                val DropCallRate = ArrayList<String>()
                var jsonArray6: JsonArray = json_contact.getAsJsonArray("Table5")
                for (jsonIndex in 0..(jsonArray6.size() - 1)) {
                    val sObject = jsonArray6.get(jsonIndex).toString()
                    val mItemObject = JSONObject(sObject)

                    val DropCallRatevalue = mItemObject.getString("DropCallRate")
                    val Cnt = mItemObject.getString("Cnt")
                    val OrderNo = mItemObject.getString("OrderNo")

                    DropCallRate.add(DropCallRatevalue)
                    Log.d("TrafficPS", DropCallRatevalue)
                    Log.d("Cnt", Cnt)
                    Log.d("OrderNo", OrderNo)

                }

                listData["Traffc CS (ERL)    Cell Count"] = Traffc
                listData["RRC Success (%)  Cell Count"] = RRCSuccessRate
                listData["Availablity (%)  Cell Coun"] = Avibility
                listData["CSSR-CS (%)    Cell Count"] = CSSRCS
                listData["DROP Call (%))   Cell Count"] = DropCallRate


                titleList = ArrayList(listData.keys)
                Log.d("titleList",""+ (titleList as ArrayList<String>).size)
                adapter = CustomExpandableListAdapter(applicationContext, titleList as ArrayList<String>, listData)
                expandableListView!!.setAdapter(adapter)
               Log.d("isdialog",""+isdialog)
                if(isdialog==true) {
                    expandableListView.expandGroup(0)
                    expandableListView.expandGroup(1)
                    expandableListView.expandGroup(2)
                    expandableListView.expandGroup(3)
                    expandableListView.expandGroup(4)
                }
                /* expandableListView!!.setOnGroupExpandListener { groupPosition -> Toast.makeText(applicationContext, (titleList as ArrayList<String>)[groupPosition] + " List Expanded.", Toast.LENGTH_SHORT).show() }

                 expandableListView!!.setOnGroupCollapseListener { groupPosition -> Toast.makeText(applicationContext, (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.", Toast.LENGTH_SHORT).show() }

                 expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                     Toast.makeText(applicationContext, "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition), Toast.LENGTH_SHORT).show()
                     true
                 }*/
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                Toast.makeText(applicationContext, "no data found ", Toast.LENGTH_SHORT).show()
            }
        })
    }


    val animals: ArrayList<String> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        setContentView(R.layout.livekpi_layout)
        getlivekpilist("2020-02-17","19","20")
        liveakpi_back_layout.setOnClickListener { view: View ->
            finish()
        }

        filter_kpi.setOnClickListener { view: View ->
            showDialog()
        }

    }
   /* override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        faqsView = inflater?.inflate(R.layout.livekpi_layout, container, false)



        if (faqsView.expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = CustomExpandableListAdapter(requireContext(), titleList as ArrayList<String>, listData)
            faqsView.expandableListView!!.setAdapter(adapter)

            faqsView.expandableListView!!.setOnGroupExpandListener { groupPosition -> Toast.makeText(activity, (titleList as ArrayList<String>)[groupPosition] + " List Expanded.", Toast.LENGTH_SHORT).show() }

            faqsView.expandableListView!!.setOnGroupCollapseListener { groupPosition -> Toast.makeText(activity, (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.", Toast.LENGTH_SHORT).show() }

            faqsView. expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                Toast.makeText(activity, "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition), Toast.LENGTH_SHORT).show()
                false
            }
        }


        return faqsView
    }*/

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDialog() {

        val dialog = Dialog(this)
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setCancelable(false)
        dialog .setContentView(R.layout.filterkpi_layout)

        dialog.date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, R.style.DialogTheme, DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->
                dialog.date.setText("" + year + "-" + (monthOfYear+1) + "-" + dayOfMonth)

            }, year, month, day)

            datePickerDialog.show()
        }
        dialog.search.setOnClickListener {

            progressbar_kpi.visibility=View.VISIBLE
            if(dialog.date.text.toString().length==0)
            {
                Toast.makeText(applicationContext, "Please select date", Toast.LENGTH_SHORT).show()
            } else    if(dialog.date.text.toString().length==0)
            {
                Toast.makeText(applicationContext, "Please enter  time", Toast.LENGTH_SHORT).show()
            }
            else    if(dialog.date.text.toString().length==0)
            {
                Toast.makeText(applicationContext, "Please enter  time", Toast.LENGTH_SHORT).show()
            }
            else{
                isdialog=true
            getlivekpilist(dialog.date.text.toString(),dialog.fromtime.text.toString(),dialog.totime.text.toString())
            dialog .dismiss() }}
        dialog.close.setOnClickListener {
            dialog .dismiss() }
        dialog .show()

    }
}