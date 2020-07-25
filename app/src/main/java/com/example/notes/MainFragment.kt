package com.example.notes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.notes.databinding.FragmentMainBinding
import java.lang.Boolean.getBoolean


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {


    private lateinit var listItems : ArrayList<String> //for list items
   private lateinit var adapter : ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_main, container, false)

        val binding  = DataBindingUtil.inflate<FragmentMainBinding>(inflater,R.layout.fragment_main,container,false)


       var arg : Bundle? = arguments

        if (arg != null) {
            var args=MainFragmentArgs.fromBundle(arguments!!)
//            val notesList : ListView = binding.notesList
           val text1 : TextView = binding.textView
            text1.text=args.title


//        val arrayAdapter: ArrayAdapter<*>
//        val notes = arrayOf(args.title)
//        val notesList : ListView = binding.notesList
//
//
//        arrayAdapter= ArrayAdapter<String>(context!!, R.layout.fragment_main,notes)
//
//        notesList.adapter=arrayAdapter

        }


                binding.addButton.setOnClickListener{view:View ->
                    view.findNavController().navigate(MainFragmentDirections.actionMainFragmentToNoteFragment())
                }


        return binding.root

    }

//    private fun getIntent() : Intent{
//        var args=MainFragmentArgs.fromBundle(arguments!!)
//
//        val arrayAdapter: ArrayAdapter<*>
//        val notes = arrayOf(args.title)
//        val notesList : ListView = binding.notesList
//
//
//        arrayAdapter= ArrayAdapter<String>(context!!, R.layout.fragment_main,notes)
//
//        notesList.adapter=arrayAdapter
//    }


}
