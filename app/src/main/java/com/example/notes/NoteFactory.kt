package com.example.notes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.ContextCompat.startActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder


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

    fun deleteNote(pos: Int) {
        val notes = getAllNotes().toMutableList()
        notes.removeAt(pos)
        val editor = sharedPreferences.edit()
        val jsonString = gson.toJson(notes)
        editor.putString(noteKey, jsonString)
        editor.apply()
    }

    fun shareNote(args: NoteFragmentArgs, context: Context) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, args.title + "\n\n" + args.content)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(context, sendIntent, null)
    }

}

