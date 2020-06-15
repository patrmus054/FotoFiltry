package com.example.fotofiltry.ui.home

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fotofiltry.R
import com.example.fotofiltry.data.PhotoModel
import com.example.fotofiltry.databinding.ItemListBinding
import java.io.File


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
                val rawTakenImage = BitmapFactory.decodeFile(Uri.decode(model.location))
                imageView.setImageBitmap(rawTakenImage)

            }
        }
    }

    interface HomeListener {
        fun onItemSelected(item: PhotoModel)
    }
}

fun getPhotoFileUri(fileName: String): Uri? {
    // Get safe storage directory for photos
    val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "APP_TAG")

    // Create the storage directory if it does not exist
    if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
        Log.d("APP_TAG", "failed to create directory")
    }
    // Return the file target for the photo based on filename
    return Uri.fromFile(File(mediaStorageDir.getPath() + File.separator.toString() + fileName))
}