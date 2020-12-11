package com.example.oop2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oop2.Database.AppRoomDB
import com.example.oop2.Database.Album
import kotlinx.android.synthetic.main.activity_edit_album.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditAlbumActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_album)
        setupListener()
    }

    fun setupListener(){
        btn_saveAlbum.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.albumDao().addAlbum(
                    Album(0, txt_nama.text.toString(), txt_musisi.text.toString(), Integer.parseInt(txt_harga.text.toString()) )
                )
                finish()
            }
        }
    }
}

