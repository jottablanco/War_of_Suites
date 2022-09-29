package com.jorgereyes.warofsuites.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jorgereyes.warofsuites.R
import com.jorgereyes.warofsuites.data.model.Player

class ScoresAdapter : RecyclerView.Adapter<ScoresAdapter.ScoresViewHolder>() {

  private val callback = object : DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
      return oldItem == newItem
    }
  }

  val differ = AsyncListDiffer(this, callback)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoresViewHolder {
    return ScoresViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.scores_adapter_item, parent, false))
  }

  override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
    val player = differ.currentList[position]
    holder.bind(player)
  }

  override fun getItemCount(): Int {
    return differ.currentList.size
  }


  inner class ScoresViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val playerTag: TextView = view.findViewById(R.id.player_tag)
    private val cardCount: TextView = view.findViewById(R.id.card_counter_tv)

    fun bind(player: Player) {
      playerTag.text = player.playerTag.toString()
      cardCount.text = player.finalScore.toString()
    }
  }
}
