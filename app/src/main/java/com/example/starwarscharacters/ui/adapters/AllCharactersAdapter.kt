package com.example.starwarscharacters.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharacters.data.ResultCharacters
import com.example.starwarscharacters.databinding.CharactersRowBinding

class AllCharactersAdapter: RecyclerView.Adapter<AllCharactersAdapter.CharacterViewHolder>()  {

    inner class CharacterViewHolder(val binding: CharactersRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object  : DiffUtil.ItemCallback<ResultCharacters>() {
        override fun areItemsTheSame( oldItem: ResultCharacters, newItem: ResultCharacters): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ResultCharacters, newItem: ResultCharacters): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            CharactersRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val binding = holder.binding
        val character = differ.currentList[position]

        holder.itemView.apply {
            binding.nameTextView.text = character.name
            binding.dobTextView.text = character.birth_year

            setOnClickListener {
                onItemClickListener?.let { it(character) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((ResultCharacters) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResultCharacters) -> Unit) {
        onItemClickListener = listener
    }

}