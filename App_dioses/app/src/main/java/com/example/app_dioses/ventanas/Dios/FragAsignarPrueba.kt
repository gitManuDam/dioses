package com.example.app_dioses.ventanas.Dios

import HumanoAdapter
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
import com.example.app_dioses.databinding.FragmentFragAsignarPruebaBinding
import com.example.app_dioses.modelo.Humano

import com.example.app_dioses.modelo.Prueba
import com.example.app_dioses.ventanas.LogIn.MainViewModel
import com.google.android.material.chip.Chip

class FragAsignarPrueba : Fragment() {
    private var _binding: FragmentFragAsignarPruebaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragAsignarPruebaViewModel by viewModels()
    private val viewModelProtegidos by activityViewModels<FragProtegidosViewModel>()
    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = FragAsignarPrueba()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("Manuel", "onCreate: Iniciando fragmento FragAsignarPrueba")
        viewModelProtegidos.obtenerHumanosProtegidos(mainViewModel.diosLogeado.value!!.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragAsignarPruebaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.e("Manuel", "onCreateView: Fragmento creado correctamente")

        val prueba: Prueba? = arguments?.getSerializable("Prueba") as? Prueba
        Log.e("Manuel", "onCreateView: Prueba recibida -> $prueba")

        // Observa la lista de humanos y actualiza el Spinner cuando cambien los datos
        viewModelProtegidos.humanos.observe(viewLifecycleOwner) { listaHumanos ->
            if (listaHumanos.isNullOrEmpty()) {
                Log.e("Manuel", "onCreateView: La lista de humanos está vacía o no se ha cargado")
            } else {
                Log.e("Manuel", "onCreateView: Lista de humanos cargada con ${listaHumanos.size} elementos")
            }

            val adapterHumano = HumanoAdapter(requireContext(), viewModelProtegidos.humanos.value!!)
            binding.spElegirHumano.adapter = adapterHumano
            Log.e("Manuel", "onCreateView: Adaptador del spinner configurado correctamente")
        }

        binding.spElegirHumano.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as Humano
                Log.e("Manuel", "onItemSelected: Humano seleccionado -> $selectedItem")
                viewModel.seleccionarHumano(selectedItem)



            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.e("Manuel", "onNothingSelected: No se seleccionó ningún humano")
            }
        }

        viewModel.humanosSeleccionado.observe(viewLifecycleOwner) {listaHumanos ->
            val humanosText = listaHumanos.joinToString("\n") { it.nombre } // Asumiendo que 'nombre' es el atributo de Humano

            // Asignar la cadena resultante al TextView
            binding.tvHumanosSeleccionados.text = humanosText
        }

        viewModel.errorCode.observe(viewLifecycleOwner){error->
            if (error!=null){
                //Implementara los mensajes de error para ver el seguimiento
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                viewModel.restError()
            }
        }

        binding.btnAsignarPrueba.setOnClickListener(){
            Log.e("Manuel", "onCreateView: Botón de asignar prueba clickeado")
            viewModel.asignarSeleccionados(prueba!!.id)

        }

        binding.btnAsignarTodos.setOnClickListener(){
            Log.e("Manuel", "onCreateView: Botón de asignar todos clickeado")
            viewModel.asignarTodos(viewModelProtegidos.humanos.value as ArrayList<Humano>,prueba!!.id)

        }

        binding.btnAsignarAfin.setOnClickListener(){
            Log.e("Manuel", "onCreateView: Botón de asignar afin clickeado")
            viewModel.asignarAfin(mainViewModel.diosLogeado.value!!,viewModelProtegidos.humanos.value as ArrayList<Humano>,prueba!!.id)
        }


        return root
    }






    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("Manuel", "onDestroyView: Fragmento destruido")
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Manuel", "onViewCreated: Vista creada correctamente")


    }
}
