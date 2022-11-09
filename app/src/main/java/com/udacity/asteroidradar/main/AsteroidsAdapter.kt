package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidItemBinding

class AsteroidsAdapter(private val clickListener: AsteroidClickListener) : ListAdapter<Asteroid, AsteroidsAdapter.ViewHolder>(AsteroidsDiffCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    class ViewHolder(private val binding: AsteroidItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(clickListener: AsteroidClickListener, asteroid: Asteroid)
        {
            binding.asteroid = asteroid
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object
        {
            fun from(parent: ViewGroup): ViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val binding = AsteroidItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }

        }
    }

    class AsteroidsDiffCallback: DiffUtil.ItemCallback<Asteroid>()
    {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

    }

    class AsteroidClickListener (val clickListener : (asteroid: Asteroid) -> Unit)
    {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }

}