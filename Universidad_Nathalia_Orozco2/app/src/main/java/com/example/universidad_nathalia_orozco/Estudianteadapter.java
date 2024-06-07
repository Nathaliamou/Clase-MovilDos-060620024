package com.example.universidad_nathalia_orozco;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Estudianteadapter extends RecyclerView.Adapter <Estudianteadapter.estudianteViewHolder>{


    ArrayList<ClsEstudiantes> alestudiante;
    public Estudianteadapter(ArrayList<ClsEstudiantes> alestudiante) {
        this.alestudiante = alestudiante;
    }
    @NonNull
    @Override
    public Estudianteadapter.estudianteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //clase 06062024 hora 6:30 pm, reemplazo return null;
        //array list , luego constructor
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estudiantesresource,null,false);
                return new Estudianteadapter.estudianteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Estudianteadapter.estudianteViewHolder holder, int position) {
        holder.carnet.setText(alestudiante.get(position).getIdentificacion().toString());
        holder.nombre.setText(alestudiante.get(position).getNombre().toString());
        holder.carrera.setText(alestudiante.get(position).getCarrera().toString());
        holder.semestre.setText(alestudiante.get(position).getSemestre().toString());
        //preguntar que si lo que hay en el arrays list y el Si en mayuscula ojo.
        if (alestudiante.get(position).getActivo().toString().equals("Si"))
            holder.activo.setChecked(true);
        else
            holder.activo.setChecked(false);

    }

    @Override
    public int getItemCount() {
        return alestudiante.size();
    }

    public static class estudianteViewHolder extends RecyclerView.ViewHolder {

        TextView carnet, nombre, carrera, semestre;
        CheckBox activo;
        public estudianteViewHolder(@NonNull View itemView) {
            super(itemView);
            //vincular los objetos que acabe de definir con los objetos del CardView
            carnet=itemView.findViewById(R.id.tvcarnet);
            nombre=itemView.findViewById(R.id.tvnombre);
            carrera=itemView.findViewById(R.id.tvcarrera);
            semestre=itemView.findViewById(R.id.tvsemestre);
            activo=itemView.findViewById(R.id.cbactivo);
        }
    }
    //es una clase abstracta
}