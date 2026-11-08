package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // تعريف المفاتيح باستعمال رقم التسجيل
    public static String creerCle(String matricule, String nomCle) {
        return matricule + "_" + nomCle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ربط عناصر الواجهة
        EditText edtNom = findViewById(R.id.edt_nom_rf);
        EditText edtNaissance = findViewById(R.id.edt_naissance_rf);
        EditText edtDestination = findViewById(R.id.edt_destination_rf);
        Spinner spinnerMotif = findViewById(R.id.spinner_motif_rf);
        Button btnInspecter = findViewById(R.id.btn_inspecter_rf);

        // إضافة أسباب السفر إلى القائمة
        String[] motifs = {
                "Tourisme",
                "Modification du passé",
                "Fuite"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                motifs
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerMotif.setAdapter(adapter);

        // عند الضغط على زر فحص الملف
        btnInspecter.setOnClickListener(v -> {

            String nom = edtNom.getText().toString().trim();
            String naissance = edtNaissance.getText().toString().trim();
            String destination = edtDestination.getText().toString().trim();
            String motif = spinnerMotif.getSelectedItem().toString();

            // التحقق من إدخال المعلومات
            if (nom.isEmpty() || naissance.isEmpty() || destination.isEmpty()) {
                Toast.makeText(this,
                        "Veuillez remplir tous les champs",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            int anneeNaissance;
            int anneeDestination;

            try {
                anneeNaissance = Integer.parseInt(naissance);
                anneeDestination = Integer.parseInt(destination);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Année invalidee", Toast.LENGTH_SHORT).show();
                return;
            }

            if (anneeNaissance > anneeDestination) {
                Toast.makeText(this,
                        "Année de destination invalide",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // إنشاء Intent صريح للانتقال إلى النشاط الثاني
            Intent intent = new Intent(MainActivity.this, VerificationActivity.class);

            // إرسال المعلومات باستعمال المفاتيح الديناميكية
            intent.putExtra(creerCle("1234", "NOM_VOYAGEUR"), nom);
            intent.putExtra(creerCle("1234", "ANNEE_NAISSANCE"), anneeNaissance);
            intent.putExtra(creerCle("1234", "ANNEE_DESTINATION"), anneeDestination);
            intent.putExtra(creerCle("1234", "MOTIF_VOYAGE"), motif);

            startActivity(intent);
        });
    }
}
