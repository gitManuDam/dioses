package com.example.app_dioses.ventanas.Humano

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import com.example.app_dioses.R
import com.example.app_dioses.databinding.FragmentFragEleccionBinding
import com.example.app_dioses.databinding.FragmentFragValoracionBinding
import com.example.app_dioses.modelo.Actualizacion
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.modelo.PruebaHumanoRV
import com.example.app_dioses.ventanas.LogIn.MainViewModel

class FragValoracion : Fragment() {
    private val viewModel: FragValoracionViewModel by viewModels()
    private var _binding: FragmentFragValoracionBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = FragValoracion()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragValoracionBinding.inflate(inflater, container, false)
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
        viewModel.preguntaAfinidad.observe(viewLifecycleOwner) {
            Log.d("Manuel", "Frag Puntual onViewCreated: $it $prueba")
            binding.tvPregunta.text = prueba.titulo
            binding.tvPregunta.text = it.pregunta

        }
        binding.sbRespuesta.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvValorRespuesta.text = "$progress"
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        binding.btnEnviar.setOnClickListener {
            realizarPrueba(prueba, binding.sbRespuesta.progress)
            binding.btnEnviar.visibility = View.INVISIBLE

        }



    }

    private fun realizarPrueba(prueba: PruebaHumanoRV, progress: Int) {
        val humano: Humano = mainViewModel.humanoLogeado.value!!
        val preguntaAfinidad = viewModel.preguntaAfinidad.value!!

        val atributoPrueba = preguntaAfinidad.atributo // Atributo que la prueba evalúa
        val destinoBase = prueba.destino // Destino base que se gana o pierde

        // Obtener el valor del atributo del humano
        val atributoHumano = when (atributoPrueba) {
            "sabiduria" -> humano.sabiduria
            "nobleza" -> humano.nobleza
            "virtud" -> humano.virtud
            "maldad" -> humano.maldad
            "audacia" -> humano.audacia
            else -> 1 // Valor mínimo por defecto
        }

        // Calcular la diferencia entre el progreso marcado y el atributo del humano
        val diferencia = kotlin.math.abs(progress - atributoHumano)
        val exito = diferencia <= 2
        // Determinar la bonificación o penalización según la diferencia
        val bonificacion = when (diferencia) {
            0 -> 1.5  // Acierto exacto → +50%
            1 -> 1.2  // Cercano (±1) → +20%
            2 -> 1.0  // Diferencia de 2 → Sin bonificación
            else -> -1.0 // Si la diferencia es mayor a 2 → Pierde destino
        }

        // Calcular destino final con bonificación o penalización
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