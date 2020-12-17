package com.example.oop2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oop2.Database.Album
import kotlinx.android.synthetic.main.adapter_album.view.*

class AlbumAdapter (private val AllAlbum: ArrayList<Album>, private val listener: OnAdapterListener) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_album, parent, false)
        )
    }

    override fun getItemCount() = AllAlbum.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = AllAlbum[position]
        holder.view.text_nama.text = album.nama
        holder.view.text_nama.setOnClickListener {
            listener.onClick(album)
        }
    }

    class AlbumViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Album>) {
        AllAlbum.clear()
        AllAlbum.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(album: Album)
    }
}
