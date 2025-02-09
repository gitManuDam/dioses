package com.example.app_dioses.ventanas.Dios

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_dioses.R
import com.example.app_dioses.adaptadores.AdaptadorPruebas
import com.example.app_dioses.databinding.FragmentFragPruebasBinding
import com.example.app_dioses.modelo.Prueba
import com.example.app_dioses.ventanas.LogIn.MainViewModel

class FragPruebas : Fragment() {
    private var _binding: FragmentFragPruebasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragPruebasViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    var datos = ArrayList<Prueba>()
    private lateinit var adaptador: AdaptadorPruebas

    companion object {
        fun newInstance() = FragPruebas()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Manuel", "onCreate: FragPruebas creado")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("Manuel", "onCreateView: Inflando vista de FragPruebas")
        _binding = FragmentFragPruebasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.errorCode.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Log.e("Manuel", "Error recibido: $error")
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.pruebas.observe(viewLifecycleOwner) { pruebas ->
            Log.d("Manuel", "Datos actualizados en LiveData: ${pruebas.size} pruebas recibidas")
            datos.clear()
            datos.addAll(pruebas)
            adaptador.notifyDataSetChanged()
            if (datos.isEmpty()) {
                Log.d("Manuel", "No hay pruebas disponibles")
                binding.tvSinPruebas.visibility = View.VISIBLE
            } else {
                Log.d("Manuel", "Se han cargado pruebas en el RecyclerView")
                binding.tvSinPruebas.visibility = View.GONE
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Manuel", "onDestroyView: Liberando binding de FragPruebas")
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Manuel", "onViewCreated: Obteniendo pruebas para el dios logeado " +
                "${mainViewModel.diosLogeado.value}")
        setupRecyclerView()
        val idDios = mainViewModel.diosLogeado.value?.id
        if (idDios != null) {
            viewModel.obtenerPruebas(idDios)
        } else {
            Log.e("Manuel", "Error: ID del dios logeado es nulo")
        }
        binding.btnAniadirPrueba.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_fragCrearPrueba)
        }
    }

    private fun setupRecyclerView() {
        Log.d("Manuel", "setupRecyclerView: Configurando RecyclerView en FragPruebas")
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyViewPruebas.layoutManager = linearLayoutManager
        adaptador = AdaptadorPruebas(datos, viewModel, mainViewModel, requireContext())
        binding.recyViewPruebas.adapter = adaptador
    }
}
