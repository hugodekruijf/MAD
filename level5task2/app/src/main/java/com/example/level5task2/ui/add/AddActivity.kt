package com.example.level5task2.ui.add

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.level5task2.R
import com.example.level5task2.model.Game

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

const val EXTRA_GAME = "EXTRA_GAME"
class AddActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        initViews()
    }

    private fun initViews() {
        fab.setOnClickListener { onSave() }
    }


    private fun onSave() {
        if (etTitle.text.toString().isNotBlank() && etPlatform.text.toString().isNotBlank() && etYear.text.toString().isNotBlank()) {

            val game = Game(etTitle.text.toString(),etPlatform.text.toString(),(etDay.text.toString() + "-"+ etMonth.text.toString() + "-"+ etYear.text.toString()))
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_GAME, game)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(this,"The Fields cannot be empty"
                , Toast.LENGTH_SHORT).show()
        }
    }

}
