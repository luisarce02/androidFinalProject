package com.example.finalproject

import android.R.attr.name
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.finalproject.databinding.FragmentDetailBinding
import com.example.finalproject.viewmodels.NotesDetailViewModel
import com.example.finalproject.viewmodels.NotesSharedViewModel


class DetailFragment : Fragment(R.layout.fragment_detail) {
    lateinit var binding: FragmentDetailBinding
    lateinit var viewModel: NotesSharedViewModel
    lateinit var detailViewModel: NotesDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).notesSharedViewModel
        detailViewModel = (activity as MainActivity).notesDetailViewModel
        binding.viewModel = viewModel
        binding.detailsViewModel = detailViewModel
        binding.lifecycleOwner = this
        detailViewModel.updateTexts()
        binding.button.setOnClickListener {
            detailViewModel.save()
            // forma tradicional
            binding.root.findNavController().navigate(R.id.action_detailFragment_to_homeFragment2)
        }

        binding.button2.setOnClickListener {
            detailViewModel.delete()
            // forma tradicional
            binding.root.findNavController().navigate(R.id.action_detailFragment_to_homeFragment2)
        }

        binding.button4.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=${detailViewModel.latitud.value},${detailViewModel.longitud.value} (" + name + ")")
            )
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent)
        }
    }
}
