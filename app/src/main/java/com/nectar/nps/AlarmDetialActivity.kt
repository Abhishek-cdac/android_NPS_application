package com.nectar.nps

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.nectar.nps.Adapter.AlarmAdapter
import com.nectar.nps.Adapter.Alarmtype_Adapter
import com.nectar.nps.data.ActiveAlarm
import kotlinx.android.synthetic.main.alarm_details_layout.*
import kotlinx.android.synthetic.main.alarm_item_layout.*
import kotlinx.android.synthetic.main.alarm_item_layout.view.*
import kotlinx.android.synthetic.main.alarm_item_layout.view.alarm_list
import kotlinx.android.synthetic.main.login_layout.*

class AlarmDetialActivity:AppCompatActivity() {
    internal var textlength = 0
    internal lateinit var data: ActiveAlarm
    val animals: ArrayList<String> = ArrayList()
    val animals1: ArrayList<String> = ArrayList()
    fun addAnimals() {

        animals.add("Alarm 1")
        animals.add("Alarm 2")
        animals.add("Alarm 3")
        animals.add("Alarm 4")
        animals.add("Alarm 5")
        animals.add("Alarm 6")
        animals.add("Alarm 7")
        animals.add("Alarm 8")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.alarm_item_layout)
     //   tabLayout = findViewById(R.id.tabLayout) as TabLayout
       /* tabLayout!!.addTab(tabLayout!!.newTab().setText("Active Alarm"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Resolved Alarm"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = Alarmtype_Adapter(this, supportFragmentManager, tabLayout!!.tabCount)
       viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {


            }
        })*/
        addAnimals()
        // Creates a vertical Layout Manager
        alarm_list.layoutManager = LinearLayoutManager(applicationContext)


        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        // rv_animal_list.layoutManager = GridLayoutManager(this, 2)
        // Access the RecyclerView Adapter and load the data into it
        alarm_list.adapter= applicationContext?.let { AlarmAdapter(it,animals) }
        //  getactivealarm()
         active_back_layout.setOnClickListener { view: View ->
            finish()
        }
       /* edittext!!.addTextChangedListener(object : TextWatcher {


            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                textlength = edittext!!.text.length
                animals1.clear()
                for (i in animals.indices) {
                    if (textlength <= animals[i].length) {
                        Log.d("ertyyy", animals[i].toLowerCase().trim())
                        if (animals[i].toLowerCase().trim().contains(
                                edittext!!.text.toString().toLowerCase().trim { it <= ' ' })
                        ) {
                            animals1.add(animals[i])
                        }
                    }
                }
                alarm_list.adapter= applicationContext?.let { AlarmAdapter(it,animals1) }

            }
        })*/
    }
}