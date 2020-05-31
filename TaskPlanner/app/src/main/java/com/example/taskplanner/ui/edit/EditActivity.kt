package com.example.taskplanner.ui.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.taskplanner.R
import com.example.taskplanner.model.Task
import kotlinx.android.synthetic.main.activity_edit.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


const val EXTRA_TASK = "EXTRA_TASK"
class EditActivity : AppCompatActivity() {
    private lateinit var editViewModel: EditActivityViewModel
    private var nullOnStart = false
    val DATE_FORMAT = "dd-MM-yyyy"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViewModel()

    }

    private fun initViewModel() {
        editViewModel = ViewModelProvider(this).get(EditActivityViewModel::class.java)
        val task =  intent!!.getParcelableExtra<Task>(com.example.taskplanner.ui.edit.EXTRA_TASK)
        editViewModel.task.value = task


        editViewModel.task.observe(this, Observer { task ->
            if (task != null) {
                nullOnStart = false
                etProject.setText(task.name)
                etTimeEst.setText(task.timeEstimate.toString())
                etDay.setText(task.deadline[0].toString() + task.deadline[1].toString())
                etMonth.setText(task.deadline[3].toString() + task.deadline[4].toString())
                etYear.setText(task.deadline[6].toString() + task.deadline[7].toString()+ task.deadline[8].toString()+ task.deadline[9].toString())
                etTask.setText(task.taskDescription)
            }
            else  {
                nullOnStart = true
            }
        })

        editViewModel.success.observe(this, Observer { success ->
            if (success) finish()
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit,menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun save() {
        if(checkInputs()){
            if(nullOnStart){
            val task = Task(etProject.text.toString(),etTimeEst.text.toString().toInt(),(etDay.text.toString() + "-"+ etMonth.text.toString() + "-"+ etYear.text.toString()), etTask.text.toString())
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_TASK, task)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
             }
            else
            {
                editViewModel.task.value?.apply {
                name = etProject.text.toString()
                timeEstimate = etTimeEst.text.toString().toInt()
                deadline = (etDay.text.toString() + "-"+ etMonth.text.toString() + "-"+ etYear.text.toString())
                taskDescription = etTask.text.toString()
                }

                editViewModel.updateTask()
            }
        }
    }

    fun checkInputs(): Boolean{
        if(checkDeadline()){
            return true
        }else{
            return false
        }

    }

    fun isDateValid(date: String?): Boolean {
        return try {
            val df: DateFormat = SimpleDateFormat(DATE_FORMAT)
            df.setLenient(false)
            df.parse(date)
            true
        } catch (e: ParseException) {
            false
        }
    }

   private fun checkDeadline(): Boolean {
       if(!isDateValid(etDay.text.toString()+"-"+etMonth.text.toString()+"-"+etYear.text.toString())){
           Toast.makeText(this, "invalid Deadline", Toast.LENGTH_SHORT).show();
           return false
       }
       var today = Calendar.getInstance()
       var myDate = Calendar.getInstance()

       myDate.set(etYear.text.toString().toInt(), etMonth.text.toString().toInt() -1, etDay.text.toString().toInt())

       if (myDate.before(today))
       {
           Toast.makeText(this, "Deadline has already passed", Toast.LENGTH_SHORT).show();
           return false;
       }
       var month = etMonth.text.toString()
       var day = etDay.text.toString()

       if(month.length <2){
           etMonth.setText("0" + month)
       }

       if(day.length <2){
           etDay.setText("0" + day)
       }

       if(etYear.text.toString().length >4){
           Toast.makeText(this, "invalid deadline", Toast.LENGTH_SHORT).show();
           return false
       }
       return true;
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_save -> {
                save()
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_TASK = "EXTRA_TASK"
    }
}
