package com.example.oop2

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oop2.Database.Album
import com.example.oop2.Database.AppRoomDB
import com.example.oop2.Database.Constant
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var albumAdapter : AlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadAlbum()
    }

    fun loadAlbum() {
        CoroutineScope(Dispatchers.IO).launch {
            val allAlbum = db.albumDao().getAllAlbum()
            Log.d("AlbumActivity", "dbResponse: $allAlbum")
            withContext(Dispatchers.Main) {
                albumAdapter.setData(allAlbum)
            }
        }
    }

    fun setupListener() {
        btn_createAlbum.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        albumAdapter = AlbumAdapter(arrayListOf(), object: AlbumAdapter.OnAdapterListener {
            override fun onClick(album: Album) {
                // read detail
                intentEdit(album.id, Constant.TYPE_READ)
            }

            override fun onDelete(album: Album) {
                // delete data
                deleteDialog(album)
            }

            override fun onUpdate(album: Album) {
                // edit data
                intentEdit(album.id, Constant.TYPE_UPDATE)
            }

        })
        list_album.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = albumAdapter
        }
    }

    fun intentEdit(albumId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditAlbumActivity::class.java)
                .putExtra("intent_id", albumId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(album: Album) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.albumDao().deleteAlbum(album)
                    loadAlbum()
                }
            }
        }
        alertDialog.show()
    }
}
