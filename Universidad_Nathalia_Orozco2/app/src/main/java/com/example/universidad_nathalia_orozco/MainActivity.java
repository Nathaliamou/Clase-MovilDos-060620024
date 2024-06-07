package com.example.universidad_nathalia_orozco;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    EditText etcarnet, etnombre, etcarrera, etsemestre;
    Button btbuscar, btadicionar, btmodificar, btcancelar, bteliminar;
    CheckBox cbactivo;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //definir variables
    String carnet, nombre, carrera, semestre, id_documento, colleccion = "estudiantes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //asociar obj java con xml
        btadicionar = findViewById(R.id.btadicionar);
     //   btanular = findViewById(R.id.btanular);
        btbuscar = findViewById(R.id.btbuscar);
        btcancelar = findViewById(R.id.btcancelar);
        bteliminar = findViewById(R.id.bteliminar);
        btmodificar = findViewById(R.id.btmodificar);
        cbactivo = findViewById(R.id.cbactivo);
        etcarnet = findViewById(R.id.etcarnet);
        etcarrera = findViewById(R.id.etcarrera);
        etnombre = findViewById(R.id.etnombre);
        etsemestre = findViewById(R.id.etsemestre);
        etcarnet.requestFocus();
    } //fin metodo onCreate

    public void Adicionar(View view) {

        carnet = etcarnet.getText().toString();
        nombre = etnombre.getText().toString();
        carrera = etcarrera.getText().toString();
        semestre = etsemestre.getText().toString();
        if (!carnet.isEmpty() && !nombre.isEmpty() && !carrera.isEmpty() && !semestre.isEmpty()) {
// Create a new student with a name
            Map<String, Object> alumno = new HashMap<>();
            alumno.put("Carnet", carnet);
            alumno.put("Nombre", nombre);
            alumno.put("Carrera", carrera);
            alumno.put("Semestre", semestre);
            alumno.put("Activo", "Si");

// Add a new document with a generated ID indico alumno de arriba.
            db.collection(colleccion)
                    .add(alumno)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Limpiar_campos();
                            Toast.makeText(MainActivity.this, "Registro guardado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Log.w(TAG, "Error adding document", e); Solo va aparecer cuando no lo encuentra.
                            Toast.makeText(MainActivity.this, "Error buscando registro", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            etcarnet.requestFocus();
        }
    }//Fin metodo Adicionar


    public void Buscar(View view) {
        carnet=etcarnet.getText().toString();
        if(!carnet.isEmpty()) {
            db.collection(colleccion)
                    .whereEqualTo("Carnet", carnet)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().size() > 0) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //Log.d(TAG, document.getId() + " => " + document.getData());
                                        id_documento = document.getId();
                                        etnombre.setText(document.getString("Nombre"));
                                        etcarrera.setText(document.getString("Carrera"));
                                        etsemestre.setText(document.getString("Semestre"));
                                        if (document.getString("Activo").equals("Si"))
                                            cbactivo.setChecked(true);
                                        else
                                            cbactivo.setChecked(false);
                                       // btanular.setEnabled(true);

                                        bteliminar.setEnabled(true);
                                        btmodificar.setEnabled(true);
                                    }
                                }else {
                                    btadicionar.setEnabled(true);
                                    Toast.makeText(MainActivity.this, "Documento no encontrado", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                // Log.w(TAG, "Error getting documents.", task.getExcept
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Los datos son requeridos", Toast.LENGTH_SHORT).show();
            etcarnet.requestFocus();
        }
    }//Fin metodo Consultar

    public void Modificar(View view) {
        carnet = etcarnet.getText().toString();
        nombre = etnombre.getText().toString();
        carrera = etcarrera.getText().toString();
        semestre= etsemestre.getText().toString();
        if (!carnet.isEmpty() && !nombre.isEmpty() && !carrera.isEmpty() && !semestre.isEmpty()) {

            // Modificar a new student with a name
            Map<String, Object> alumno = new HashMap<>();
            alumno.put("Carnet", carnet);
            alumno.put("Nombre", nombre);
            alumno.put("Carrera", carrera);
            alumno.put("Semestre", semestre);
            if (cbactivo.isChecked())
                alumno.put("Activo", "si"); //si esta chuleado me pone si (nathamou069)
            else
                alumno.put("Activo", "si");//si no esta chuleado me pone no (nathamou069)
                    //toca decir cual es el ID del documento
            db.collection(colleccion).document(id_documento)
                    .set(alumno)
                    //escucha respuesta
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this,"Documento Actualizado...",Toast.LENGTH_SHORT).show();
                            Limpiar_campos();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"Error actualizando documento...",Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(this, "Todos datos son requeridos", Toast.LENGTH_SHORT).show();
            etcarnet.requestFocus();
        }
    }//Fin metodo modificar

    public void Eliminar(View view) {

            db.collection(colleccion).document(id_documento)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Limpiar_campos();
                            Toast.makeText(MainActivity.this,"Documento eliminado...",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"Error eliminando documento...",Toast.LENGTH_SHORT).show();
                        }
                    });
    }//fin metodo Eliminar

/*elanular no lo elimino, es por que borra total de los datos de la DB de firebase
    public void Anular(View view) {
        // Modificar a new student with a name
        Map<String, Object> alumno = new HashMap<>();
        alumno.put("Activo", "si");

        db.collection("estudiantes").document(id_documento)
                .set(alumno)
                //escucha respuesta
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Documento Anulado...", Toast.LENGTH_SHORT).show();
                        Limpiar_campos();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error anulado documento...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

 */
            public void Limpiar(View view) {Limpiar_campos(); }//fin metodo limpiar

    public void Listar(View view){
        Intent intListar=new Intent(this, ListarActivity.class);
        startActivity(intListar);
    } //fin metodo listar
    private void Limpiar_campos(){
        etcarnet.setText("");
        etnombre.setText("");
        etcarrera.setText("");
        etsemestre.setText("");
        cbactivo.setChecked(false);
      //  btanular.setEnabled(false);
        bteliminar.setEnabled(false);
        btmodificar.setEnabled(false);
        btadicionar.setEnabled(false);
        etcarnet.requestFocus();
    }//fin método limpiar_campos //no le puse el view no lo quiero pegar en xml


}