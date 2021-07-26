package com.newcompany.roomdatabasetask.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newcompany.roomdatabasetask.MainActivity
import com.newcompany.roomdatabasetask.MainActivityViewModel
import com.newcompany.roomdatabasetask.R
import com.newcompany.roomdatabasetask.adapter.UserRecyclerAdapter
import com.newcompany.roomdatabasetask.databinding.FragmentSecondBinding
import com.newcompany.roomdatabasetask.db.User
import java.util.zip.Inflater

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var viewModel:MainActivityViewModel
    private lateinit var binding:FragmentSecondBinding
    private lateinit var adapter:UserRecyclerAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(layoutInflater, R.layout.fragment_second, container,false)
        binding.lifecycleOwner=this
        viewModel=(activity as MainActivity).viewModel
        binding.viewmodel= viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        viewModel.message.observe(viewLifecycleOwner, Observer { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        })
        initUserRecyclerView()
        setAdapterList()
    }

    fun initUserRecyclerView()
    {
        binding.userRecyclerView.layoutManager=LinearLayoutManager(activity)
        adapter= UserRecyclerAdapter( { user: User ->listItemClick(user) }, { user2: User ->listItemClickDelete(user2) })
        binding.userRecyclerView.adapter=adapter



    }
    fun setAdapterList()
    {
     viewModel.getUsers().observe(viewLifecycleOwner, Observer {
        adapter.setList(it)
         adapter.notifyDataSetChanged()
     })
    }

    fun listItemClick(user: User)
    {
        viewModel.initUserBeforeUpdate(user)
        requireView().findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

    }
    fun listItemClickDelete(user: User)
    {
        viewModel.deleteUser(user)
    }
}