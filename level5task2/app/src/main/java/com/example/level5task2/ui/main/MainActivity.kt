package com.example.level5task2.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level5task2.R
import com.example.level5task2.model.Game
import com.example.level5task2.ui.add.AddActivity
import com.example.level5task2.ui.add.EXTRA_GAME

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
const val ADD_GAME_REQUEST_CODE = 100
class MainActivity : AppCompatActivity() {

    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)
    private  val viewModel: MainActivityViewModel by  viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViews()
        observeViewModel()

    }

    private fun initViews() {
        fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, ADD_GAME_REQUEST_CODE)
        }

        rvGames.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvGames.adapter = gameAdapter
        rvGames.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvGames)
    }

    private fun observeViewModel() {
        viewModel.games.observe(this, Observer { reminders ->
            this@MainActivity.games.clear()
            this@MainActivity.games.addAll(reminders)
            gameAdapter.notifyDataSetChanged()
        })

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

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gameToDelete = games[position]
                viewModel.deleteGame(gameToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_GAME_REQUEST_CODE -> {
                    val game = data!!.getParcelableExtra<Game>(EXTRA_GAME)

                    viewModel.insertGame(game)

                }
            }
        }
    }
}
