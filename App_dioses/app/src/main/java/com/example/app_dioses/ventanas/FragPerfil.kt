package com.example.app_dioses.ventanas

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.app_dioses.databinding.FragmentFragPerfilBinding
import com.example.app_dioses.ventanas.LogIn.MainViewModel
import java.io.File
import java.io.FileOutputStream

class FragPerfil : Fragment() {
    private val viewModel: FragPerfilViewModel by viewModels()
    private var _binding: FragmentFragPerfilBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance() = FragPerfil()
    }

    private lateinit var bitmap: Bitmap
    private val cameraRequest = 1888
    var uriImagen: Uri? = null

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("Manuel", "[FragPerfil] Imagen seleccionada desde la galería: $uri")
            uriImagen = uri
            binding.ivPerfil.setImageURI(uri)
        } else {
            Log.d("Manuel", "[FragPerfil] No se seleccionó ninguna imagen de la galería")
        }
    }

    val openCamera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            Log.d("Manuel", "[FragPerfil] Imagen capturada desde la cámara")
            this.bitmap = bitmap
            binding.ivPerfil.setImageBitmap(bitmap)
        } else {
            Log.d("Manuel", "[FragPerfil] No se tomó ninguna foto")
        }
    }

    val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("Manuel", "[FragPerfil] Permiso de cámara concedido, abriendo cámara")
            openCamera.launch()
        } else {
            Log.d("Manuel", "[FragPerfil] Permiso de cámara denegado")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Manuel", "[FragPerfil] onCreate del fragmento")

        viewModel.urlFoto.observe(this) { url ->
            var urlFoto=url?.replace("http://", "https://")
            Log.d("Manuel", "[FragPerfil] Se observó un cambio en la URL de la foto: $url")
            try {
                if (mainViewModel.humanoLogeado.value != null) {
                    mainViewModel.humanoLogeado.value?.foto = urlFoto!!
                    mainViewModel.humanoLogeado.value!!.clave = binding.etClavePerfil.text.toString()
                    viewModel.actualizarHumano(mainViewModel.humanoLogeado.value!!)
                } else if (mainViewModel.diosLogeado.value != null) {
                    mainViewModel.diosLogeado.value?.foto = urlFoto!!
                    mainViewModel.diosLogeado.value!!.clave = binding.etClavePerfil.text.toString()
                    viewModel.actualizarDios(mainViewModel.diosLogeado.value!!)
                }
                viewModel.resetUrlFoto()
            } catch (e: Exception) {
                Log.e("Manuel", "[FragPerfil] Error actualizando humano/dios: ${e.message}", e)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFragPerfilBinding.inflate(inflater, container, false)
        Log.d("Manuel", "[FragPerfil] onCreateView ejecutado")
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Manuel", "[FragPerfil] onDestroyView ejecutado")
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Manuel", "[FragPerfil] onViewCreated ejecutado")

        try {
            cargarDatos()
        } catch (e: Exception) {
            Log.e("Manuel", "[FragPerfil] Error en cargarDatos: ${e.message}", e)
        }

        binding.btnCamaraPerfil.setOnClickListener {
            Log.d("Manuel", "[FragPerfil] Botón cámara presionado")
            requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }

        binding.btnGaleriaPerfil.setOnClickListener {
            Log.d("Manuel", "[FragPerfil] Botón galería presionado")
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnEditarPerfil.setOnClickListener {
            Log.d("Manuel", "[FragPerfil] Modo edición activado")
            binding.btnAceptarPerfil.visibility = View.VISIBLE
            binding.btnCancelarPerfil.visibility = View.VISIBLE
            binding.btnCamaraPerfil.visibility = View.VISIBLE
            binding.btnGaleriaPerfil.visibility = View.VISIBLE
            binding.btnEditarPerfil.visibility = View.INVISIBLE
            binding.etClavePerfil.isEnabled = true
        }

        binding.btnCancelarPerfil.setOnClickListener {
            Log.d("Manuel", "[FragPerfil] Edición cancelada, restaurando datos")
            cargarDatos()
            binding.btnAceptarPerfil.visibility = View.INVISIBLE
            binding.btnCancelarPerfil.visibility = View.INVISIBLE
            binding.btnCamaraPerfil.visibility = View.INVISIBLE
            binding.btnGaleriaPerfil.visibility = View.INVISIBLE
            binding.btnEditarPerfil.visibility = View.VISIBLE
            binding.etClavePerfil.isEnabled = false
        }

        binding.btnAceptarPerfil.setOnClickListener {
            Log.d("Manuel", "[FragPerfil] Guardando cambios en el perfil")
            try {
                val bitmap2 = (binding.ivPerfil.drawable as BitmapDrawable).bitmap
                val file = File(requireContext().cacheDir, "temp_image.jpg")
                val outputStream = FileOutputStream(file)
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                viewModel.subirImagen(file)
            } catch (e: Exception) {
                Log.e("Manuel", "[FragPerfil] Error guardando imagen: ${e.message}", e)
            }
        }
    }

    private fun limpiar() {
        Log.d("Manuel", "[FragPerfil] Limpiando campos del perfil")
        binding.etNombre.text?.clear()
        binding.etClavePerfil.text?.clear()
        binding.tvSabiduria.text = ""
        binding.tvNobleza.text = ""
        binding.tvVirtud.text = ""
        binding.tvMaldad.text = ""
        binding.tvAudadcia.text = ""
        binding.ivPerfil.setImageURI(null)
    }

    private fun cargarDatos() {
        Log.d("Manuel", "[FragPerfil] Cargando datos del perfil")
        try {
            limpiar()
            if (mainViewModel.diosLogeado.value != null) {
                val dios = mainViewModel.diosLogeado.value
                binding.etNombre.append(dios?.nombre)
                binding.etClavePerfil.append(dios?.clave)
                binding.tvSabiduria.text = dios?.sabiduria.toString()
                binding.tvNobleza.text = dios?.nobleza.toString()
                binding.tvVirtud.text = dios?.virtud.toString()
                binding.tvMaldad.text = dios?.maldad.toString()
                binding.tvAudadcia.text = dios?.audacia.toString()
                Glide.with(requireContext()).load(dios?.foto).into(binding.ivPerfil)
                binding.lbEstado.visibility= View.GONE
                binding.tvEstado.visibility= View.GONE
                binding.lbDestino.visibility= View.GONE
                binding.tvDestino.visibility= View.GONE
            } else if (mainViewModel.humanoLogeado.value != null) {
                val humano = mainViewModel.humanoLogeado.value
                binding.etNombre.append(humano?.nombre)
                binding.etClavePerfil.append(humano?.clave)
                binding.tvSabiduria.text = humano?.sabiduria.toString()
                binding.tvNobleza.text = humano?.nobleza.toString()
                binding.tvVirtud.text = humano?.virtud.toString()
                binding.tvMaldad.text = humano?.maldad.toString()
                binding.tvAudadcia.text = humano?.audacia.toString()
                binding.tvEstado.text = if (humano?.estado == 0) "vivo" else "muerto"
                binding.tvDestino.text = humano?.destino.toString()
                Glide.with(requireContext()).load(humano?.foto).into(binding.ivPerfil)
            }
        } catch (e: Exception) {
            Log.e("Manuel", "[FragPerfil] Error cargando datos: ${e.message}", e)
        }
    }
}
