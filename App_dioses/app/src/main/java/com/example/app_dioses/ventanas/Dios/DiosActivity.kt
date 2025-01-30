package com.example.app_dioses.ventanas.Dios

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.app_dioses.R
import com.example.app_dioses.databinding.ActivityDiosBinding
import com.example.app_dioses.modelo.Dios
import com.example.app_dioses.ventanas.LogIn.MainViewModel
import com.google.android.material.navigation.NavigationView

class DiosActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var binding: ActivityDiosBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDiosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e("Manuel", "onCreate:Intent ${intent.getSerializableExtra("Usuario")}")
        mainViewModel.reiniciarSesionVM(intent.getSerializableExtra("Usuario")!! as Dios)

        setSupportActionBar(binding.toolBarDios)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_cont_dios) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_fragProtegidos -> {
                    binding.toolBarDios.title = resources.getString(R.string.tituloProtegidos)
                }

            }
        }


    }

}