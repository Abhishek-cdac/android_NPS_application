package com.nectar.nps

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nectar.nps.Adapter.AlarmAdapter
import kotlinx.android.synthetic.main.alarm_item_layout.view.*

class ResolvedAlarmAdapter   : Fragment(){
    val animals: ArrayList<String> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.alarm_item_layout, container, false)
        addAnimals()

        // Creates a vertical Layout Manager
        view.alarm_list.layoutManager = LinearLayoutManager(activity)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
//        rv_animal_list.layoutManager = GridLayoutManager(this, 2)

        // Access the RecyclerView Adapter and load the data into it
        //view.alarm_list.adapter= activity?.let { AlarmAdapter(it,animals) }

        return view
    }

    fun addAnimals() {
        animals.add("dogaaaaa")
        animals.add("caaaaaaaat")
        animals.add("owl")
        animals.add("cheetahaaaaaaa")
        animals.add("raccoon")
        animals.add("bird")
        animals.add("snake")
        animals.add("lizard")

    }
}