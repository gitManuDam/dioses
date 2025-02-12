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
import com.example.app_dioses.databinding.FragmentFragPruebasPendientesBinding
import com.example.app_dioses.databinding.FragmentFragPuntualBinding
import com.example.app_dioses.modelo.Actualizacion
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.Prueba
import com.example.app_dioses.modelo.PruebaHumanoRV
import com.example.app_dioses.ventanas.LogIn.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragPuntual : Fragment() {
    private var _binding: FragmentFragPuntualBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragPuntualViewModel by viewModels()
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
        _binding = FragmentFragPuntualBinding.inflate(inflater, container, false)
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
        viewModel.obtenerPruebaPuntual(prueba!!.idPrueba)
        viewModel.pruebaPuntual.observe(viewLifecycleOwner) {
            Log.d("Manuel", "Frag Puntual onViewCreated: $it $prueba")
            binding.tvTituloPrueba.text = prueba.titulo

        }
        binding.btnEmepzar.setOnClickListener {
            lifecycleScope.launch {
                realizarPrueba(prueba)
            }
            binding.btnEmepzar.visibility = View.GONE
        }



    }

    suspend fun realizarPrueba( prueba: PruebaHumanoRV){
        val humano: Humano = mainViewModel.humanoLogeado.value!!
        val atributoHumano = when (viewModel.pruebaPuntual.value!!.atributo) {
            "sabiduria" -> humano.sabiduria
            "nobleza" -> humano.nobleza
            "virtud" -> humano.virtud
            "maldad" -> humano.maldad
            "audacia" -> humano.audacia
            else -> 1 // Si no encuentra el atributo, asumimos el mínimo
        }


        // Fórmula para calcular la probabilidad de éxito
        val probabilidadExito = ((atributoHumano * 20) / (viewModel.pruebaPuntual.value!!.dificultad + 1)).coerceIn(0, 100)

        // Generamos un número aleatorio entre 1 y 100
        val resultado = (1..100).random()

        val exito = resultado <= probabilidadExito
        val act = Actualizacion(
            estado = if (exito) 1 else 2,
            idPruebaHumano = prueba.id,
            destinoFin = if (exito) prueba.destino else -prueba.destino
        )
        viewModel.actualizarPruebaPuntual(act)

        // Modificamos el destino según el resultado de la prueba
        mainViewModel.humanoLogeado.value!!.destino += if (exito) prueba.destino else -prueba.destino
        viewModel.actualizarDestinoHumano(mainViewModel.humanoLogeado.value!!)
        delay(22)
        binding.tvRegistroPrueba.append("${humano.nombre} intenta superar la prueba '${prueba.titulo}" )
         delay(2000)

        binding.tvRegistroPrueba.append("\nProbabilidad de éxito calculada: $probabilidadExito%. Resultado: ${if (exito) "Éxito ✅" else "Fracaso ❌"}")
        delay(2000)
        binding.tvRegistroPrueba.append("\nDestino final de ${humano.nombre}: ${humano.destino}")
    }


}