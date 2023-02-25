package com.example.notes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.model.Notes

@Database(
    entities = [Notes::class], version = 10, exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao() : NotesDao

    companion object{

        @Volatile
        private var INSTANCE : NotesDatabase?=null

        fun getDatabase(context : Context) : NotesDatabase{

            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }

            //we create a synchronous block here so that whatever happens
            //cannot be accessed by other threads

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "user-database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance

            }

        }

    }

}