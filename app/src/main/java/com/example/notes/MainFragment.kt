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
import androidx.navigation.fragment.findNavController
import com.example.notes.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.Boolean.getBoolean


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment  : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding  = DataBindingUtil.inflate<FragmentMainBinding>(inflater,R.layout.fragment_main,container,false)

        var arg : Bundle? = arguments

        if (arg != null) {
          var args=MainFragmentArgs.fromBundle(arguments!!)

            val notes = listOf<String>(args.title)

        val listView : ListView = binding.notesList

        listView.adapter= ArrayAdapter(activity!!.applicationContext,android.R.layout.simple_list_item_1,notes)


        }


                binding.addButton.setOnClickListener{view:View ->
                    view.findNavController().navigate(MainFragmentDirections.actionMainFragmentToNoteFragment())
                }


        return binding.root

    }




}
