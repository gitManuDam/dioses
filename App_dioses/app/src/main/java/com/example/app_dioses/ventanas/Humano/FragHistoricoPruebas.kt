package com.example.app_dioses.ventanas.Humano

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app_dioses.R

class FragHistoricoPruebas : Fragment() {

    companion object {
        fun newInstance() = FragHistoricoPruebas()
    }

    private val viewModel: FragHistoricoPruebasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_frag_historico_pruebas, container, false)
    }
}