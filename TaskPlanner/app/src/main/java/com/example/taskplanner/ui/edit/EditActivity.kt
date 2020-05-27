package com.example.taskplanner.ui.edit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.taskplanner.R
import com.example.taskplanner.model.Task
import kotlinx.android.synthetic.main.activity_edit.*

const val EXTRA_TASK = "EXTRA_TASK"
class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit,menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun save() {
        if (etProject.text.toString().isNotBlank()) {

            val task = Task(etProject.text.toString(),etTimeEst.text.toString().toInt(),(etDay.text.toString() + "-"+ etMonth.text.toString() + "-"+ etYear.text.toString()), etTask.text.toString())
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_TASK, task)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(this,"The Fields cannot be empty"
                , Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_save -> {
                save()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
