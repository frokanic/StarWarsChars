package com.example.starwarscharacters.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharacters.data.Films
import com.example.starwarscharacters.databinding.FilmRowBinding


class CharacterAdapter(private val films: Unit): RecyclerView.Adapter<CharacterAdapter.FilmViewHolder>() {

    inner class FilmViewHolder(val binding: FilmRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object  : DiffUtil.ItemCallback<Films>() {
        override fun areItemsTheSame( oldItem: Films, newItem: Films): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Films, newItem: Films): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapter.FilmViewHolder {
        return FilmViewHolder(
            FilmRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterAdapter.FilmViewHolder, position: Int) {
        val binding = holder.binding
        val film = differ.currentList[position]

        holder.itemView.apply {
            binding.textViewFilmName.text = film.title
            binding.textViewOpeningCrawl.text = film.opening_crawl
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}

