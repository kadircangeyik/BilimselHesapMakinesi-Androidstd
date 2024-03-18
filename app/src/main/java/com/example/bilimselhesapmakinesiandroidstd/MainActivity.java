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

    // Başlık ve başlangıç mesajı için ifadeler
    String title = "Bilimsel Hesap Makinesi", startMessage = "NESNEYE DAYALI PROGRAMLAMA DERSI PROJESI SUNAR !";

    // Girilen ve sonuç metin alanları için TextView değişkenleri
    TextView txtGirdi;

    boolean tema = true;
    private Button temaDegis;

    // AlertDialog değişkeni oluşturuyoruz
    AlertDialog modlarDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML dosyasındaki TextView'i id'si ile eşleştiriyoruz
        txtGirdi = findViewById(R.id.txtGirdi);

        // Buton dinleyicilerini okuyoruz
        dinleyiciler();

        // Başlangıç mesajını gösterdik
        mesajKutusu(MainActivity.this, title, startMessage);

        // AlertDialogumuzu (Parabol,Integral gibi...) modlar için oluşturuyoruz
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.mod_layout, null);

        // Önemli !!! AlertDialog içindeki btnGeri butonunu bulma
        Button btnGeri = dialogView.findViewById(R.id.btnGeri);

        //Modlar Dialogu kapatmak için geri butonu
        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modlarDialog.hide(); // AlertDialog'u kapat yada gizle
            }
        });
        temaDegis = findViewById(R.id.temaDegis);
        temaDegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tema) {

                    // Fenerbahçe teması
                    findViewById(R.id.toolBar).setBackgroundColor(Color.parseColor("#ffef00"));
                    findViewById(R.id.baslik).setBackgroundColor(Color.parseColor("#002749"));
                    ((TextView) findViewById(R.id.baslik)).setText("FENERBAHÇE CAMİASINA ARMAĞAN OLSUN");
                    ((TextView) findViewById(R.id.baslik)).setTextColor(Color.parseColor("#ffef00"));
                    ((ImageView) findViewById(R.id.logo)).setImageResource(R.drawable.fblogo);

                    int[] buttonIds = {R.id.btnSifir, R.id.btnBir, R.id.btnIki, R.id.btnUc, R.id.btnDört, R.id.btnBes, R.id.btnAlti,
                            R.id.btnYedi, R.id.btnSekiz, R.id.btnDokuz, R.id.btnDecimal, R.id.btnEksi, R.id.btnArti, R.id.btnCarp,
                            R.id.btnBöl, R.id.btnMod, R.id.btnTemizle, R.id.btnEsittir};
                    for (int id : buttonIds) {
                        Button button = findViewById(id);
                        button.setBackgroundColor(Color.parseColor("#002749"));
                        button.setTextColor(Color.parseColor("#ffef00"));
                    }
                } else {

                    // Galatasaray teması
                    findViewById(R.id.toolBar).setBackgroundColor(Color.parseColor("#B30C28"));
                    findViewById(R.id.baslik).setBackgroundColor(Color.parseColor("#FFCC33"));
                    ((TextView) findViewById(R.id.baslik)).setText("GALATASARAY CAMİASINA ARMAĞAN OLSUN");
                    ((TextView) findViewById(R.id.baslik)).setTextColor(Color.parseColor("#B30C28"));
                    ((ImageView) findViewById(R.id.logo)).setImageResource(R.drawable.gs);


                    int[] buttonIds = {R.id.btnSifir, R.id.btnBir, R.id.btnIki, R.id.btnUc, R.id.btnDört, R.id.btnBes, R.id.btnAlti,
                            R.id.btnYedi, R.id.btnSekiz, R.id.btnDokuz, R.id.btnDecimal, R.id.btnEksi, R.id.btnArti, R.id.btnCarp,
                            R.id.btnBöl, R.id.btnMod, R.id.btnTemizle, R.id.btnEsittir};
                    for (int id : buttonIds) {
                        Button button = findViewById(id);
                        button.setBackgroundColor(Color.parseColor("#B30C28"));
                        button.setTextColor(Color.parseColor("#FFCC33"));
                    }
                }
                // Tema durumunu değiştir
                tema = !tema;
            }
        });
        builder.setView(dialogView);
        modlarDialog = builder.create();
    }

    // Buton dinleyicilerini ayarlıyoruz
    void dinleyiciler() {
        // Butonların id'lerini bir diziye atama
        int[] buttonIds = {R.id.btnSifir, R.id.btnBir, R.id.btnIki, R.id.btnUc, R.id.btnDört, R.id.btnBes, R.id.btnAlti,
                R.id.btnYedi, R.id.btnSekiz, R.id.btnDokuz, R.id.btnDecimal, R.id.btnEksi, R.id.btnArti, R.id.btnCarp,
                R.id.btnBöl, R.id.btnMod};

        // Her buton için OnClickListener eklemek adına for döngüsü
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(buttonClickListener);
        }
        // Temizleme butonuna OnClickListener ekliyoruz
        findViewById(R.id.btnTemizle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = txtGirdi.getText().toString();
                if (!input.isEmpty()) {
                    txtGirdi.setText(input.substring(0, input.length() - 1));
                }
            }
        });

        // Eşittir butonuna OnClickListener ekliyoruz
        findViewById(R.id.btnEsittir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expression = txtGirdi.getText().toString();
                double result = evaluateExpression(expression);
                txtGirdi.setText(String.valueOf(result));
            }
        });

        // Mod butonuna OnClickListener ekliyoruz (Butona basıldığında yapılacaklar)
        findViewById(R.id.btnMod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modlarDialog.show(); //Modlar Dialogu Gösteriyoruz
            }
        });
    }

    // Diğer buton tıklama işlemleri için OnClickListener
    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            String buttonText = button.getText().toString();
            txtGirdi.append(buttonText); //Butonların textini txtgirdiye ekle her basıldığında...
        }
    };

    // Matematiksel ifadeyi hesaplayan metodumuz
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

    // AlertDialog gösteren metod
    public static void mesajKutusu(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // AlertDialog'u kapatma
            }
        }).show();
    }
}
