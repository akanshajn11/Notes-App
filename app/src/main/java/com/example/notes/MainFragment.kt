package com.example.notes

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.notes.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        binding.notesList.adapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_list_item_1,
            NoteFactory(context!!).getAllNotes()
        )

        binding.addButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToNoteFragment())
        }
        return binding.root
    }
}
