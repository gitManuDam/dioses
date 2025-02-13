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
import com.example.app_dioses.databinding.FragmentFragEleccionBinding
import com.example.app_dioses.databinding.FragmentFragRespuestaLibreBinding
import com.example.app_dioses.modelo.Actualizacion
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.PruebaHumanoRV
import com.example.app_dioses.ventanas.LogIn.MainViewModel

class FragEleccion : Fragment() {

    private val viewModel: FragEleccionViewModel by viewModels()
    private var _binding: FragmentFragEleccionBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = FragEleccion()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragEleccionBinding.inflate(inflater, container, false)
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
        viewModel.preguntaEleccion.observe(viewLifecycleOwner) {
            Log.d("Manuel", "Frag Puntual onViewCreated: $it $prueba")
            binding.tvTituloPrueba.text = prueba.titulo
            binding.tvPregunta.text = it.pregunta
            binding.btnOp1.text = it.opcion1
            binding.btnOp2.text = it.opcion2


        }
        binding.btnOp1.setOnClickListener {
            if(viewModel.isPruebaAct.value == false){
                realizarPrueba(prueba,binding.btnOp1.text.toString())
            }



        }
        binding.btnOp2.setOnClickListener {
            if(viewModel.isPruebaAct.value == false){
                realizarPrueba(prueba,binding.btnOp2.text.toString())
            }
        }


    }

    private fun realizarPrueba(prueba: PruebaHumanoRV, opcion: String) {
        val humano: Humano = mainViewModel.humanoLogeado.value!!
        val preguntaRl = viewModel.preguntaEleccion.value!!

        val correcta = preguntaRl.correcta // Respuesta correcta
        val atributoPrueba = preguntaRl.atributo // Atributo que la prueba evalúa
        val valorPrueba = preguntaRl.valor // Valor del atributo que la prueba requiere
        val destinoBase = prueba.destino // Destino base que se gana o pierde

        // Obtener el valor del atributo del humano
        val atributoHumano = when (atributoPrueba) {
            "sabiduria" -> humano.sabiduria
            "nobleza" -> humano.nobleza
            "virtud" -> humano.virtud
            "maldad" -> humano.maldad
            "audacia" -> humano.audacia
            else -> 1 // Si no encuentra el atributo, asumimos el mínimo
        }

        // Verificar si la respuesta es correcta
        val exito = opcion == correcta

        // Calcular la diferencia entre el atributo del humano y el requerido
        val diferencia = kotlin.math.abs(atributoHumano - valorPrueba)

        // Bonificación según la diferencia
        val bonificacion = when (diferencia) {
            0 -> 1.5 // Si coincide exactamente, se gana un 50% más de destino
            1 -> 1.2 // Si está cerca (±1), se gana un 20% más
            2 -> 1.0 // Diferencia de 2, sin bonificación
            else -> 0.8 // Si la diferencia es mayor a 2, gana menos
        }

        // Ajuste del destino
        val destinoGanado = if (exito) (destinoBase * bonificacion).toInt() else -destinoBase

        // Aplicar el destino al humano
        mainViewModel.humanoLogeado.value!!.destino += destinoGanado
        viewModel.actualizarDestinoHumano(mainViewModel.humanoLogeado.value!!)

        val act = Actualizacion(
            estado = if (exito) 1 else 2,
            idPruebaHumano = prueba.id,
            destinoFin = if (exito) destinoGanado else -destinoBase
        )
        viewModel.actualizarPrueba(act)

        // Mostrar resultado
        val resultado = if (exito) "Has ganado ✅" else "Has perdido ❌"
        binding.tvResultado.text = " $resultado"

    }

}