package com.example.app_dioses.ventanas.Dios

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.app_dioses.R
import com.example.app_dioses.databinding.FragmentFragCrearPruebaBinding
import com.example.app_dioses.parametros.Parametros
import com.example.app_dioses.ventanas.LogIn.MainViewModel

class FragCrearPrueba : Fragment() {
    private var _binding: FragmentFragCrearPruebaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragCrearPruebaViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()


    companion object {
        fun newInstance() = FragCrearPrueba()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragCrearPruebaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapterTipos = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listOf(Parametros.eleccion,Parametros.afinidad,Parametros.pp,Parametros.resL))
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spTipoPrueba.adapter = adapterTipos

        binding.spTipoPrueba.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position)
                when (selectedItem as String) {
                    Parametros.pp -> {
                        binding.lbDescripcion.visibility = View.VISIBLE
                        binding.textDescripcion.visibility = View.VISIBLE
                        binding.etDescripcion.visibility = View.VISIBLE
                        binding.lbAtributo.visibility = View.VISIBLE
                        binding.spAtributo.visibility=  View.VISIBLE
                        binding.lbDificultad.visibility = View.VISIBLE
                        binding.etPorcenAciertosDificultad.visibility = View.VISIBLE

                        binding.rgOpCorrecta.visibility = View.GONE
                        binding.lbOp1.visibility = View.GONE
                        binding.lbOp2.visibility = View.GONE
                        binding.textOp1.visibility = View.GONE
                        binding.textOp2.visibility = View.GONE
                        binding.etOp1.visibility = View.GONE
                        binding.etOp2.visibility = View.GONE
                        binding.lbPregunta.visibility = View.GONE
                        binding.textPregunta.visibility = View.GONE
                        binding.etPregunta.visibility = View.GONE
                        binding.lbPalabrasClave.visibility = View.GONE
                        binding.textPalabrasClave.visibility = View.GONE
                        binding.etPalabrasClave.visibility = View.GONE
                        binding.lbValor.visibility = View.GONE
                        binding.etValor.visibility = View.GONE
                        binding.lbPorcenAciertos.visibility = View.GONE
                        binding.lbCorrecta.visibility = View.GONE



                        Log.d("Manuel","se selecciono pp")
                    }
                    Parametros.resL ->  {
                        binding.lbPregunta.visibility = View.VISIBLE
                        binding.textPregunta.visibility = View.VISIBLE
                        binding.etPregunta.visibility = View.VISIBLE
                        binding.textPalabrasClave.visibility = View.VISIBLE
                        binding.etPalabrasClave.visibility = View.VISIBLE
                        binding.lbPorcenAciertos.visibility = View.VISIBLE
                        binding.etPorcenAciertosDificultad.visibility = View.VISIBLE
                        binding.lbPalabrasClave.visibility = View.VISIBLE

                        binding.etValor.visibility = View.GONE
                        binding.rgOpCorrecta.visibility = View.GONE
                        binding.lbOp1.visibility = View.GONE
                        binding.lbOp2.visibility = View.GONE
                        binding.textOp1.visibility = View.GONE
                        binding.textOp2.visibility = View.GONE
                        binding.etOp1.visibility = View.GONE
                        binding.etOp2.visibility = View.GONE
                        binding.lbValor.visibility = View.GONE
                        binding.lbDescripcion.visibility = View.GONE
                        binding.textDescripcion.visibility = View.GONE
                        binding.etDescripcion.visibility = View.GONE
                        binding.lbAtributo.visibility = View.GONE
                        binding.spAtributo.visibility=  View.GONE
                        binding.lbDificultad.visibility = View.GONE
                        binding.lbCorrecta.visibility = View.GONE

                        Log.d("Manuel","se selecciono resL")
                    }
                    Parametros.afinidad -> {
                        binding.lbPregunta.visibility = View.VISIBLE
                        binding.textPregunta.visibility = View.VISIBLE
                        binding.etPregunta.visibility = View.VISIBLE
                        binding.spAtributo.visibility=  View.VISIBLE
                        binding.lbAtributo.visibility = View.VISIBLE


                        binding.rgOpCorrecta.visibility = View.GONE
                        binding.lbOp1.visibility = View.GONE
                        binding.lbOp2.visibility = View.GONE
                        binding.textOp1.visibility = View.GONE
                        binding.textOp2.visibility = View.GONE
                        binding.etOp1.visibility = View.GONE
                        binding.etOp2.visibility = View.GONE
                        binding.lbPalabrasClave.visibility = View.GONE
                        binding.lbDescripcion.visibility = View.GONE
                        binding.textDescripcion.visibility = View.GONE
                        binding.etDescripcion.visibility = View.GONE
                        binding.lbDificultad.visibility = View.GONE
                        binding.lbCorrecta.visibility = View.GONE
                        binding.lbPorcenAciertos.visibility = View.GONE
                        binding.lbValor.visibility = View.GONE
                        binding.etValor.visibility = View.GONE
                        binding.etPorcenAciertosDificultad.visibility = View.GONE
                        binding.lbPorcenAciertos.visibility = View.GONE



                        Log.d("Manuel","se selecciono afinidad")
                    }
                    Parametros.eleccion -> {
                        binding.lbPregunta.visibility = View.VISIBLE
                        binding.textPregunta.visibility = View.VISIBLE
                        binding.etPregunta.visibility = View.VISIBLE
                        binding.lbOp1.visibility = View.VISIBLE
                        binding.lbOp2.visibility = View.VISIBLE
                        binding.textOp1.visibility = View.VISIBLE
                        binding.textOp2.visibility = View.VISIBLE
                        binding.etOp1.visibility = View.VISIBLE
                        binding.etOp2.visibility = View.VISIBLE
                        binding.spAtributo.visibility=  View.VISIBLE
                        binding.lbAtributo.visibility = View.VISIBLE
                        binding.lbValor.visibility = View.VISIBLE
                        binding.etValor.visibility = View.VISIBLE
                        binding.lbCorrecta.visibility = View.VISIBLE
                        binding.rgOpCorrecta.visibility = View.VISIBLE

                        binding.lbDescripcion.visibility = View.GONE
                        binding.textDescripcion.visibility = View.GONE
                        binding.etDescripcion.visibility = View.GONE
                        binding.lbDificultad.visibility = View.GONE
                        binding.etPorcenAciertosDificultad.visibility = View.GONE
                        binding.lbPalabrasClave.visibility = View.GONE
                        binding.textPalabrasClave.visibility = View.GONE
                        binding.etPalabrasClave.visibility = View.GONE
                        binding.lbPorcenAciertos.visibility = View.GONE




                        Log.d("Manuel","se selecciono eleccion")
                    }

                }

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                //Acción cuando no se selecciona nada
            }
        }

        val adapterAtributos = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, Parametros.atributos)
        adapterAtributos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spAtributo.adapter = adapterAtributos

        binding.spAtributo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                //Acción cuando no se selecciona nada
            }
        }





        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnInsertaPrueba.setOnClickListener {
            val isTituloComplete = binding.etTituloPrueba.text.isNullOrEmpty()
            val isDestinoComplete = binding.etDestino.text.isNullOrEmpty()
            val isTipoSelected = binding.spTipoPrueba.selectedItemPosition == AdapterView.INVALID_POSITION

            if (isTituloComplete || isDestinoComplete || isTipoSelected) {
                Toast.makeText(requireContext(), R.string.errRellenaCampos, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            when(binding.spTipoPrueba.selectedItem.toString()){
                Parametros.eleccion -> {
                    // Validar campos visibles cuando se selecciona "eleccion"
                    val isPreguntaComplete = binding.etPregunta.text.isNullOrEmpty()
                    val isOp1Complete = binding.etOp1.text.isNullOrEmpty()
                    val isOp2Complete = binding.etOp2.text.isNullOrEmpty()
                    val isValorComplete = binding.etValor.text.isNullOrEmpty()
                    val isCorrectaComplete = binding.rgOpCorrecta.checkedRadioButtonId == -1
                    val isAtributoSelected = binding.spAtributo.selectedItemPosition == AdapterView.INVALID_POSITION

                    if (isPreguntaComplete || isOp1Complete || isOp2Complete || isValorComplete || isAtributoSelected || isCorrectaComplete) {
                        Toast.makeText(requireContext(), R.string.errRellenaCampos, Toast.LENGTH_SHORT).show()
                    } else {
                        // Proceder con la inserción de la prueba
                        Log.d("Manuel", "Campos completos para elección")
                    }
                }
                Parametros.afinidad -> {
                    // Validar campos visibles cuando se selecciona "afinidad"
                    val isPreguntaComplete = binding.etPregunta.text.isNullOrEmpty()
                    val isAtributoSelected = binding.spAtributo.selectedItemPosition == AdapterView.INVALID_POSITION


                    if (isPreguntaComplete ||   isAtributoSelected  ) {
                        Toast.makeText(requireContext(), R.string.errRellenaCampos, Toast.LENGTH_SHORT).show()
                    } else {
                        // Proceder con la inserción de la prueba
                        Log.d("Manuel", "Campos completos para afinidad")
                    }
                }
                Parametros.pp -> {
                    // Validar campos visibles cuando se selecciona "pp"
                    val isDescripcionComplete = binding.etDescripcion.text.isNullOrEmpty()
                    val isAtributoSelected = binding.spAtributo.selectedItemPosition == AdapterView.INVALID_POSITION
                    val isDificultadComplete = binding.etPorcenAciertosDificultad.text.isNullOrEmpty()

                    if (isDescripcionComplete || isAtributoSelected || isDificultadComplete) {
                        Toast.makeText(requireContext(), R.string.errRellenaCampos, Toast.LENGTH_SHORT).show()
                    } else {
                        // Proceder con la inserción de la prueba
                        Log.d("Manuel", "Campos completos para pp")
                    }
                }
                Parametros.resL -> {
                    // Validar campos visibles cuando se selecciona "resL"
                    val isPreguntaComplete = binding.etPregunta.text.isNullOrEmpty()
                    val isPalabrasClaveComplete = binding.etPalabrasClave.text.isNullOrEmpty()
                    val isDificultadComplete = binding.etPorcenAciertosDificultad.text.isNullOrEmpty()

                    if (isPreguntaComplete || isPalabrasClaveComplete || isDificultadComplete) {
                        Toast.makeText(requireContext(), R.string.errRellenaCampos, Toast.LENGTH_SHORT).show()
                    } else {
                        // Proceder con la inserción de la prueba
                        Log.d("Manuel", "Campos completos para resL")
                    }
                }
            }
        }


        binding.btnCancelarInsertPrueba.setOnClickListener {
            limpiar()
        }
    }
    private fun limpiar(){
        binding.etDescripcion.text?.clear()
        binding.etOp1.text?.clear()
        binding.etOp2.text?.clear()
        binding.etPregunta.text?.clear()
        binding.etPalabrasClave.text?.clear()
        binding.etPorcenAciertosDificultad.text?.clear()
        binding.etValor.text?.clear()
        binding.rgOpCorrecta.clearCheck()
        binding.spAtributo.setSelection(0)
        binding.spTipoPrueba.setSelection(0)

    }
}