package com.abhilekh.assignmentapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhilekh.assignmentapplication.R
import com.abhilekh.assignmentapplication.model.Hit
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Hit>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {


    fun updateAnimalList(newAnimalList: List<Hit>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    class AnimalViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount(): Int = animalList.size


    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animalName.text = animalList.get(position).tags
        Glide.with(holder.view.animalItem.context)
            .load(animalList.get(position).previewURL)
            .into(holder.view.animalItem);

    }
}