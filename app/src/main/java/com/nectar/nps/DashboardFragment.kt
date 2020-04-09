package com.nectar.nps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.nectar.nps.Adapter.Dashboard_Adapter
import kotlinx.android.synthetic.main.alarm_details_layout.view.*

class DashboardFragment : Fragment() {
    lateinit var faqsView: View
    private lateinit var tabLayout: TabLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        faqsView = inflater?.inflate(R.layout.alarm_details_layout, container, false)

        tabLayout = faqsView.findViewById(R.id.tabLayout) as TabLayout
        faqsView.tabLayout!!.addTab(faqsView.tabLayout!!.newTab().setText("Alarm"))
        faqsView.tabLayout!!.addTab(faqsView.tabLayout!!.newTab().setText("KPI"))
        faqsView.tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = activity?.supportFragmentManager?.let {
            Dashboard_Adapter(
                requireContext(),
                it,
                faqsView.tabLayout!!.tabCount
            )

        }

        faqsView.viewPager!!.adapter = adapter

        faqsView.viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(faqsView.tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                faqsView.viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {


            }
        })


        return faqsView
    }
}