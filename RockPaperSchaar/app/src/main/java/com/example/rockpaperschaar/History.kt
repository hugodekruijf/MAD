package com.example.rockpaperschaar

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentportal.GameAdapter

import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.content_history.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val EXTRA_HISTORY = "EXTRA_HISTORY"
class History : AppCompatActivity() {

    public val historyGames = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(historyGames, onClickListener = this::itemClicked)
    public lateinit var historyGameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)

        historyGameRepository = GameRepository(this)
        initViews()
        addGame()
        //Log.d("TAG", "INSERTED")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_history,menu)
        return super.onCreateOptionsMenu(menu)
    }

    private  fun deleteAll(){
        CoroutineScope(Dispatchers.Main).launch {
            historyGameRepository.deleteGames()
            gameAdapter.notifyDataSetChanged()
        }
        getGamesFromDatabase()
    }

    private fun addGame() {
            mainScope.launch {
               // val game = Game("win", java.util.Calendar.getInstance().toString(),"choiceHuman","choiceComputer")
                //historyGameRepository.insertGame(game)
               // getGamesFromDatabase()
                val a: ArrayList<Game> = intent.getSerializableExtra("mylist") as ArrayList<Game>
                for (game in a) {
                    withContext(Dispatchers.IO) {
                        historyGameRepository.insertGame(game)
                        Log.d("TAG", "INSERTED")
                    }
                }
                getGamesFromDatabase()
                //historyGames.clear()

            }
    }


    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvHistory.layoutManager = LinearLayoutManager(this@History, RecyclerView.VERTICAL, false)
        rvHistory.adapter = gameAdapter
        rvHistory.addItemDecoration(DividerItemDecoration(this@History, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getGamesFromDatabase()
    }

    public fun getGamesFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val games = withContext(Dispatchers.IO) {
                historyGameRepository.getAllGames()
            }
            this@History.historyGames.clear()
            this@History.historyGames.addAll(games)
            gameAdapter.notifyDataSetChanged()
        }
    }



    fun itemClicked(position: Int) {

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
                historyGames.removeAt(position)
                gameAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_delete -> {
                deleteAll()
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


}
