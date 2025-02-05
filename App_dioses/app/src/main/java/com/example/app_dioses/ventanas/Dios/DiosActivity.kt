package com.example.app_dioses.ventanas.Dios

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.app_dioses.R
import com.example.app_dioses.databinding.ActivityDiosBinding
import com.example.app_dioses.modelo.Dios
import com.example.app_dioses.ventanas.FragPerfil
import com.example.app_dioses.ventanas.LogIn.MainViewModel
import com.google.android.material.navigation.NavigationView

class DiosActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var binding: ActivityDiosBinding
    private val mainViewModel: MainViewModel by viewModels()
    var idFragVolver = R.id.nav_fragProtegidos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDiosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e("Manuel", "Activity Dios onCreate:Intent ${intent.getSerializableExtra("Usuario")}")
        mainViewModel.reiniciarSesionVM(intent.getSerializableExtra("Usuario")!! as Dios)
        Log.e("Manuel", "Activty Dios onCreate:Dios Logueado ${mainViewModel.diosLogeado.value}")

        setSupportActionBar(binding.toolBarDios)
        val drawerLayout: DrawerLayout = binding.drawerLayout



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_cont_dios) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_fragProtegidos, R.id.nav_fragPruebas, R.id.nav_fragPerfil),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        val navView: NavigationView = binding.navigationView
        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when (destination.id) {
                R.id.nav_fragProtegidos -> {
                    supportActionBar?.title = resources.getString(R.string.tituloProtegidos)
                    idFragVolver=R.id.nav_fragProtegidos
                }
                R.id.nav_fragPruebas -> {
                    supportActionBar?.title = resources.getString(R.string.tituloPruebas)
                    idFragVolver=R.id.nav_fragPruebas
                }
                R.id.nav_fragCrearHumano -> {
                    supportActionBar?.title = resources.getString(R.string.tituloCrearHumano)
                    idFragVolver=R.id.nav_fragProtegidos
                }
                R.id.nav_fragCrearPrueba -> {
                    supportActionBar?.title = resources.getString(R.string.tituloCrearPrueba)
                    idFragVolver=R.id.nav_fragCrearPrueba

                }
                R.id.nav_fragAsignarPrueba -> {
                    supportActionBar?.title = resources.getString(R.string.tituloAsignarPrueba)
                    idFragVolver=R.id.nav_fragAsignarPrueba
                }
                R.id.nav_fragPerfil -> {
                    supportActionBar?.title = resources.getString(R.string.tituloPerfil)
                    idFragVolver=R.id.nav_fragPerfil
                }

            }
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_fragProtegidos -> navController.navigate(R.id.nav_fragProtegidos)
                R.id.nav_fragPerfil -> navController.navigate(R.id.nav_fragPerfil)
                R.id.nav_fragPruebas -> navController.navigate(R.id.nav_fragPruebas)

            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_puntos, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_cont_dios) as NavHostFragment
                val fragmentoActual = navHostFragment.childFragmentManager.primaryNavigationFragment
                if ((fragmentoActual is FragProtegidos) || (fragmentoActual is FragPruebas) || (fragmentoActual is FragPerfil) ) {
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                }else if (fragmentoActual is FragCrearHumano) {
                    navController.navigate(R.id.nav_fragProtegidos)
                }else if (fragmentoActual is FragCrearPrueba) {
                    navController.navigate(R.id.nav_fragPruebas)

                }else if(fragmentoActual is FragAsignarPrueba){
                    navController.navigate(R.id.nav_fragPruebas)
                }else {
                    navController.navigate(idFragVolver)
                }
                true
            }

            R.id.nav_cerrarSesion -> {
                Toast.makeText(this, resources.getString(R.string.msjCerrarSesion), Toast.LENGTH_SHORT).show()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}