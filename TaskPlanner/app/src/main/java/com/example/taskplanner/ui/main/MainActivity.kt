package com.example.taskplanner.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanner.R
import com.example.taskplanner.model.Task
import com.example.taskplanner.ui.edit.EXTRA_TASK
import com.example.taskplanner.ui.edit.EditActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val ADD_TASK_REQUEST_CODE = 100
class MainActivity : AppCompatActivity() {


    private val tasks = arrayListOf<Task>()
    private val taskAdapter = TaskAdapter(tasks)

    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    private  val viewModel: MainActivityViewModel by  viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        fab.setOnClickListener { view ->
            val intent = Intent(this, EditActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        rvTasks.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvTasks.adapter = taskAdapter
        rvTasks.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvTasks)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun selector(task: Task): LocalDate = LocalDate.parse(task.deadline, formatter)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeViewModel() {
        viewModel.tasks.observe(this, Observer { tasks ->
            this@MainActivity.tasks.clear()
            this@MainActivity.tasks.addAll(tasks)

            this@MainActivity.tasks.sortBy ( {selector(it)} )
            taskAdapter.notifyDataSetChanged()
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    val dateTimeStrToLocalDateTime: (String) -> LocalDateTime = {
        LocalDateTime.parse(it, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteTask(tasks[position])
            }
        }
        return ItemTouchHelper(callback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_TASK_REQUEST_CODE -> {
                    val task = data!!.getParcelableExtra<Task>(EXTRA_TASK)

                    viewModel.insertTask(task)
                }
            }
        }
    }
}
