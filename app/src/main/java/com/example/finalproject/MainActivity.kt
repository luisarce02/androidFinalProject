package com.example.finalproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.factory.LoginViewModelFactory
import com.example.finalproject.repositories.UserRepository
import com.example.finalproject.viewmodels.LoginViewModel

class MainActivity : AppCompatActivity() {
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userDataStore = SharedPreferencesUserDataStore(applicationContext)
        userRepository = UserRepository(userDataStore)

        // Configurar ViewModelProvider con LoginViewModelFactory
        val factory = LoginViewModelFactory(userRepository)
        val viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }
}
