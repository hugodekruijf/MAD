package com.example.logicaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class LogicaActivity : AppCompatActivity() {

    public var firstAns: String = "";
    public var secondAns: String = "";
    public var thirdAns: String = "";
    public var fourthAns: String = "";
    private var amountCorrect: Int = 0;
    private var checked: Boolean = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews();
        ConfirmButton.setOnClickListener {
            checkAns();
        }
    }

    private  fun initViews(){
        updateUI()
    }

    private  fun updateUI(){

    }

    private  fun checkAns(){
        firstAns = editText1.text.toString()
        secondAns = editText2.text.toString();
        thirdAns = editText3.text.toString();
        fourthAns = editText4.text.toString();


        if(!checked){
            if(firstAns == "t" || firstAns == "T"){
                amountCorrect += 1;
            }
            if(secondAns == "f" || secondAns == "F"){
                amountCorrect += 1;
            }
            if(thirdAns == "f" || thirdAns == "F"){
                amountCorrect += 1;
            }
            if(fourthAns == "f" || fourthAns == "F"){
                amountCorrect += 1;
            }

            Toast.makeText(this, "amount correct: " + amountCorrect, Toast.LENGTH_LONG).show();
            checked = true;
        } else {
            amountCorrect = 0;
            checked = false;
            checkAns();
        }

    }
}
