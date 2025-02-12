package com.example.app_dioses.ventanas.Humano

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app_dioses.R
import com.example.app_dioses.adaptadores.AdaptadorProtegido
import com.example.app_dioses.adaptadores.AdaptadorPruebaPendientes
import com.example.app_dioses.databinding.FragmentFragProtegidosBinding
import com.example.app_dioses.databinding.FragmentFragPruebasPendientesBinding
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.PreguntaAfinidad
import com.example.app_dioses.modelo.PreguntaEleccion
import com.example.app_dioses.modelo.PreguntaRl
import com.example.app_dioses.modelo.PruebaHumanoRV
import com.example.app_dioses.modelo.PruebaPuntales
import com.example.app_dioses.ventanas.Dios.FragProtegidosViewModel
import com.example.app_dioses.ventanas.LogIn.MainViewModel

class FragPruebasPendientes : Fragment() {
    private var _binding: FragmentFragPruebasPendientesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragPruebasPendientesViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    var datos : ArrayList<PruebaHumanoRV> = ArrayList()
    lateinit var adaptador : AdaptadorPruebaPendientes


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
        _binding = FragmentFragPruebasPendientesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Log.e("Manuel", "FragProtegidos.onCreateView: probando")
        viewModel.pruebasPendientes.observe(viewLifecycleOwner) { pruebasPendientes ->
            Log.e("Manuel", "Observe.FragProtegidos.humanos.onCreateView: $pruebasPendientes")
            datos.clear()
            datos.addAll(pruebasPendientes)
            adaptador.notifyDataSetChanged()
            if (datos.isEmpty()) {
                binding.tvSinPruebasPendientes.visibility = View.VISIBLE
            } else {
                binding.tvSinPruebasPendientes.visibility = View.GONE
            }

        }

        viewModel.pruebaDetalle.observe(viewLifecycleOwner) { pruebaDetalle ->
            when (pruebaDetalle) {
                is PruebaPuntales ->{
                    mostrarDialogoPruebaPuntales(pruebaDetalle)
                    viewModel.restPruebaDetalle()

                }
                is PreguntaRl->{
                    mostrarDialogoPreguntaRl(pruebaDetalle)
                    viewModel.restPruebaDetalle()

                }
                is PreguntaEleccion->{
                    mostrarDialogoPreguntaEleccion(pruebaDetalle)
                    viewModel.restPruebaDetalle()

                }
                is PreguntaAfinidad->{
                    mostrarDialogoPreguntaAfinidad(pruebaDetalle)
                    viewModel.restPruebaDetalle()

                }
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

        Log.e("Manuel", "onViewCreated: FragProtegidos ${mainViewModel.diosLogeado.value}")
        viewModel.obtenerPruebasPendientes(mainViewModel.humanoLogeado.value!!.id)



    }
    private fun setupRecyclerView() {
        Log.e("Manuel", "setupRecyclerView: FragProtegidos")
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyViewPruebasPendientes.layoutManager = linearLayoutManager
        adaptador = AdaptadorPruebaPendientes(datos,  viewModel, mainViewModel,  requireContext())
        binding.recyViewPruebasPendientes.adapter = adaptador

    }

    private fun mostrarDialogoPreguntaRl(preguntaRl: PreguntaRl) {
        AlertDialog.Builder(requireContext())
            .setTitle("Detalles de la Pregunta RL")
            .setMessage(

                        "Pregunta: ${preguntaRl.pregunta}\n" +
                        "Porcentaje de Aciertos: ${preguntaRl.porcenAciertos}%"
            )
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
    private fun mostrarDialogoPreguntaAfinidad(preguntaAfinidad: PreguntaAfinidad) {
        AlertDialog.Builder(requireContext())
            .setTitle("Detalles de la Pregunta de Afinidad")
            .setMessage(

                        "Pregunta: ${preguntaAfinidad.pregunta}\n"

            )
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
    private fun mostrarDialogoPreguntaEleccion(preguntaEleccion: PreguntaEleccion) {
        AlertDialog.Builder(requireContext())
            .setTitle("Detalles de la Pregunta de Elección")
            .setMessage(

                        "Pregunta: ${preguntaEleccion.pregunta}\n" +
                        "Opción 1: ${preguntaEleccion.opcion1}\n" +
                        "Opción 2: ${preguntaEleccion.opcion2}\n"

            )
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
    private fun mostrarDialogoPruebaPuntales(pruebaPuntales: PruebaPuntales) {
        AlertDialog.Builder(requireContext())
            .setTitle("Detalles de la Prueba de Puntuales")
            .setMessage(
                        "Descripción: ${pruebaPuntales.descripcion}\n"
            )
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

}