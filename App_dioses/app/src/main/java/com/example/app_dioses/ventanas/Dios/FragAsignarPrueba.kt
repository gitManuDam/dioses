package com.example.app_dioses.ventanas.Dios

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app_dioses.R
import com.example.app_dioses.databinding.FragmentFragAsignarPruebaBinding

class FragAsignarPrueba : Fragment() {
    private var _binding: FragmentFragAsignarPruebaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragAsignarPruebaViewModel by viewModels()


    companion object {
        fun newInstance() = FragAsignarPrueba()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFragAsignarPruebaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val idPrueba = arguments?.getInt("idPrueba")
        Log.e("Manuel", "onCreateView: $idPrueba")
        return root
    }
}