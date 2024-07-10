package com.example.finalproject

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.factory.NoteDetailsViewModelFactory
import com.example.finalproject.factory.NotesViewModelFactory
import com.example.finalproject.factory.LoginViewModelFactory
import com.example.finalproject.repositories.UserRepository
import com.example.finalproject.viewmodels.NotesDetailViewModel
import com.example.finalproject.viewmodels.NotesSharedViewModel
import com.example.finalproject.viewmodels.LoginViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    lateinit var userRepository: UserRepository
    lateinit var notesSharedViewModel: NotesSharedViewModel
    lateinit var notesDetailViewModel: NotesDetailViewModel
    private lateinit var locationProvider: FusedLocationProviderClient
    private val permissionsRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // nos dieron el permiso de fine location
                tryGetLastLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // nos dieron el permiso de coarse location
                tryGetLastLocation()
            } else -> {
            // no location
            // mostrar error solicitando y pedir al usuario a settings
        }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        locationProvider = LocationServices.getFusedLocationProviderClient(this)

        tryGetLastLocation()


        val userDataStore = SharedPreferencesUserDataStore(applicationContext)
        userRepository = UserRepository(userDataStore)


        val factory = LoginViewModelFactory(userRepository)
        val viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        // Recupera el ID del usuario
        val userId = viewModel.getUserId()
        if (userId != null) {
            println("User ID: $userId")
        } else {
            println("No User ID found")
        }

        val factoryHome = NotesViewModelFactory(applicationContext)
        notesSharedViewModel = ViewModelProvider(this, factoryHome).get(NotesSharedViewModel::class.java)

        val detailsFactory = NoteDetailsViewModelFactory(notesSharedViewModel, userId)
        notesDetailViewModel = ViewModelProvider(this, detailsFactory).get(
            NotesDetailViewModel::class.java)


    }

    fun tryGetLastLocation() {
        val hasFineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (!hasCoarseLocation && !hasFineLocation) {
            // request permission
            permissionsRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
            return
        }
        locationProvider.lastLocation.addOnSuccessListener {location ->
            if (location != null) {
                notesDetailViewModel.latitud.value = location.latitude
                notesDetailViewModel.longitud.value = location.longitude
            }
        }
    }
}
