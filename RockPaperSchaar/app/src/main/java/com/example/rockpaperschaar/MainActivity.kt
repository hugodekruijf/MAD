package com.example.rockpaperschaar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random


const val ADD_HISTORY_REQUEST_CODE = 100
const val EXTRA_GAMES = "EXTRA_GAMES"
class MainActivity : AppCompatActivity() {

    public val games = arrayListOf<Game>()
    public var currentTime : String = ""
    public var outcome : String = ""
    public var choiceComputer : String = ""
    public var choiceHuman : String = ""
    public var history : History = History()

    override fun onStart() {
        super.onStart()
        games.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        buttonRock.setOnClickListener { Rock()}
        buttonPaper.setOnClickListener { Paper() }
        buttonScissors.setOnClickListener { Scissors()  }

    }

    private fun Scissors(){
        imageUser.setImageResource(R.drawable.scissors)
        resolveTurn("scissors")
    }
    private fun Paper(){
        imageUser.setImageResource(R.drawable.paper)
        resolveTurn("paper")
    }
    private fun Rock(){
        imageUser.setImageResource(R.drawable.rock)
        resolveTurn("rock")
    }

    private fun resolveTurn(humanchoice: String){
        choiceHuman = humanchoice
        val randomNumber = Random.nextInt(3)+1
        if(randomNumber == 1){
            choiceComputer = "rock"
            imageComputer.setImageResource(R.drawable.rock)
        }else if(randomNumber == 2){
            choiceComputer = "paper"
            imageComputer.setImageResource(R.drawable.paper)
        }else if(randomNumber == 3){
            choiceComputer = "scissors"
            imageComputer.setImageResource(R.drawable.scissors)
        }

        if(choiceHuman == choiceComputer){
            outcome = "Draw"
        }
        if(choiceHuman == "scissors" && choiceComputer == "paper"){
            outcome = "Win"
        }
        if(choiceHuman == "paper" && choiceComputer == "rock"){
            outcome = "Win"
        }
        if(choiceHuman == "rock" && choiceComputer == "scissors"){
            outcome = "Win"
        }
        if(choiceHuman == "scissors" && choiceComputer == "rock"){
            outcome = "Lose"
        }
        if(choiceHuman == "paper" && choiceComputer == "scissors"){
            outcome = "Lose"
        }
        if(choiceHuman == "rock" && choiceComputer == "paper"){
            outcome = "Lose"
        }
        textResult.text = outcome
        val time = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        var formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        games.add(Game(outcome, formatter.format(time) ,choiceHuman,choiceComputer))
    }



    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_HISTORY_REQUEST_CODE -> {
                    val game = data!!.getParcelableExtra<Game>(EXTRA_HISTORY)

                    CoroutineScope(Dispatchers.Main).launch {
                        withContext(Dispatchers.IO) {
                            history.historyGameRepository.insertGame(game)
                        }
                        history.getGamesFromDatabase()
                    }


                }
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun startHistory() {
        val intent = Intent(this, History::class.java)
        intent.putParcelableArrayListExtra("mylist", games)
       // intent.putExtra(EXTRA_GAMES, games)
        startActivityForResult(intent, ADD_HISTORY_REQUEST_CODE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        startHistory()
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
