package com.example.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.notes.databinding.FragmentNoteBinding
import kotlinx.android.synthetic.main.fragment_note.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentNoteBinding>(
            inflater,
            R.layout.fragment_note,
            container,
            false
        )
        val noteFactory = NoteFactory(context!!)
        binding.doneButton.setOnClickListener { view: View ->
            view.findNavController().navigate(
                NoteFragmentDirections.actionNoteFragmentToMainFragment()
            )
            noteFactory.saveNote(binding.editTitleText.text.toString())
        }
        return binding.root
    }

}
