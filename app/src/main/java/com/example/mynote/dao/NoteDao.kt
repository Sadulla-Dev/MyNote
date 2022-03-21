package com.example.mynote.dao

import androidx.room.*
import com.example.mynote.entities.Notes

@Dao
interface NoteDao {

    @Query("SELECT *FROM notes ORDER BY id DESC")
    suspend fun getAllNotes():List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

}