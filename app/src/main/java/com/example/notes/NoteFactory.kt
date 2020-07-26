package com.example.notes

import android.content.Context
import android.content.SharedPreferences

class NoteFactory(context: Context) {

    private val noteKey = "com.example.notes.NOTE"

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("com.example.notes.preferences", Context.MODE_PRIVATE)

    fun getAllNotes(): List<String> {
        return sharedPreferences.getStringSet(noteKey, null)?.toList() ?: listOf<String>()
    }

    fun saveNote(note: String) {
        val notes = getAllNotes().toMutableList()
        notes.add(note)
        val editor = sharedPreferences.edit()
        val set: HashSet<String> = hashSetOf()
        set.addAll(notes)
        editor.putStringSet(noteKey, set)
        editor.apply()
    }
}