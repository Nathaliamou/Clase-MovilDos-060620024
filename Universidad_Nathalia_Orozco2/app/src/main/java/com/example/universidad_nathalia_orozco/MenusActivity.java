package com.example.universidad_nathalia_orozco;

//librerias

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MenusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
    } //fin del metodo on Create

    public void universidad(View view){
        Intent intuniversidad=new Intent( this, MainActivity.class);
        startActivity(intuniversidad);
    }//fin metodo boton  universidad

    public void materias(View view){
        Intent intmaterias=new Intent( this, MateriaActivity.class);
        startActivity(intmaterias);
    }//fin metodo boton  materias
}