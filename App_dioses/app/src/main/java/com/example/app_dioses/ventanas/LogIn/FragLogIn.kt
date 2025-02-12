package com.example.app_dioses.ventanas.LogIn

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.app_dioses.R
import com.example.app_dioses.databinding.FragmentFragLogInBinding
import com.example.app_dioses.modelo.UsuarioLogIn
import com.example.app_dioses.ventanas.Dios.DiosActivity
import com.example.app_dioses.ventanas.Humano.HumanoActivity

class FragLogIn : Fragment() {
    private var _binding: FragmentFragLogInBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: FragLogInViewModel by viewModels()

    companion object {
        fun newInstance() = FragLogIn()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.cerrarSesionVM()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragLogInBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mainViewModel.errorCode.observe(viewLifecycleOwner){error ->
            if (error!=null) {
                when (error) {
                    400 -> Toast.makeText(requireContext(), getString(R.string.errPassword2), Toast.LENGTH_SHORT).show()
                    404 -> Toast.makeText(requireContext(), getString(R.string.errUsuario2), Toast.LENGTH_SHORT).show()
                    200 -> Toast.makeText(requireContext(),getString(R.string.msjSesionIniciada),
                        Toast.LENGTH_SHORT).show()
                }
                mainViewModel.restablecerError()
            }
        }

        mainViewModel.diosLogeado.observe(viewLifecycleOwner){
            if (it!=null){
                val intent = Intent(requireContext(), DiosActivity::class.java)
                intent.putExtra("Usuario", it)
                Log.e("Manuel", "onCreateView: $it")
                startActivity(intent)
                limpiarCampos()
            }
        }
        mainViewModel.humanoLogeado.observe(viewLifecycleOwner){
            if (it!=null){
                val intent = Intent(requireContext(), HumanoActivity::class.java)
                intent.putExtra("Usuario", it)
                Log.e("Manuel", "onCreateView: $it")
                startActivity(intent)
                Toast.makeText(requireContext(), getString(R.string.msjSesionIniciada), Toast.LENGTH_SHORT).show()
                limpiarCampos()
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

        binding.btnIniciarSesion.setOnClickListener {
            if (binding.etUsuarioLogin.text.isNullOrBlank() || binding.etPasswordLogin.text.isNullOrBlank()){
                Toast.makeText(requireContext(), resources.getString(R.string.errRellenaCampos), Toast.LENGTH_SHORT).show()
            }else {
                Log.e("Manuel", "LLamando a loginVM:")
                val usuario = binding.etUsuarioLogin.text.toString().trim()
                val password = binding.etPasswordLogin.text.toString().trim()
                mainViewModel.loginVM(UsuarioLogIn(usuario, password))
            }
        }


        binding.btnRegistro.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.nav_fragRegistro)
        }

        binding.btnSalir.setOnClickListener {
            requireActivity().finish()
        }

    }

    fun limpiarCampos(){
        binding.etUsuarioLogin.setText("")
        binding.etPasswordLogin.setText("")
    }



}