package com.example.oop2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.oop2.Database.AppRoomDB
import com.example.oop2.Database.Album
import com.example.oop2.Database.Constant
import kotlinx.android.synthetic.main.activity_edit_album.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditAlbumActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var albumId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_album)
        setupListener()
        setupView()
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
        btn_updateAlbum.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.albumDao().updateAlbum(
                    Album(albumId, txt_nama.text.toString(), txt_musisi.text.toString(), Integer.parseInt(txt_harga.text.toString()) )
                )
                finish()
            }
        }
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_updateAlbum.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_saveAlbum.visibility = View.GONE
                btn_updateAlbum.visibility = View.GONE
                getAlbum()
            }
            Constant.TYPE_UPDATE -> {
                btn_saveAlbum.visibility = View.GONE
                getAlbum()
            }
        }
    }

    fun getAlbum() {
        albumId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val albums =  db.albumDao().getAlbum( albumId )[0]
            txt_nama.setText( albums.nama )
            txt_musisi.setText( albums.musisi )
            txt_harga.setText( albums.harga.toString() )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

