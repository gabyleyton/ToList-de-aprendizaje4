package com.example.tolist_de_aprendizaje;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ActivitySpinnerRegistrar extends AppCompatActivity {
    private Spinner spinnerNiveles1;
    private Spinner spinnerSubniveles2;
    private Spinner spinnerProyecto3;
    private Button buttonSiguiente4;

    private HashMap<String, String[]> subnivelesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_registrar); // Asegúrate de que el layout esté correctamente referenciado

        spinnerNiveles1 = findViewById(R.id.spinnerNiveles1);
        spinnerSubniveles2 = findViewById(R.id.spinnerSubniveles2);
        spinnerProyecto3 = findViewById(R.id.spinnerProyecto3);
        buttonSiguiente4 = findViewById(R.id.buttonSiguiente4);

        // Definición de los niveles
        String[] niveles = {"Desarrollo personal y social", "Comunicación integral"};

        // Mapa de subniveles
        subnivelesMap = new HashMap<>();
        subnivelesMap.put("Desarrollo personal y social", new String[]{
                "Identidad y autonomía",
                "Convivencia y ciudadanía",
                "Corporalidad y movimiento"
        });
        subnivelesMap.put("Comunicación integral", new String[]{
                "Lenguaje verbal",
                "Lenguajes artísticos"
        });

        // Adaptador para el primer Spinner
        ArrayAdapter<String> nivelesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, niveles);
        nivelesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNiveles1.setAdapter(nivelesAdapter);

        // Adaptador para el Spinner de proyecto
        String[] proyectos = {"Sala cuna", "Niveles medios", "Transición"};
        ArrayAdapter<String> proyectosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, proyectos);
        proyectosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProyecto3.setAdapter(proyectosAdapter);

        // Listener para cambios en el primer Spinner
        spinnerNiveles1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedNivel = (String) parent.getItemAtPosition(position);
                updateSubniveles(selectedNivel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        // Configurar el botón para ir a la actividad correspondiente
        buttonSiguiente4.setOnClickListener(v -> {
            String selectedProyecto = (String) spinnerProyecto3.getSelectedItem();
            String selectedSubnivel = (String) spinnerSubniveles2.getSelectedItem();
            String selectedNivel = (String) spinnerNiveles1.getSelectedItem();

            Intent intent = null;

            // Lógica para dirigir a la actividad correcta
            if (selectedNivel.equals("Desarrollo personal y social")) {
                if (selectedSubnivel.equals("Identidad y autonomía")) {
                    if (selectedProyecto.equals("Sala cuna")) {
                        intent = new Intent(ActivitySpinnerRegistrar.this, ActivitySCiden.class);
                    } else if (selectedProyecto.equals("Niveles medios")) {
                        intent = new Intent(ActivitySpinnerRegistrar.this, ActivityMediosIden.class);
                    } else if (selectedProyecto.equals("Transición")) {
                        intent = new Intent(ActivitySpinnerRegistrar.this, ActivityTransIden.class);
                    }
                } else if (selectedSubnivel.equals("Convivencia y ciudadanía")) {
                    if (selectedProyecto.equals("Sala cuna")) {
                        intent = new Intent(ActivitySpinnerRegistrar.this, SCconv.class);
                    } else if (selectedProyecto.equals("Niveles medios")) {
                        intent = new Intent(ActivitySpinnerRegistrar.this, ActivityMediosConv.class);
                    } else if (selectedProyecto.equals("Transición")) {
                        intent = new Intent(ActivitySpinnerRegistrar.this, ActivityTransConv.class);
                    }
                }
                // Aquí puedes agregar más condiciones si es necesario
            } else if (selectedNivel.equals("Comunicación integral")) {
                if (selectedSubnivel.equals("Lenguaje verbal")) {
                    if (selectedProyecto.equals("Sala cuna")) {
                        intent = new Intent(ActivitySpinnerRegistrar.this, ActivityScVerb.class);
                    } else if (selectedProyecto.equals("Niveles medios")) {
                        intent = new Intent(ActivitySpinnerRegistrar.this, ActivityMediosVerb.class);
                    } else if (selectedProyecto.equals("Transición")) {
                        intent = new Intent(ActivitySpinnerRegistrar.this, ActivityTransVerb.class);
                    }
                } else if (selectedSubnivel.equals("Lenguajes artísticos")) {
                    if (selectedProyecto.equals("Sala cuna")) {
                        intent = new Intent(ActivitySpinnerRegistrar.this, ActivityScArt.class);
                    } else if (selectedProyecto.equals("Niveles medios")) {
                        intent = new Intent(ActivitySpinnerRegistrar.this, ActivityRegMedArt.class);
                    } else if (selectedProyecto.equals("Transición")) {
                        intent = new Intent(ActivitySpinnerRegistrar.this, ActivityTransArt.class);
                    }
                }
            }

            if (intent != null) {
                startActivity(intent);
            } else {
                // Manejo de error si no se encontró la actividad
                // Puedes mostrar un Toast o un mensaje al usuario
            }
        });
    }

    private void updateSubniveles(String nivel) {
        String[] subniveles = subnivelesMap.get(nivel);
        ArrayAdapter<String> subnivelesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subniveles);
        subnivelesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubniveles2.setAdapter(subnivelesAdapter);
    }
}