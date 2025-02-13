package com.example.app_dioses.ventanas.Humano

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.app_dioses.R
import com.example.app_dioses.databinding.FragmentFragPuntualBinding
import com.example.app_dioses.databinding.FragmentFragRespuestaLibreBinding
import com.example.app_dioses.modelo.Actualizacion
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.PruebaHumanoRV
import com.example.app_dioses.ventanas.LogIn.MainViewModel
import kotlinx.coroutines.launch

class FragRespuestaLibre : Fragment() {
    private var _binding: FragmentFragRespuestaLibreBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragRespuestaLibreViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    companion object {
        fun newInstance() = FragPuntual()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragRespuestaLibreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prueba: PruebaHumanoRV? = arguments?.getSerializable("Prueba") as? PruebaHumanoRV
        Log.d("Manuel", "Frag Puntual onViewCreated: $prueba")
        viewModel.obtenerPrueba(prueba!!.idPrueba)
        viewModel.preguntaRl.observe(viewLifecycleOwner) {
            Log.d("Manuel", "Frag Puntual onViewCreated: $it $prueba")
            binding.tvTituloPrueba.text = prueba.titulo
            binding.tvPregunta.text = it.pregunta


        }
        binding.btnEnviar.setOnClickListener {

            binding.btnEnviar.visibility = View.GONE
        }



    }
    private fun realizarPrueba(prueba: PruebaHumanoRV) {
        val humano: Humano = mainViewModel.humanoLogeado.value!!
        val preguntaRl = viewModel.preguntaRl.value!!

        // Lista de palabras clave
        val palabrasClave = preguntaRl.palabrasClave.split(",").map { it.trim().lowercase() }

        // Respuesta del usuario en minúsculas y separada por palabras
        val respuestaUsuario = binding.etRespuesta.text.toString().lowercase().split("\\s+".toRegex())

        // Contar palabras clave presentes en la respuesta
        val palabrasAcertadas = palabrasClave.count { it in respuestaUsuario }

        // Calcular el porcentaje de aciertos
        val porcentajeAciertos = (palabrasAcertadas.toDouble() / palabrasClave.size) * 100

        // Verificar si supera el porcentaje mínimo requerido
        val exito = porcentajeAciertos >= preguntaRl.porcenAciertos

        val act = Actualizacion(
            estado = if (exito) 1 else 2,
            idPruebaHumano = prueba.id,
            destinoFin = if (exito) prueba.destino else -prueba.destino
        )
        viewModel.actualizarPrueba(act)

        // Modificamos el destino según el resultado de la prueba
        mainViewModel.humanoLogeado.value!!.destino += if (exito) prueba.destino else -prueba.destino
        viewModel.actualizarDestinoHumano(mainViewModel.humanoLogeado.value!!)

        // Mostrar resultado
        val resultado = if (exito) "Has ganado ✅" else "Has perdido ❌"
        binding.tvResultado.text = "Resultado: $resultado"

    }
}