package com.example.app_dioses.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_dioses.R
import com.example.app_dioses.modelo.Humano
import com.example.app_dioses.ventanas.Dios.FragProtegidosViewModel
import com.example.app_dioses.ventanas.LogIn.MainViewModel

class AdaptadorProtegido (var humanos: ArrayList<Humano>,
                          var contexto: Context,
                          var mainviewModel: MainViewModel,
                          var viewModel: FragProtegidosViewModel):RecyclerView.Adapter<AdaptadorProtegido.ViewHolder>(){


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = humanos.get(position)
        holder.bind(item,contexto,position,mainviewModel,viewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card_humano,parent,false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return humanos.size
    }



    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val nombreHumano = view.findViewById(R.id.tvNombreHumano) as TextView
        val correoHumano = view.findViewById(R.id.tvCorreoHumano) as TextView
        val destinoHumano = view.findViewById(R.id.tvDestinoHumano) as TextView
        val eliminarHumano = view.findViewById(R.id.btnEliminarHumano) as Button
        val fotoHumano = view.findViewById(R.id.ivFotoHumano) as ImageView
        fun bind(
            item: Humano,
            contexto: Context,
            position: Int,
            mainviewModel: MainViewModel,
            viewModel: FragProtegidosViewModel
        ) {
            nombreHumano.text = item.nombre
            correoHumano.text = item.correo
            destinoHumano.text = item.destino.toString()


        }

    }




}
