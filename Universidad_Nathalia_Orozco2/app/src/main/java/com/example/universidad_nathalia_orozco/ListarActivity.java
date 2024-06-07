package com.example.universidad_nathalia_orozco;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class ListarActivity extends AppCompatActivity {
    RecyclerView rvestudiantes;
    ArrayList<ClsEstudiantes> listaestudiantes;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String coleccion = "estudiantes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar2);

        // Asociar los objetos XML a los objetos Java.
        rvestudiantes = findViewById(R.id.rvestudiantes);

        // Inicializar el ArrayList
        listaestudiantes = new ArrayList<>();

        // Configurar el RecyclerView
        rvestudiantes.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        rvestudiantes.setHasFixedSize(true);

        // Cargar estudiantes desde Firestore
        Cargar_Estudiantes();
    }

    private void Cargar_Estudiantes() {
        db.collection(coleccion)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ClsEstudiantes objestudiante = new ClsEstudiantes();
                                objestudiante.setIdentificacion(document.getString("Carnet"));
                                objestudiante.setNombre(document.getString("Nombre"));
                                objestudiante.setCarrera(document.getString("Carrera"));
                                objestudiante.setSemestre(document.getString("Semestre"));
                                objestudiante.setActivo(document.getString("Activo"));
                                listaestudiantes.add(objestudiante);
                            }

                            // Adaptar los datos
                            Estudianteadapter estudianteAdapter = new Estudianteadapter(listaestudiantes);
                            rvestudiantes.setAdapter(estudianteAdapter);
                        } else {
                            Toast.makeText(ListarActivity.this, "Error en la búsqueda", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Regresar(View view) {
        Intent intmain = new Intent(this, MainActivity.class);
        startActivity(intmain);
    }//fin metodo regresar
}
