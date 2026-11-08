
package com.example.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // ربط عناصر الواجهة
        LinearLayout layout = findViewById(R.id.layout_result_rf);
        TextView txtResultat = findViewById(R.id.txt_resultat_rf);
        Button btnNouveau = findViewById(R.id.btn_nouveau_rf);

        // استقبال النتيجة واسم المسافر
        boolean verdict = getIntent().getBooleanExtra(
                MainActivity.creerCle("1234", "VERDICT_FINAL"),
                false
        );

        String nom = getIntent().getStringExtra(
                MainActivity.creerCle("1234", "NOM_VOYAGEUR")
        );

        // تحديد النتيجة وتغيير لون الخلفية
        if (verdict) {

            layout.setBackgroundColor(Color.GREEN);

            txtResultat.setText("Bon voyage " + nom + " !");

        } else {

            layout.setBackgroundColor(Color.RED);

            txtResultat.setText(
                    nom + " est en état d'arrestation temporelle !"
            );
        }

        // العودة إلى المسافر الجديد
        btnNouveau.setOnClickListener(v -> {

            Intent intent = new Intent(
                    ResultActivity.this,
                    MainActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(intent);

            finish();
        });
    }
}
