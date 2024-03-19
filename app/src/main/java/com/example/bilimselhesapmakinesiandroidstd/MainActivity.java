package com.example.bilimselhesapmakinesiandroidstd;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String title = "Bilimsel Hesap Makinesi", startMessage = "NESNEYE DAYALI PROGRAMLAMA DERSI PROJESI SUNAR !";
    TextView txtGirdi;
    boolean tema = true;
    private Button temaDegis;
    AlertDialog modlarDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dinleyiciler();

        mesajKutusu(MainActivity.this, title, startMessage);

        //-------------------------------------------------------------------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.mod_layout, null);
        Button btnGeri = dialogView.findViewById(R.id.btnGeri);
        Button btnParabol = dialogView.findViewById(R.id.btnParabol);

        btnParabol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder parabolDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                View parabolDialogView = getLayoutInflater().inflate(R.layout.parabol, null);
                parabolDialogBuilder.setView(parabolDialogView);
                AlertDialog parabolDialog = parabolDialogBuilder.create();
                parabolDialog.show();
            }
        });

        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modlarDialog.hide();
            }
        });
        temaDegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTheme();
            }
        });

        builder.setView(dialogView);
        modlarDialog = builder.create();
    }

    void dinleyiciler() {
        int[] buttonIds = {R.id.btnSifir, R.id.btnBir, R.id.btnIki, R.id.btnUc, R.id.btnDört, R.id.btnBes, R.id.btnAlti,
                R.id.btnYedi, R.id.btnSekiz, R.id.btnDokuz, R.id.btnDecimal, R.id.btnEksi, R.id.btnArti, R.id.btnCarp,
                R.id.btnBöl, R.id.btnMod};

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(buttonClickListener);
        }

        txtGirdi = findViewById(R.id.txtGirdi);

        temaDegis = findViewById(R.id.temaDegis);

        findViewById(R.id.btnTemizle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText();
            }
        });

        findViewById(R.id.btnEsittir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    calculateExpression();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        findViewById(R.id.btnMod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModlarDialog();
            }
        });
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            String buttonText = button.getText().toString();
            txtGirdi.append(buttonText);
        }
    };

    private void clearText() {
        String input = txtGirdi.getText().toString();
        if (!input.isEmpty()) {
            txtGirdi.setText(input.substring(0, input.length() - 1));
        }
    }

    private void calculateExpression() {
        String expression = txtGirdi.getText().toString().trim();
        if (!expression.isEmpty()) { // txtGirdi boş değilse işlemi hesapla
            double result = evaluateExpression(expression);
            txtGirdi.setText(String.valueOf(result));
        }
    }

    private double evaluateExpression(String expression) {
        // İfadeyi işleçlere göre ayırma
        String[] parts = expression.split("[+\\-*/%]");

        // İfade sadece bir sayı içeriyorsa veya işlem yoksa hata fırlat
        if (parts.length != 2) {
            throw new IllegalArgumentException("İfade geçersiz");
        }

        double operand1 = Double.parseDouble(parts[0]);
        double operand2 = Double.parseDouble(parts[1]);
        char operator = expression.charAt(parts[0].length());
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0)
                    throw new ArithmeticException("Sıfır");
                return operand1 / operand2;
            case '%':
                return operand1 % operand2;
            default:
                throw new IllegalArgumentException("Hata");
        }
    }

    public static void mesajKutusu(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void changeTheme() {

        int[] contentsAll = {R.id.btnSifir, R.id.btnBir, R.id.btnIki, R.id.btnUc, R.id.btnDört, R.id.btnBes, R.id.btnAlti,
                R.id.btnYedi, R.id.btnSekiz, R.id.btnDokuz, R.id.btnDecimal, R.id.btnEksi, R.id.btnArti, R.id.btnCarp,
                R.id.btnBöl, R.id.btnTemizle, R.id.btnEsittir};

        if (tema) {
            findViewById(R.id.toolBar).setBackgroundColor(Color.parseColor("#ffef00"));
            findViewById(R.id.baslik).setBackgroundColor(Color.parseColor("#002749"));
            findViewById(R.id.btnMod).setBackgroundColor(Color.parseColor("#ffef00"));
            ((Button) findViewById(R.id.btnMod)).setTextColor(Color.parseColor("#002749"));
            ((TextView) findViewById(R.id.baslik)).setText("FENERBAHÇE CAMİASINA ARMAĞAN OLSUN");
            ((TextView) findViewById(R.id.baslik)).setTextColor(Color.parseColor("#ffef00"));
            ((ImageView) findViewById(R.id.logo)).setImageResource(R.drawable.fblogo);


            for (int id : contentsAll) {
                Button button = findViewById(id);
                button.setBackgroundColor(Color.parseColor("#002749"));
                button.setTextColor(Color.parseColor("#ffef00"));
            }

        } else {
            findViewById(R.id.toolBar).setBackgroundColor(Color.parseColor("#B30C28"));
            findViewById(R.id.baslik).setBackgroundColor(Color.parseColor("#FFCC33"));
            findViewById(R.id.btnMod).setBackgroundColor(Color.parseColor("#FFCC33"));
            ((Button) findViewById(R.id.btnMod)).setTextColor(Color.parseColor("#B30C28"));
            ((TextView) findViewById(R.id.baslik)).setText("GALATASARAY CAMİASINA ARMAĞAN OLSUN");
            ((TextView) findViewById(R.id.baslik)).setTextColor(Color.parseColor("#B30C28"));
            ((ImageView) findViewById(R.id.logo)).setImageResource(R.drawable.gs);

            for (int id : contentsAll) {
                Button button = findViewById(id);
                button.setBackgroundColor(Color.parseColor("#B30C28"));
                button.setTextColor(Color.parseColor("#FFCC33"));
            }
        }
        tema = !tema;
    }

    private void showModlarDialog() {
        modlarDialog.show();
    }
}
