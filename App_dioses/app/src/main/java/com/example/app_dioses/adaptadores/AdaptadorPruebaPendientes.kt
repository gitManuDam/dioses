package com.example.app_dioses.adaptadores

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
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
import com.example.app_dioses.adaptadores.AdaptadorPruebas.ViewHolder
import com.example.app_dioses.modelo.Prueba
import com.example.app_dioses.modelo.PruebaHumanoRV
import com.example.app_dioses.parametros.Parametros
import com.example.app_dioses.ventanas.Dios.FragPruebasViewModel
import com.example.app_dioses.ventanas.Humano.FragPruebasPendientesViewModel
import com.example.app_dioses.ventanas.LogIn.MainViewModel

class AdaptadorPruebaPendientes(var pruebasPendientes: ArrayList<PruebaHumanoRV>, var viewModel: FragPruebasPendientesViewModel,
                                var mainViewModel: MainViewModel,
                                var context: Context
):
RecyclerView.Adapter<AdaptadorPruebaPendientes.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pruebasPendientes.get(position)
        holder.bind(item,position,mainViewModel,viewModel,context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_prueba_ampliada,parent,false)
        return ViewHolder(vista)

    }



    override fun getItemCount(): Int {
        return pruebasPendientes.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tituloPrueba = view.findViewById(R.id.tvTituloPA) as TextView
        val destinoPrueba = view.findViewById(R.id.tvDestinoPA) as TextView
        val tipoPrueba = view.findViewById(R.id.tvTipoPA) as TextView
        val btnRealizar = view.findViewById(R.id.btnRealizarPA) as Button
        val destinoFinalPrueba = view.findViewById(R.id.tvDestinoFinalPA) as TextView
        val estado = view.findViewById(R.id.tvEstadoPA) as TextView

        fun bind(item: PruebaHumanoRV, position: Int, mainViewModel: MainViewModel, viewModel: FragPruebasPendientesViewModel, context: Context){
            tituloPrueba.text = item.titulo
            destinoPrueba.text = item.destino.toString()
            tipoPrueba.text = item.tipo
            destinoFinalPrueba.text = item.destinoFin.toString()
            estado.text = when (item.estado) {
                0 -> "Pendiente"
                1 -> "Ganada"
                2 -> "Finalizada"
                else -> "Pendiente"

            }

            btnRealizar.setOnClickListener {
                when(item.tipo){
                    Parametros.pp->{
                        val bundle = bundleOf("Prueba" to item)
                        Navigation.findNavController(itemView).navigate(R.id.nav_fragPuntual,bundle)

                    }
                    Parametros.resL->{
                        val bundle = bundleOf("Prueba" to item)
                        Navigation.findNavController(itemView).navigate(R.id.nav_fragRespuestaLibre,bundle)
                    }
                    Parametros.eleccion->{
                        val bundle = bundleOf("Prueba" to item)
                        Navigation.findNavController(itemView).navigate(R.id.nav_fragEleccion,bundle)
                    }
                    Parametros.afinidad->{
                        val bundle = bundleOf("Prueba" to item)
                        Navigation.findNavController(itemView).navigate(R.id.nav_fragValoracion,bundle)
                    }
                }
            }

            itemView.setOnClickListener {
                viewModel.obtenerPruebaDetalle(item)
                Log.d("Manuel", "Adaptador prueba pendientes.bind: $item")
            }




        }
    }



}
