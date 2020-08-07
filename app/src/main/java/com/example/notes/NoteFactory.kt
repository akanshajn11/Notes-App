package com.example.notes

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.sql.RowId
import java.text.FieldPosition

data class Note(var title: String, var content: String)

class NoteFactory(context: Context) {

    private val noteKey = "com.example.notes.NOTE"
    private val gson: Gson = GsonBuilder().create()
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("com.example.notes.preferences", Context.MODE_PRIVATE)

    fun getAllNotes(): List<Note> {

        val jsonString = sharedPreferences.getString(noteKey, null)
        return if (jsonString != null) {
            gson.fromJson(jsonString, Array<Note>::class.java).toList()
        } else
            listOf<Note>()
    }

    fun saveNote(note: Note, mode: String, rowId: Int) {
        val notes = getAllNotes().toMutableList()
        if (mode == "New") {
            notes.add(note)
        } else {
            notes[rowId].title = note.title
            notes[rowId].content = note.content
        }
        val editor = sharedPreferences.edit()
        val jsonString = gson.toJson(notes)
        editor.putString(noteKey, jsonString)
        editor.apply()
    }

    fun editNote(index: Int): Note {
        return getAllNotes()[index]
    }


}

