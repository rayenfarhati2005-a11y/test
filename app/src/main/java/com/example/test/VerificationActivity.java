
package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    // التحقق إذا كان العدد أوليا
    public static boolean estPremier(int nombre) {

        if (nombre < 2) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(nombre); i++) {
            if (nombre % i == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        // ربط عناصر الواجهة
        TextView txtNom = findViewById(R.id.txt_nom_verif_rf);
        TextView txtAge = findViewById(R.id.txt_age_verif_rf);
        TextView txtMotif = findViewById(R.id.txt_motif_verif_rf);

        Button btnApprouver = findViewById(R.id.btn_approuver_rf);
        Button btnRefuser = findViewById(R.id.btn_refuser_rf);

        // استقبال المعلومات من النشاط الأول
        String nom = getIntent().getStringExtra(
                MainActivity.creerCle("1234", "NOM_VOYAGEUR"));

        int anneeNaissance = getIntent().getIntExtra(
                MainActivity.creerCle("1234", "ANNEE_NAISSANCE"), 0);

        int anneeDestination = getIntent().getIntExtra(
                MainActivity.creerCle("1234", "ANNEE_DESTINATION"), 0);

        String motif = getIntent().getStringExtra(
                MainActivity.creerCle("1234", "MOTIF_VOYAGE"));

        // حساب العمر الزمني
        int ageTemporel = anneeDestination - anneeNaissance;

        // عرض معلومات المسافر
        txtNom.setText("Nom : " + nom);
        txtAge.setText("Age temporel : " + ageTemporel + " ans");
        txtMotif.setText("Motif : " + motif);

        // التحقق من شروط المفارقة الزمنية
        if ("Modification du passé".equals(motif)
                && estPremier(ageTemporel)) {

            btnApprouver.setEnabled(false);

            Toast.makeText(this,
                    "Risque de paradoxe temporel !",
                    Toast.LENGTH_LONG).show();
        }

        // الموافقة على الرحلة
        btnApprouver.setOnClickListener(v -> {
            envoyerVerdict(true, nom);
        });

        // رفض الرحلة
        btnRefuser.setOnClickListener(v -> {
            envoyerVerdict(false, nom);
        });
    }

    // إرسال النتيجة إلى النشاط الثالث
    private void envoyerVerdict(boolean verdict, String nom) {

        Intent intent = new Intent(
                VerificationActivity.this,
                ResultActivity.class
        );

        intent.putExtra(
                MainActivity.creerCle("1234", "VERDICT_FINAL"),
                verdict
        );

        intent.putExtra(
                MainActivity.creerCle("1234", "NOM_VOYAGEUR"),
                nom
        );

        startActivity(intent);
    }
}
