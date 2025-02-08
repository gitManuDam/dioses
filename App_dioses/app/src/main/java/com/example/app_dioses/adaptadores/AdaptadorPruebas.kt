package com.example.app_dioses.adaptadores

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.app_dioses.R
import com.example.app_dioses.modelo.Prueba
import com.example.app_dioses.ventanas.Dios.FragPruebasViewModel
import com.example.app_dioses.ventanas.LogIn.MainViewModel

class AdaptadorPruebas(var pruebas: ArrayList<Prueba>,var viewModel: FragPruebasViewModel,
                       var mainViewModel: MainViewModel,
                        var context: Context
):
    RecyclerView.Adapter<AdaptadorPruebas.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pruebas.get(position)
        holder.bind(item,position,mainViewModel,viewModel,context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_prueba,parent,false)
        return ViewHolder(vista)

    }

    override fun getItemCount(): Int {
        return pruebas.size
    }






    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tituloPrueba = view.findViewById(R.id.tvTitulo) as TextView
        val destinoPrueba = view.findViewById(R.id.tvDestino) as TextView
        val tipoPrueba = view.findViewById(R.id.tvTipo) as TextView
        val btnAsignar = view.findViewById(R.id.btnAsignarPrueba) as Button
        val btnEliminar = view.findViewById(R.id.btnEliminarPrueba) as Button

        fun bind(item:Prueba,position: Int,mainViewModel: MainViewModel,viewModel: FragPruebasViewModel,context: Context){
            tituloPrueba.text = item.titulo
            destinoPrueba.text = item.destino.toString()
            tipoPrueba.text = item.tipo

            btnAsignar.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.nav_fragAsignarPrueba, bundleOf(Pair("idPrueba", item.id)))
            }

            btnEliminar.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.eliminarPrueba))
                    .setMessage(context.getString(R.string.PreguntaEliminarPrueba)+" ${item.titulo}?")
                    .setPositiveButton(context.getString(R.string.si), DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                        viewModel.eliminarPrueba(item.id,mainViewModel.diosLogeado.value!!.id)


                    }))
                    .setNegativeButton(context.getString(R.string.no), ({ dialog: DialogInterface, which: Int ->
                        Toast.makeText(context,context.getString(R.string.cancelarEliminacion), Toast.LENGTH_SHORT).show()
                    }))
                    .show()


            }


        }
    }

}