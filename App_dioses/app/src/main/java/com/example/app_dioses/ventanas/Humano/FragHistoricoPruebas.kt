package com.example.app_dioses.ventanas.Humano

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_dioses.R
import com.example.app_dioses.adaptadores.AdaptadorHistoricoPruebas
import com.example.app_dioses.adaptadores.AdaptadorProtegido
import com.example.app_dioses.adaptadores.AdaptadorPruebaPendientes
import com.example.app_dioses.databinding.FragmentFragHistoricoPruebasBinding
import com.example.app_dioses.databinding.FragmentFragProtegidosBinding
import com.example.app_dioses.databinding.FragmentFragPruebasPendientesBinding
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.PruebaHumanoRV
import com.example.app_dioses.ventanas.Dios.FragProtegidosViewModel
import com.example.app_dioses.ventanas.LogIn.MainViewModel

class FragHistoricoPruebas : Fragment() {
    private var _binding: FragmentFragHistoricoPruebasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragPruebasPendientesViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    var datos : ArrayList<PruebaHumanoRV> = ArrayList()
    lateinit var adaptador : AdaptadorHistoricoPruebas


    companion object {
        fun newInstance() = FragPruebasPendientes()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("Manuel", "FragProtegidos.onCreateView:ver dioslogueado ${mainViewModel.diosLogeado.value}")
        _binding = FragmentFragHistoricoPruebasBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Log.e("Manuel", "FragProtegidos.onCreateView: probando")
        viewModel.historico.observe(viewLifecycleOwner) { historico ->
            Log.e("Manuel", "Observe.FragProtegidos.humanos.onCreateView: $historico")
            datos.clear()
            datos.addAll(historico)
            adaptador.notifyDataSetChanged()
            if (datos.isEmpty()) {
                binding.tvSinPruebas.visibility = View.VISIBLE
            } else {
                binding.tvSinPruebas.visibility = View.GONE
            }

        }


        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("Manuel", "onViewCreated: FragProtegidos ")
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        Log.e("Manuel", "onViewCreated: FragProtegidos ${mainViewModel.humanoLogeado.value}")
        viewModel.obtenerHistorico(mainViewModel.humanoLogeado.value!!.id)



    }
    private fun setupRecyclerView() {
        Log.e("Manuel", "setupRecyclerView: FragProtegidos")
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyViewHistorico.layoutManager = linearLayoutManager
        adaptador = AdaptadorHistoricoPruebas(datos,  viewModel, mainViewModel,  requireContext())
        binding.recyViewHistorico.adapter = adaptador

    }
}