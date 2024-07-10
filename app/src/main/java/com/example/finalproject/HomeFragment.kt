package com.example.finalproject

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.factory.LoginViewModelFactory
import com.example.finalproject.viewmodels.LoginViewModel
import com.example.finalproject.viewmodels.NotesSharedViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: NotesSharedViewModel
    lateinit var adapter: NotesRecyclerViewAdapter

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((requireActivity() as MainActivity).userRepository)
    }

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
        viewModel = (activity as MainActivity).notesSharedViewModel
        viewModel.getAllNotes() // llamada a la api
        setupRecyclerView()
        setupAddButton()

        binding.button3.setOnClickListener {
            // forma tradicional
            loginViewModel.deleteUserId()
            binding.root.findNavController().navigate(R.id.action_homeFragment2_to_loginFragment)
        }
    }

    private fun setupAddButton(){
        binding.floatingActionButton2.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragment2ToDetailFragment()
            binding.root.findNavController().navigate(direction)
        }
    }

    private fun setupRecyclerView() {
        adapter = NotesRecyclerViewAdapter(listOf()) { contact ->
            viewModel.selectNote(contact)
            if (isInternetAvailable(requireContext())) {
                // Redireccionar al Detail fragment
                val direction = HomeFragmentDirections.actionHomeFragment2ToDetailFragment()
                binding.root.findNavController().navigate(direction)
            } else {
                // Mostrar un Toast indicando que se requiere internet
                Toast.makeText(requireContext(), "Esta función requiere internet", Toast.LENGTH_SHORT).show()
            }
        }
        val ownerContext = (activity as MainActivity)
        binding.recyclerView.layoutManager = LinearLayoutManager(ownerContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        activity?.let {
            viewModel.notes.observe(viewLifecycleOwner) { notes ->
                adapter.contacts = notes
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}
