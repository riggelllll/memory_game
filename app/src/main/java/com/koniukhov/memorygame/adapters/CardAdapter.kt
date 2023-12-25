package com.koniukhov.memorygame.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.koniukhov.memorygame.R
import com.koniukhov.memorygame.data.Card

class CardAdapter(private val onClick: (Card) -> Unit
): ListAdapter<Card, CardAdapter.CardViewHolder>(DiffCallback) {

    inner class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imageView = itemView.findViewById<ImageView>(R.id.image)

        fun bind(card: Card){
            imageView.setOnClickListener{
                onClick(card)
            }
            if (card.isFlipped){
                imageView.setImageResource(card.resId)
            }else{
                imageView.setImageResource(R.drawable.card_back)
            }
            //TODO delete
//            if (card.isFlipped){
//                imageView.setImageResource(R.drawable.card_back)
//            }else{
//                imageView.setImageResource(card.resId)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.block_item, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback: DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }

    }
}