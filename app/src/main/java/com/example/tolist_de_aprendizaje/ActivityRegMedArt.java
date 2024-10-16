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
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
            TextView textView = new TextView(this);
            textView.setText("Objetivo " + i);
            textView.setTextSize(18);

            EditText editText = new EditText(this);
            editText.setHint("Ingresa el porcentaje");
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            textParams.setMargins(0, 0, 16, 0);
            textView.setLayoutParams(textParams);

            LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f);
            editText.setLayoutParams(editParams);

            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.addView(textView);
            horizontalLayout.addView(editText);

            linearLayout.addView(horizontalLayout);
            editTexts.add(editText);
        }

        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percentages.clear();
                for (int i = 0; i < editTexts.size(); i++) {
                    String text = editTexts.get(i).getText().toString();
                    Integer percentage;

                    if (!text.isEmpty()) {
                        try {
                            percentage = Integer.parseInt(text);
                        } catch (NumberFormatException e) {
                            Toast.makeText(ActivityRegMedArt.this,
                                    "Por favor ingresa un número válido para el objetivo " + (i + 1),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (percentage >= 0 && percentage <= 100) {
                            // Obtener el id del usuario, objetivo y nivel
                            int idUsuario = SessionManager.getUserId();
                            //int idObjetivo = /* Obtener el ID del objetivo correspondiente */;
                            //int idNivel = /* Obtener el ID del nivel seleccionado */;

                            // Llamar al método para guardar el porcentaje
                            //savePorcentaje(id, idObjetivo, idNivel, percentage);
                        } else {
                            Toast.makeText(ActivityRegMedArt.this,
                                    "Porcentaje debe estar entre 0 y 100 para el objetivo " + (i + 1),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        percentages.add(null); // O simplemente no hacer nada
                    }
                }
            }
        });
    }

    private void savePorcentaje(int idUsuario, int idObjetivo, int idNivel, int porcentaje) {
        new Thread(() -> {
            try {
                String urlString = "http://tu_dominio_o_ip/save_percentage.php"; // Cambia por la URL correcta
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // Crear el cuerpo de la solicitud
                String postData = "id_usuario=" + idUsuario + "&id_objetivo=" + idObjetivo + "&id_nivel=" + idNivel + "&porcentaje=" + porcentaje;

                // Enviar datos
                OutputStream os = conn.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();

                // Leer la respuesta
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Procesar la respuesta
                JSONObject jsonResponse = new JSONObject(response.toString());
                String status = jsonResponse.getString("status");

                runOnUiThread(() -> {
                    if (status.equals("success")) {
                        Toast.makeText(ActivityRegMedArt.this, "Porcentaje guardado exitosamente.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ActivityRegMedArt.this, "Error: " + jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(ActivityRegMedArt.this, "Error al guardar el porcentaje.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
