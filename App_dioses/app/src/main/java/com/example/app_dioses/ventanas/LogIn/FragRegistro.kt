package com.example.app_dioses.ventanas.LogIn

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
import com.example.app_dioses.databinding.FragmentFragRegistroBinding
import com.example.app_dioses.modelo.UsuarioLogIn

class FragRegistro : Fragment() {

    private var _binding: FragmentFragRegistroBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: FragRegistroViewModel by viewModels()

    companion object {
        fun newInstance() = FragRegistro()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.cerrarSesionVM()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragRegistroBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.errorCode.observe(viewLifecycleOwner){error ->
            if (error!=null) {
                when (error) {
                    400 -> Toast.makeText(requireContext(), getString(R.string.errPassword2), Toast.LENGTH_SHORT).show()
                    404 -> Toast.makeText(requireContext(), getString(R.string.errUsuario2), Toast.LENGTH_SHORT).show()
                    200 -> Toast.makeText(requireContext(),getString(R.string.msjSesionIniciada),
                        Toast.LENGTH_SHORT).show()
                }
                viewModel.restablecerError()
            }
        }

        viewModel.boolRegistro.observe(viewLifecycleOwner){
            if (it!=null){
                if (it){
                    Toast.makeText(requireContext(), getString(R.string.msjRegistroExitoso), Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                    Navigation.findNavController(requireView()).navigate(R.id.nav_fragLogIn)
                }else{
                    Toast.makeText(requireContext(), getString(R.string.errRegistro), Toast.LENGTH_SHORT).show()
                }
                viewModel.restablecerboolRegistro()
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

        binding.btnRegistrarRegistro.setOnClickListener {
            Log.e("Manuel", binding.etCorreo.text.toString())
            Log.e("Manuel", binding.etClaveRegistro.text.toString())
            Log.e("Manuel", binding.etConfClaveRegistro.text.toString())
            Log.e("Manuel", binding.etHumanoRegistro.text.toString())
            if (binding.etHumanoRegistro.text.isNullOrBlank() ||
                binding.etClaveRegistro.text.isNullOrBlank()||
                binding.etCorreo.text.isNullOrBlank()||
                binding.etConfClaveRegistro.text.isNullOrBlank()){
                Toast.makeText(requireContext(), resources.getString(R.string.errRellenaCampos), Toast.LENGTH_SHORT).show()
            }else if (binding.etClaveRegistro.text.toString() != binding.etConfClaveRegistro.text.toString()){

                Toast.makeText(requireContext(), resources.getString(R.string.errConfirmarClave), Toast.LENGTH_SHORT).show()

            }else if (!correoValido(binding.etCorreo.text.toString())){
                Toast.makeText(requireContext(), resources.getString(R.string.errCorreo), Toast.LENGTH_SHORT).show()

            }else {
                Log.e("Manuel", "LLamando a loginVM:")
                val nombre = binding.etHumanoRegistro.text.toString().trim()
                val correo = binding.etCorreo.text.toString().trim()
                val clave = binding.etClaveRegistro.text.toString().trim()
                viewModel.crearHumano(nombre, correo, clave)

            }
        }




        binding.btnCancelarRegistro.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.nav_fragLogIn)
        }

    }

    fun limpiarCampos(){
        binding.etHumanoRegistro.setText("")
        binding.etClaveRegistro.setText("")
        binding.etCorreo.setText("")
        binding.etConfClaveRegistro.setText("")

    }

    fun correoValido(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
        return email.matches(emailRegex)
    }
}