package com.example.tolist_de_aprendizaje;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityRegMedArt extends AppCompatActivity {
    private final int OBJECTIVE_COUNT = 7;
    private List<Integer> percentages;
    private List<EditText> editTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_med_art);

        percentages = new ArrayList<>(OBJECTIVE_COUNT);
        editTexts = new ArrayList<>();

        LinearLayout linearLayout = findViewById(R.id.linearLayoutObjectives);

        // Crear y añadir dinámicamente 7 objetivos
        for (int i = 1; i <= OBJECTIVE_COUNT; i++) {
            // Crear TextView para el objetivo
            TextView textView = new TextView(this);
            textView.setText("Objetivo " + i);
            textView.setTextSize(18);

            // Crear EditText para el porcentaje
            EditText editText = new EditText(this);
            editText.setHint("Ingresa el porcentaje");
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            // Configurar LayoutParams para el TextView y EditText
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            textParams.setMargins(0, 0, 16, 0); // Márgenes opcionales
            textView.setLayoutParams(textParams);

            LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f); // Usar peso para ocupar el espacio disponible
            editText.setLayoutParams(editParams);

            // Crear un LinearLayout horizontal para el TextView y el EditText
            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.addView(textView);
            horizontalLayout.addView(editText);

            // Añadir el LinearLayout horizontal al contenedor principal
            linearLayout.addView(horizontalLayout);

            // Agregar el EditText a la lista
            editTexts.add(editText);
        }

        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percentages.clear(); // Limpiar la lista antes de llenar
                for (int i = 0; i < editTexts.size(); i++) {
                    String text = editTexts.get(i).getText().toString();

                    if (!text.isEmpty()) { // Solo procesar si no está vacío
                        Integer percentage;
                        try {
                            percentage = Integer.parseInt(text);
                        } catch (NumberFormatException e) {
                            Toast.makeText(ActivityRegMedArt.this,
                                    "Por favor ingresa un número válido para el objetivo " + (i + 1),
                                    Toast.LENGTH_SHORT).show();
                            return; // Detener la ejecución si hay un error
                        }

                        if (percentage >= 0 && percentage <= 100) {
                            percentages.add(percentage);
                        } else {
                            Toast.makeText(ActivityRegMedArt.this,
                                    "Por favor ingresa un porcentaje válido (0-100) para el objetivo " + (i + 1),
                                    Toast.LENGTH_SHORT).show();
                            return; // Detener la ejecución si hay un error
                        }
                    } else {
                        // Si el campo está vacío, no se agrega a la lista
                        percentages.add(null); // O simplemente no hacer nada
                    }
                }
                // Procesa los porcentajes aquí (por ejemplo, guardarlos en la base de datos)
                Toast.makeText(ActivityRegMedArt.this,
                        "Porcentajes registrados: ",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}