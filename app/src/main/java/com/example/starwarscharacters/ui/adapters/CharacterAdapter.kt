package com.example.starwarscharacters.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharacters.data.Films
import com.example.starwarscharacters.databinding.FilmRowBinding




class CharacterAdapter :
    ListAdapter<Films, CharacterAdapter.MyViewHolder>(CHARACTER_COMPARATOR) {

    inner class MyViewHolder(private val binding: FilmRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(filmResponse: Films?) {
            binding.textViewFilmName.text = filmResponse?.title
            binding.textViewOpeningCrawl.text = filmResponse?.opening_crawl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            FilmRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<Films>() {
            override fun areItemsTheSame(oldItem: Films, newItem: Films): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Films, newItem: Films): Boolean {
                return oldItem == newItem
            }
        }
    }
}



