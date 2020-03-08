package pucminas.br.calculoimc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText inputPeso;
    private EditText inputAltura;
    private TextView txtPeso;
    private TextView txtAltura;
    private TextView txtIMC;

    private double calcularIMC(String strPeso, String strAltura)
    {
        Double imc = 0.0;

        try
        {
            Double peso = Double.parseDouble(strPeso);
            // Converte a altura para metros dividindo por 100
            Double altura = Double.parseDouble(strAltura) / 100;

            imc = peso / (altura * altura);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return imc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputPeso = (EditText) findViewById(R.id.inputPeso);
        inputAltura = (EditText) findViewById(R.id.inputAltura);
        txtPeso = (TextView) findViewById(R.id.txtPeso);
        txtAltura = (TextView) findViewById(R.id.txtAltura);
        txtIMC = (TextView) findViewById(R.id.txtIMC);

        inputAltura.addTextChangedListener(
            new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String strPeso = inputPeso.getText().toString();
                    String strAltura = editable.toString();

                    txtIMC.setText("Seu IMC é: " + calcularIMC(strPeso, strAltura));
                }
            }
        );

        inputPeso.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        String strAltura = inputAltura.getText().toString();
                        String strPeso = editable.toString();

                        txtIMC.setText("Seu IMC é: " + calcularIMC(strPeso, strAltura));
                    }
                }
        );
    }
}
