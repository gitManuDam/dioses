package com.example.app_dioses.ventanas.Dios

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.app_dioses.R
import com.example.app_dioses.databinding.FragmentFragCrearHumanoBinding
import com.example.app_dioses.ventanas.LogIn.MainViewModel

class FragCrearHumano : Fragment() {
    private var _binding: FragmentFragCrearHumanoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragCrearHumanoViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    companion object {
        fun newInstance() = FragCrearHumano()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragCrearHumanoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.errorCode.observe(viewLifecycleOwner){error->
            if (error != null){
                when(error){
                    400 -> Toast.makeText(requireContext(), getString(R.string.errRegistro), Toast.LENGTH_SHORT).show()
                    201 -> Toast.makeText(requireContext(), getString(R.string.msjHumanoRegistrado), Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(requireContext(), getString(R.string.errHumanoExistente), Toast.LENGTH_SHORT).show()
                }
                viewModel.resetErrorCode()
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

        binding.btnCrearHumanoAceptar.setOnClickListener {
            for (i in 1..binding.etNumeroHumanos.text.toString().toInt()) {
                var humano = viewModel.generarHumano(mainViewModel.diosLogeado.value!!.id)
                viewModel.registrarUsuarioVM(humano)
                viewModel.resetResRegistro()
            }
        }
        binding.btnCrearHumanoCancelar.setOnClickListener {
            binding.etNumeroHumanos.text.clear()
            Toast.makeText(requireContext(), getString(R.string.msjHumanoCancelado), Toast.LENGTH_SHORT).show()
        }

    }
}