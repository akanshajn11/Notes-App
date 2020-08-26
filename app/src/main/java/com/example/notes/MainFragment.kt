package com.example.notes

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.notes.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.title = "Notes"

        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        binding.notesList.adapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_list_item_1,
            NoteFactory(context!!).getAllNotes().map { it.title }
        )

        binding.notesList.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                view.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToNoteFragment(
                        NoteFactory(context!!).editNote(position).title,
                        NoteFactory(context!!).editNote(position).content,
                        position
                    )
                )

            }

        binding.addButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToNoteFragment("", "", 0))
        }
        return binding.root
    }
}
