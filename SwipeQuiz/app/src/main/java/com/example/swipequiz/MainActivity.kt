package com.example.swipequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_quiz.*
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {

    private  val questions = arrayListOf<Question>()
    private  val quizAdapter = QuizAdapter(questions, onClickListener = this::itemClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        addQuestion("Is true false?", false)
        addQuestion("The answer to this question is true?", false)

    }

    private fun addQuestion(question: String, answer: Boolean){
        if(question.isNotBlank()){
            questions.add(Question((question),answer))
            quizAdapter.notifyDataSetChanged()

        }
    }
    fun itemClicked(position: Int) {
        Toast.makeText(this, "The answer is: " + questions[position].answer, Toast.LENGTH_SHORT).show()
    }
    private fun initViews(){
        rvQuestions.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvQuestions.adapter = quizAdapter
        rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        SwipeFalse().attachToRecyclerView(rvQuestions)
        SwipeTrue().attachToRecyclerView(rvQuestions)
    }

    private fun SwipeFalse(): ItemTouchHelper {
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
                CheckAnswerFalse(position)
            }
        }
        return ItemTouchHelper(callback)
    }
    private fun SwipeTrue(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                CheckAnswerTrue(position)
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun CheckAnswerTrue(position : Int){
       if(!questions[position].answer){
           addQuestion(questions[position].questionText,questions[position].answer)
           Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show()
       }else{Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()}
        questions.removeAt(position)
        quizAdapter.notifyDataSetChanged()
    }

    private fun CheckAnswerFalse(position : Int){
        if(questions[position].answer){
            addQuestion(questions[position].questionText,questions[position].answer)
            Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
        }
        questions.removeAt(position)
        quizAdapter.notifyDataSetChanged()
    }
}
