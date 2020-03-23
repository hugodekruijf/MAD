package com.example.swipequiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_quiz.view.*

typealias MyCategoryClickListener = (Int) -> Unit

class QuizAdapter(private  val questions: List<Question>,
private val onClickListener: MyCategoryClickListener
): RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false))
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind((questions[position]))
        holder.itemView.setOnClickListener { Int ->
            onClickListener(position)
        }
    }

    inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView){
        fun bind(Question: Question){
            itemView.tvQuestion.text = Question.questionText
        }
    }


}