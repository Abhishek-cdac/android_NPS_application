package com.nectar.nps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nectar.nps.Adapter.CustomExpandableListAdapter
import kotlinx.android.synthetic.main.livealarmdashboard_layout.*
import kotlinx.android.synthetic.main.livekpi_layout.*
import kotlinx.android.synthetic.main.livekpi_layout.view.*
import kotlinx.android.synthetic.main.livekpi_layout.view.liveakpi_back_layout


class LiveKPIFragment : AppCompatActivity() {
  //  internal var expandableListView: ExpandableListView? = null
    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: List<String> ? = null
    lateinit var faqsView: View
    val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            val redmiMobiles = ArrayList<String>()
            redmiMobiles.add("Redmi Y2")
            redmiMobiles.add("Redmi S2")
            redmiMobiles.add("Redmi Note 5 Pro")
            redmiMobiles.add("Redmi Note 5")
            redmiMobiles.add("Redmi 5 Plus")
            redmiMobiles.add("Redmi Y1")
            redmiMobiles.add("Redmi 3S Plus")

            val micromaxMobiles = ArrayList<String>()
            micromaxMobiles.add("Micromax Bharat Go")
            micromaxMobiles.add("Micromax Bharat 5 Pro")
            micromaxMobiles.add("Micromax Bharat 5")
            micromaxMobiles.add("Micromax Canvas 1")
            micromaxMobiles.add("Micromax Dual 5")

            val appleMobiles = ArrayList<String>()
            appleMobiles.add("iPhone 8")
            appleMobiles.add("iPhone 8 Plus")
            appleMobiles.add("iPhone X")
            appleMobiles.add("iPhone 7 Plus")
            appleMobiles.add("iPhone 7")
            appleMobiles.add("iPhone 6 Plus")

            val samsungMobiles = ArrayList<String>()
            samsungMobiles.add("Samsung Galaxy S9+")
            samsungMobiles.add("Samsung Galaxy Note 7")
            samsungMobiles.add("Samsung Galaxy Note 5 Dual")
            samsungMobiles.add("Samsung Galaxy S8")
            samsungMobiles.add("Samsung Galaxy A8")
            samsungMobiles.add("Samsung Galaxy Note 4")

            listData["Traffc CS (ERL)          Cell Count"] = redmiMobiles
            listData["Traffc PS (MB)          Cell Count"] = micromaxMobiles
            listData["RRC Success (%)  Cell Count"] = appleMobiles
            listData["Availablity (%)  Cell Count\""] = samsungMobiles
            listData["HOSR (%)         Cell Count"] = redmiMobiles
            listData["HS Accessiblity (%)          Cell Count"] = micromaxMobiles
            listData["CSSR-CS (%)    Cell Count"] = appleMobiles
            listData["CSSR-PS  (%) Cell Count"] = samsungMobiles
            listData["DROP Call (%))          Cell Count"] = redmiMobiles
            listData["PS ENT EUL DROP RATE (%)      Cell Count"] = micromaxMobiles
            listData["PS ENT EUL SUMMARY  DROP RATE (%)   Cell Count"] = appleMobiles
            listData["PS ENT HS & EUL SUMMARY  DROP RATE (%) Cell Count\""] = samsungMobiles
            listData["PS RRC SETUP  (%)        Cell Count"] = redmiMobiles

            return listData
        }


    val animals: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        setContentView(R.layout.livekpi_layout)

        liveakpi_back_layout.setOnClickListener { view: View ->
            finish()
        }
        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = CustomExpandableListAdapter(applicationContext, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)

            expandableListView!!.setOnGroupExpandListener { groupPosition -> Toast.makeText(applicationContext, (titleList as ArrayList<String>)[groupPosition] + " List Expanded.", Toast.LENGTH_SHORT).show() }

            expandableListView!!.setOnGroupCollapseListener { groupPosition -> Toast.makeText(applicationContext, (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.", Toast.LENGTH_SHORT).show() }

             expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                Toast.makeText(applicationContext, "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition), Toast.LENGTH_SHORT).show()
                false
            }
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


}