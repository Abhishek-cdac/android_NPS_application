package com.nectar.nps.Adapter

import android.content.Context
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

class AlarmAdapter (private val context: Context, private val alarmlist: MutableList<AlarmDashboard>) :
    RecyclerView.Adapter<AlarmAdapter.ViewHolder>()
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
        //Toast.makeText(context, chaptersList.get(position), Toast.LENGTH_LONG).show()
    }
}
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(items: AlarmDashboard) {


            itemView.venderName.text = "vender Name : "+items.venderName
            if(items.probableCause!=null)
            {
                itemView.problem_cause.visibility=View.VISIBLE
                itemView.problem_cause.text = "Problem Cause :"+items.probableCause
            }
            else
            {
                itemView.problem_cause.visibility=View.GONE
            }

            itemView.managed_element.text = "Managed Element :"+items.managedElement
            itemView.SpecificProblem.text = "SpecificProblem :"+items.specificProblem
            itemView.SiteName.text ="Site Name :"+ items.siteName
            itemView.hours.text ="Event Time :"+ items.eventTime

        }
}
}