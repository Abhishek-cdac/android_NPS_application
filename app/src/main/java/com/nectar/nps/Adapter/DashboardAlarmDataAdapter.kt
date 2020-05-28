package com.nectar.nps.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nectar.nps.R
import com.nectar.nps.data.ActiveAlarm
import com.nectar.nps.data.AlarmDashboard
import kotlinx.android.synthetic.main.alarm_type_item_layout.view.*

class DashboardAlarmDataAdapter (private val context: Context, private val alarmlist: MutableList<AlarmDashboard>) :
    RecyclerView.Adapter<DashboardAlarmDataAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder(LayoutInflater.from(context).inflate(R.layout.alarm_type_item_layout, parent, false))
}
    override fun getItemCount(): Int {
        return alarmlist.size

}
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(alarmlist[position])
        holder.itemView.setOnClickListener {
    }
}
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(items: AlarmDashboard) {
            itemView.managed_element.visibility=View.GONE
            itemView.venderName.visibility=View.GONE
            itemView.problem_cause.visibility=View.GONE


            itemView.hours.text = "Type :"+items.perceivedSeverity
            if(items.perceivedSeverity.equals("Critical"))
            {
                itemView.hours.setTextColor(Color.rgb(223,11,11))
            } else if(items.perceivedSeverity.equals("Major"))
            {
                itemView.hours.setTextColor(Color.rgb(223,117,11))
            }
            else if(items.perceivedSeverity.equals("Minor"))
            {
                itemView.hours.setTextColor(Color.rgb(223,11,68))
            }

            itemView.SpecificProblem.text = "SpecificProblem :"+items.specificProblem
            itemView.SiteName.text ="Site Name :"+ items.siteName

        }
}
}