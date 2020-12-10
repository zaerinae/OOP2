package com.example.oop2.Database

import androidx.room.*

@Dao
interface AlbumDao {
    @Insert
    suspend fun addAlbum(album: Album)

    @Update
    suspend fun updateAlbum(album: Album)

    @Delete
    suspend fun deleteAlbum(album: Album)

    @Query("SELECT * FROM album")
    suspend fun getAllAlbum(): List<Album>
}

