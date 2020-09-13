package com.example.notes


import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.notes.databinding.FragmentNoteBinding
import android.text.TextWatcher

class NoteFragment : Fragment() {

    private lateinit var noteFactory: NoteFactory
    private lateinit var args: NoteFragmentArgs
    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.title = "My Note"

        binding = DataBindingUtil.inflate<FragmentNoteBinding>(
            inflater,
            R.layout.fragment_note,
            container,
            false
        )
        noteFactory = NoteFactory(context!!)
        args = NoteFragmentArgs.fromBundle(arguments!!)
        binding.doneButton.hide()

        checkEditMode()
        val textWatcher = textWatcher()

        binding.editTitleText.addTextChangedListener(textWatcher)
        binding.editContentText.addTextChangedListener(textWatcher)

        doneButtonListener()

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.delete_button).isVisible = true
        menu.findItem(R.id.share_button).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.delete_button -> {
            noteFactory.deleteNote(args.position)
            view!!.findNavController()
                .navigate(NoteFragmentDirections.actionNoteFragmentToMainFragment())
            true
        }
        R.id.share_button -> {
            noteFactory.shareNote(args, context!!)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun checkEditMode() {
        if (args.title != "") {
            binding.editTitleText.setText(args.title)
            binding.editContentText.setText(args.content)
            setHasOptionsMenu(true)
        }
    }

    private fun textWatcher(): TextWatcher {

        return (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.isNullOrEmpty() && binding.editTitleText.text.toString() == "" && binding.editContentText.text.toString() == "")
                    binding.doneButton.hide()
                else
                    binding.doneButton.show()
            }
        })
    }

    private fun doneButtonListener() {
        binding.doneButton.setOnClickListener { view: View ->
            view.findNavController().navigate(
                NoteFragmentDirections.actionNoteFragmentToMainFragment()
            )

            if (binding.editTitleText.text.toString() == "")
                binding.editTitleText.text = binding.editContentText.text

            if (binding.editContentText.text.toString() == "")
                binding.editContentText.text = binding.editTitleText.text

            if (args.title != "")
                noteFactory.saveNote(
                    Note(
                        binding.editTitleText.text.toString(),
                        binding.editContentText.text.toString()
                    ), "Edit", args.position
                )
            else
                noteFactory.saveNote(
                    Note(
                        binding.editTitleText.text.toString(),
                        binding.editContentText.text.toString()
                    ), "New", 0
                )
        }
    }

}
