package com.example.bilimselhesapmakinesiandroidstd;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String title ="DALTONLAR", startMessage = "NESNEYE DAYALI PROGRAMLAMA DERSI PROJESI SUNAR !";
    TextView txtGirdi,txtSonuc;
    Button sifir, bir, iki, uc, dort, bes, alti, yedi, sekiz, dokuz, temizle, mod, nokta, eksi, arti, bölü, carpim, esittir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtGirdi = findViewById(R.id.txtGirdi);
        dinleyiciler();
        showMessage(MainActivity.this, title, startMessage);
    }
    void dinleyiciler() {
            int[] buttonIds = {R.id.btnSifir, R.id.btnBir, R.id.btnIki, R.id.btnUc, R.id.btnDört, R.id.btnBes, R.id.btnAlti,
                    R.id.btnYedi, R.id.btnSekiz, R.id.btnDokuz, R.id.btnDecimal, R.id.btnEksi, R.id.btnArti, R.id.btnCarp,
                    R.id.btnBöl, R.id.btnMod};
            for (int id : buttonIds) {
                findViewById(id).setOnClickListener(buttonClickListener);
            }
            findViewById(R.id.btnTemizle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input = txtGirdi.getText().toString();
                    if (!input.isEmpty()) {
                        txtGirdi.setText(input.substring(0, input.length() - 1));
                    }
                }
            });
            findViewById(R.id.btnEsittir).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String expression = txtGirdi.getText().toString();
                    double result = evaluateExpression(expression);
                    txtGirdi.setText(String.valueOf(result));
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
    private double evaluateExpression(String expression) {
        String[] parts = expression.split("[+\\-*/%]");
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
    public static void showMessage(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton("Tamam", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();}
        }).show();
    }
}
