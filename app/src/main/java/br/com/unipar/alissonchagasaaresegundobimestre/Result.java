package br.com.unipar.alissonchagasaaresegundobimestre;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;


public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultado();
    }

    public void resultado() {
        Bundle x = getIntent().getExtras();
        String dataPagamento = x.getString("dataPagamento");
        Double saldoReceber = x.getDouble("renda");

        TextView textDataPagamento = (TextView) findViewById(R.id.dataPagamentoResult);
        textDataPagamento.setText(dataPagamento);

        TextView textValorReceber = (TextView) findViewById(R.id.valorReceberResult);
        textValorReceber.setText(saldoReceber.toString());


    }
}