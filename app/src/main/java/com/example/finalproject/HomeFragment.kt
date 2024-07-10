package com.example.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.viewmodels.ContactsSharedViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: ContactsSharedViewModel
    lateinit var adapter: ContactsRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).contactsViewModel
        viewModel.getAllContacts() // llamada a la api
        setupRecyclerView()
        setupAddButton()
    }

    private fun setupAddButton(){
        binding.floatingActionButton2.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragment2ToDetailFragment()
            binding.root.findNavController().navigate(direction)
        }
    }

    private fun setupRecyclerView(){
        adapter = ContactsRecyclerViewAdapter(listOf()) {contact ->
            viewModel.selectContact(contact)
            // redireccionar al Detail fragment
            //view?.findNavController()?.navigate(R.id.action_homeFragment_to_detailFragment)
            // esta es otra opcion para enviar parametros lo mandamos en actionhomefragmenttodetailfragment()
            val direction = HomeFragmentDirections.actionHomeFragment2ToDetailFragment()
            binding.root.findNavController().navigate(direction)
        }
        val ownerContext = (activity as MainActivity)
        binding.recyclerView.layoutManager = LinearLayoutManager(ownerContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        activity.let {// if activity != null {hacer algo}
            viewModel.notes.observe(viewLifecycleOwner){ notes ->
                adapter.contacts = notes
                adapter.notifyDataSetChanged()
            }
        }


    }
}
