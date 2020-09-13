package com.example.notes

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.notes.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var notesList: ListView
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.title = "Notes"

        binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        notesList = binding.notesList
        displayNotes()
        contextualActionMode()

        notesList.onItemClickListener =
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

    private fun displayNotes() {
        notesList.adapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_list_item_1,
            NoteFactory(context!!).getAllNotes().map { it.title }
        )
    }

    private fun contextualActionMode() {
        with(notesList) {
            choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL
            var selectCount: Int = 0
            val selectedPositionsList = mutableListOf<Int>()

            setMultiChoiceModeListener(object : AbsListView.MultiChoiceModeListener {

                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    //Inflate the menu for the CAB
                    val menuInflater: MenuInflater = mode!!.menuInflater
                    menuInflater.inflate(R.menu.contextual_menu, menu)
                    binding.addButton.hide()
                    return true
                }

                override fun onItemCheckedStateChanged(
                    mode: ActionMode?,
                    position: Int,
                    id: Long,
                    checked: Boolean
                ) {
                    // Here you can do something when items are selected/de-selected,
                    // such as update the title in the CAB
                    var item: View = getChildAt(position)

                    if (checked) {
                        item.setBackgroundColor(Color.GRAY)
                        selectCount++
                        selectedPositionsList.add(position)
                    } else {
                        item.setBackgroundColor(Color.TRANSPARENT)
                        selectCount--
                        selectedPositionsList.remove(position)
                    }
                    mode!!.title =
                        selectCount.toString() + '/' + NoteFactory(context).getAllNotes().size
                }

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    //responds to click on actions in CAB
                    return when (item?.itemId) {
                        R.id.delete_button -> {
                            NoteFactory(context).deleteSelectedNotes(
                                selectedPositionsList.sorted().reversed() as MutableList<Int>
                            )
                            mode?.finish() //action picked so close the cab
                            true
                        }
                        else -> false
                    }
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    // Here you can make any necessary updates to the activity when
                    // the CAB is removed. By default, selected items are deselected/unchecked.
                    for (pos in selectedPositionsList) {
                        var item: View = getChildAt(pos)
                        item.setBackgroundColor(Color.TRANSPARENT)
                    }
                    selectCount = 0
                    selectedPositionsList.clear()
                    binding.addButton.show()
                    displayNotes()
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return true
                }
            })
        }
    }
}
