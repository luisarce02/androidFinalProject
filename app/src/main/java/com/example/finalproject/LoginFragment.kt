package com.example.finalproject


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finalproject.databinding.FragmentLoginBinding
import com.example.finalproject.factory.LoginViewModelFactory
import com.example.finalproject.viewmodels.LoginViewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((requireActivity() as MainActivity).userRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        if (viewModel.getUserId() != null) {
            navigateToHome()
        }
        observeLoginResult()

        return binding.root
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn) {
                navigateToHome()
            } else {
            }
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
    }
}
