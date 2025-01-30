package com.example.app_dioses.ventanas.Dios

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_dioses.adaptadores.AdaptadorProtegido
import com.example.app_dioses.databinding.FragmentFragProtegidosBinding
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.ventanas.LogIn.MainViewModel

class FragProtegidos : Fragment() {
    private var _binding: FragmentFragProtegidosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragProtegidosViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    var datos : ArrayList<Humano> = ArrayList()
    lateinit var adaptador : AdaptadorProtegido

    companion object {
        fun newInstance() = FragProtegidos()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("Manuel", "FragProtegidos.onCreateView: ${mainViewModel.diosLogeado.value}")
        _binding = FragmentFragProtegidosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Log.e("Manuel", "FragProtegidos.onCreateView: probando")
        viewModel.humanos.observe(viewLifecycleOwner) { humanos ->
            Log.e("Manuel", "Observe.FragProtegidos.humanos.onCreateView: $humanos")
            datos.clear()
            datos.addAll(humanos)
            adaptador.notifyDataSetChanged()
            if (datos.isEmpty()) {
                binding.tvSinHumanos.visibility = View.VISIBLE
            } else {
                binding.tvSinHumanos.visibility = View.GONE
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
        viewModel.obtenerHumanosProtegidos(mainViewModel.diosLogeado.value!!.id)

    }
    private fun setupRecyclerView() {
        Log.e("Manuel", "setupRecyclerView: ")
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyViewRanking.layoutManager = linearLayoutManager
        adaptador = AdaptadorProtegido(datos, requireContext(), mainViewModel, viewModel)
        binding.recyViewRanking.adapter = adaptador

    }
}