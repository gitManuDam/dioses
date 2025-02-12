package com.example.app_dioses.ventanas.Humano

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.app_dioses.R
import com.example.app_dioses.databinding.ActivityDiosBinding
import com.example.app_dioses.databinding.ActivityHumanoBinding
import com.example.app_dioses.modelo.Dios
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.ventanas.Dios.FragAsignarPrueba
import com.example.app_dioses.ventanas.Dios.FragCrearHumano
import com.example.app_dioses.ventanas.Dios.FragCrearPrueba
import com.example.app_dioses.ventanas.Dios.FragProtegidos
import com.example.app_dioses.ventanas.Dios.FragPruebas
import com.example.app_dioses.ventanas.FragPerfil
import com.example.app_dioses.ventanas.LogIn.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HumanoActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var binding: ActivityHumanoBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHumanoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e("Manuel", "Activity Humano onCreate:Intent ${intent.getSerializableExtra("Usuario")}")
        mainViewModel.reiniciarSesionVM(intent.getSerializableExtra("Usuario")!! as Humano)
        Log.e("Manuel", "Activty Humano onCreate:Dios Logueado ${mainViewModel.humanoLogeado.value}")

        setSupportActionBar(binding.toolbarHumano)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_cont_humano) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_fragPruebasPendientes, R.id.nav_fragHistoricoPruebas, R.id.nav_fragPerfil),

        )
        val navView: BottomNavigationView = binding.bottomNavHumano
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when (destination.id) {
                R.id.nav_fragPruebasPendientes -> {
                    supportActionBar?.title = resources.getString(R.string.tituloPruebasPendientes)

                }
                R.id.nav_fragHistoricoPruebas -> {
                    supportActionBar?.title = resources.getString(R.string.tituloHistoricoPruebas)

                }
                R.id.nav_fragPerfil -> {
                    supportActionBar?.title = resources.getString(R.string.tituloPerfil)

                }
                R.id.nav_fragEleccion -> {
                    supportActionBar?.title = resources.getString(R.string.tituloEleccion)


                }
                R.id.nav_fragPuntual -> {
                    supportActionBar?.title = resources.getString(R.string.tituloPuntual)

                }
                R.id.nav_fragRespuestaLibre -> {
                    supportActionBar?.title = resources.getString(R.string.tituloRespuestaLibre)

                }
                R.id.nav_fragValoracion -> {
                    supportActionBar?.title = resources.getString(R.string.tituloValoracion)

                }

            }
        }

        navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_fragPruebasPendientes -> navController.navigate(R.id.nav_fragPruebasPendientes)
                R.id.nav_fragHistoricoPruebas -> navController.navigate(R.id.nav_fragHistoricoPruebas)
                R.id.nav_fragPerfil -> navController.navigate(R.id.nav_fragPerfil)

            }

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


            R.id.nav_cerrarSesion -> {
                Toast.makeText(this, resources.getString(R.string.msjCerrarSesion), Toast.LENGTH_SHORT).show()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}