package com.nectar.nps.Adapter


import android.content.Context;
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

import com.nectar.nps.ActiveAlarmFragment
import com.nectar.nps.AlarmDashboardFragment
import com.nectar.nps.LiveKPIFragment
import com.nectar.nps.ResolvedAlarmAdapter
import com.nectar.nps.utils.KPIDashboardFragment
import android.os.Parcelable




class Dashboard_Adapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentStatePagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment? {

        when (position) {

            0 -> {
                return AlarmDashboardFragment()
            }
            1 -> {
                return KPIDashboardFragment()
            }
            else -> return null
        }
        Log.d("2222",""+22222)
    }
    // this counts total number of tabs
    override fun getCount(): Int {
        Log.d("totalTabs",""+totalTabs)
        return totalTabs
    }
    /*override fun saveState(): Parcelable? {
        return null
    }*/
}