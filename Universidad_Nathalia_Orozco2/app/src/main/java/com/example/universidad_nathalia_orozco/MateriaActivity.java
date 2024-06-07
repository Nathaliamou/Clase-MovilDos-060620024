package com.example.universidad_nathalia_orozco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.HashMap;
import java.util.Map;

public class MateriaActivity extends AppCompatActivity {

    EditText etcodigo, etmateria, ethorario, etcredito, etprecio;
    Button btbuscar, btadicionar, btmodificar, btcancelar, bteliminar; // btanular
    CheckBox cbactivo;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //definir variables
    String codigo, materia, horario, credito, precio, id_codigo, colleccion = "Materia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);
        //asociar obj java con xml
        btadicionar = findViewById(R.id.btadicionar);
        //btanular = findViewById(R.id.btanular);
        btbuscar = findViewById(R.id.btbuscar);
        btcancelar = findViewById(R.id.btcancelar);
        bteliminar = findViewById(R.id.bteliminar);
        btmodificar = findViewById(R.id.btmodificar);
        cbactivo = findViewById(R.id.cbactivo);
        etcodigo = findViewById(R.id.etcodigo);
        etmateria = findViewById(R.id.etmateria);
        ethorario = findViewById(R.id.ethorario);
        etcredito = findViewById(R.id.etcredito);
        etprecio = findViewById(R.id.etprecio);
        etcodigo.requestFocus();
    }

    public void Adicionar(View view) {

        codigo = etcodigo.getText().toString();
        materia = etmateria.getText().toString();
        horario = ethorario.getText().toString();
        credito = etcredito.getText().toString();
        precio = etprecio.getText().toString();
        if (!codigo.isEmpty() && !materia.isEmpty() && !horario.isEmpty() && !credito.isEmpty() && !precio.isEmpty()) {
            // Create a new materia with a name
            Map<String, Object> alumno = new HashMap<>();
            alumno.put("Codigo", codigo);
            alumno.put("Materia", materia);
            alumno.put("Horario", horario);
            alumno.put("Credito", credito);
            alumno.put("Precio", precio);
            alumno.put("Activo", "Si");

            // Add a new codigo with a generated ID indico alumno de arriba.
            db.collection(colleccion)
                    .add(alumno)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Limpiar_campos();
                            Toast.makeText(MateriaActivity.this, "Registro guardado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Log.w(TAG, "Error adding document", e); Solo va aparecer cuando no lo encuentra.
                            Toast.makeText(MateriaActivity.this, "Error buscando registro", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            etcodigo.requestFocus();
        }
    }//Fin metodo Adicionar

    public void Buscar(View view) {
        codigo=etcodigo.getText().toString();
        if(!codigo.isEmpty()) {
            db.collection(colleccion)
                    .whereEqualTo("Codigo", codigo)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().size() > 0) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //Log.d(TAG, document.getId() + " => " + document.getData());
                                        id_codigo = document.getId();
                                        etcredito.setText(document.getString("Credito"));
                                        ethorario.setText(document.getString("Horario"));
                                        etmateria.setText(document.getString("Materia"));
                                        etprecio.setText(document.getString("Precio"));
                                        if (document.getString("Activo").equals("Si"))
                                            cbactivo.setChecked(true);
                                        else
                                            cbactivo.setChecked(false);
                                        //btanular.setEnabled(true);
                                        bteliminar.setEnabled(true);
                                        btmodificar.setEnabled(true);
                                    }
                                }else {
                                    btadicionar.setEnabled(true);
                                    Toast.makeText(MateriaActivity.this, "Codigo no encontrado", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                // Log.w(TAG, "Error getting documents.", task.getExcept
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Los datos son requeridos", Toast.LENGTH_SHORT).show();
            etcodigo.requestFocus();
        }
    }//Fin metodo Buscar

    public void Modificar(View view) {
        codigo = etcodigo.getText().toString();
        materia = etmateria.getText().toString();
        horario= ethorario.getText().toString();
        credito= etcredito.getText().toString();
        precio= etprecio.getText().toString();
        if (!codigo.isEmpty() && !materia.isEmpty() && !horario.isEmpty() && !credito.isEmpty() && !precio.isEmpty()) {

            // Modificar a new student with a name
            Map<String, Object> alumno = new HashMap<>();
            alumno.put("Codigo", codigo);
            alumno.put("Materia", materia);
            alumno.put("Horario", horario);
            alumno.put("Credito", credito);
            alumno.put("Precio", precio);
            if (cbactivo.isChecked())
                alumno.put("Activo", "si"); //si esta chuleado me pone si (nathamou069)
            else
                alumno.put("Activo", "si");//si no esta chuleado me pone no (nathamou069)
            //toca decir cual es el ID del documento
            db.collection("Materias").document(id_codigo)
                    .set(alumno)
                    //escucha respuesta
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MateriaActivity.this,"Codigo Actualizado...",Toast.LENGTH_SHORT).show();
                            Limpiar_campos();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MateriaActivity.this,"Error actualizando Codigo...",Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(this, "Todos datos son requeridos", Toast.LENGTH_SHORT).show();
            etcodigo.requestFocus();
        }
    }//Fin metodo modificar

    public void Eliminar(View view) {

        db.collection("Materias").document(id_codigo)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Limpiar_campos();
                        Toast.makeText(MateriaActivity.this,"Codigo eliminado...",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MateriaActivity.this,"Error eliminando Codigo...",Toast.LENGTH_SHORT).show();
                    }
                });
    }//fin metodo Eliminar

    public void Limpiar(View view) {Limpiar_campos(); }//fin metodo limpiar

    private void Limpiar_campos(){
        etcodigo.setText("");
        etmateria.setText("");
        ethorario.setText("");
        etcredito.setText("");
        etprecio.setText("");
        cbactivo.setChecked(false);
        //btanular.setEnabled(false);
        bteliminar.setEnabled(false);
        btmodificar.setEnabled(false);
        btadicionar.setEnabled(false);
        etcodigo.requestFocus();
    }//fin método limpiar_campos //no le puse el view no lo quiero pegar en xml
}