package com.example.fotofiltry.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.fotofiltry.data.PhotoModel
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fotofiltry.R
import com.example.fotofiltry.databinding.ItemListBinding

class HomeItemAdapter (private val list: MutableList<PhotoModel>, val fragmentManager: FragmentManager):
        RecyclerView.Adapter<HomeItemAdapter.HomeViewHolder>(){
    companion object {
        val TAG: String = "HomeAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemAdapter.HomeViewHolder {
        val binding = DataBindingUtil.inflate<ItemListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_list,parent, false
        )
        return  HomeViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HomeItemAdapter.HomeViewHolder, position: Int) {
        val model: PhotoModel = list[position]
        holder.bind(model, object : HomeListener{
            override fun onItemSelected(item: PhotoModel) {
                TODO("Not yet implemented")
            }
        })
        holder.itemView.setOnClickListener{
        }
    }
    fun setList(newList: List<PhotoModel>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
    fun removeAt(position: Int) {
        Log.d("Adapter", "Added: ${position}")
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    class HomeViewHolder(private  val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root){
        private var title: TextView = itemView.findViewById(R.id.tv_title)
        private var date: TextView = itemView.findViewById(R.id.tv_data)

        fun bind(model: PhotoModel, homeListener: HomeListener){
            with(binding){
                photoModel = model
                listener = homeListener
                title.text = model.title
                date.text = model.date

            }
        }
    }

    interface HomeListener {
        fun onItemSelected(item: PhotoModel)
    }
}