package com.example.taskplanner.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanner.R
import com.example.taskplanner.model.Task
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.item_task.view.*
import java.util.*
import java.util.concurrent.TimeUnit

typealias MyCategoryClickListener = (Int) -> Unit
class TaskAdapter(private val tasks: List<Task>,  private  val onClickListener: MyCategoryClickListener) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(task: Task) {
            itemView.textProjName.text = task.name
            itemView.textTimeEst.text = task.timeEstimate.toString()
            itemView.card.setCardBackgroundColor(backgroundColor(task))
        }
    }


    private fun backgroundColor(task: Task) : Int{
        var year : String = task.deadline[6].toString() + task.deadline[7].toString() + task.deadline[8].toString() + task.deadline[9].toString()
        var month : String = task.deadline[3].toString() + task.deadline[4].toString()
        var day : String = task.deadline[0].toString() + task.deadline[1].toString()

        var myDate = Calendar.getInstance()

        myDate.set(year.toInt(), month.toInt() -1, day.toInt())

        val msDiff: Long = Calendar.getInstance().getTimeInMillis() - myDate.getTimeInMillis()
        val daysDiff: Int = -TimeUnit.MILLISECONDS.toDays(msDiff).toInt()
        println(daysDiff)

        if(daysDiff > 7){
            return Color.GREEN
        }else if (daysDiff > 0){
            return Color.YELLOW
        }
        return Color.RED
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
        holder.itemView.setOnClickListener { Int ->
            onClickListener(position)
        }
    }
}