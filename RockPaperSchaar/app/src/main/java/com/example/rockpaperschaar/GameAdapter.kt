package com.example.studentportal

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperschaar.Game
import com.example.rockpaperschaar.R
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*
import kotlinx.android.synthetic.main.item_game.view.*


typealias MyCategoryClickListener = (Int) -> Unit
class GameAdapter (private val games: List<Game>, private  val onClickListener: MyCategoryClickListener) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(game: Game) {
            itemView.tvResult.text = game.gameOutcome
            itemView.tvTime.text = game.gameTime
            if(game.gameComputer == "rock"){
                itemView.computerChoice.setImageResource(R.drawable.rock)
            }
            if(game.gameComputer == "scissors"){
                itemView.computerChoice.setImageResource(R.drawable.scissors)
            }
            if(game.gameComputer == "paper"){
                itemView.computerChoice.setImageResource(R.drawable.paper)
            }
            if(game.gameHuman == "rock"){
                itemView.humanChoice.setImageResource(R.drawable.rock)
            }
            if(game.gameHuman == "scissors"){
                itemView.humanChoice.setImageResource(R.drawable.scissors)
            }
            if(game.gameHuman == "paper"){
                itemView.humanChoice.setImageResource(R.drawable.paper)
            }


            //todo set images
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return games.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
        holder.itemView.setOnClickListener { Int ->
            onClickListener(position)
        }
    }
}