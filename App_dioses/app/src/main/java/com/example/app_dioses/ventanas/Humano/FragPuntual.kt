package com.example.app_dioses.ventanas.Humano

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.app_dioses.R
import com.example.app_dioses.databinding.FragmentFragPruebasPendientesBinding
import com.example.app_dioses.databinding.FragmentFragPuntualBinding
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.Prueba
import com.example.app_dioses.modelo.PruebaHumanoRV
import com.example.app_dioses.ventanas.LogIn.MainViewModel
import kotlinx.coroutines.delay

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



        binding.btnEmepzar.setOnClickListener {

        }

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
    }

    suspend fun realizarPrueba(humano: Humano, prueba: PruebaHumanoRV){
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

        // Modificamos el destino según el resultado de la prueba
        humano.destino += if (exito) prueba.destino else -prueba.destino
        delay(22)
        println("${humano.nombre} intenta superar la prueba '${prueba.titulo}" +
                "' con un atributo de $atributoHumano y dificultad ${viewModel.pruebaPuntual.value!!.dificultad}%.")
        println("Probabilidad de éxito calculada: $probabilidadExito%. Resultado: ${if (exito) "Éxito ✅" else "Fracaso ❌"}")
        println("Destino final de ${humano.nombre}: ${humano.destino}")
    }


}