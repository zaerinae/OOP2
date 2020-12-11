package com.example.oop2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.oop2.Database.AppRoomDB
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val allAlbum = db.albumDao().getAllAlbum()
            Log.d("AlbumActivity", "dbResponse: $allAlbum")
        }
    }

    fun setupListener() {
        btn_createAlbum.setOnClickListener {
            startActivity(Intent(this, EditAlbumActivity::class.java))
        }
    }
}
