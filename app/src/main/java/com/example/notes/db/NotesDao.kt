package com.example.notes.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.model.Notes

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note : Notes) : Long
    //onConflictStrategy - > basically what we want to do when the note we are
    //adding already exists in the database, here we are ignoring it

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getAllNotes() : LiveData<List<Notes>>
    //The reason why this function wasn't suspended is simple.
    //This function returns a LIVE_DATA object which does not work with suspend.
    //Live Data - > As the name suggests it is part of android architectural components that enables
    //us to see the changes in the data live. Live data tell all the classes about the changes made
    //in the data so that the classes can change their recycler view accordingly

    //They are especially useful for device rotation as when we rotate our devices the activities are
    //recreated. But the view model does not get recreated and the recycler view can get the teh
    //latest data from the live data

    @Update
    suspend fun updateNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Query("DELETE FROM Notes")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM Notes WHERE title LIKE :query ORDER BY id DESC")
    fun searchNotes(query: String?): LiveData<List<Notes>>

}