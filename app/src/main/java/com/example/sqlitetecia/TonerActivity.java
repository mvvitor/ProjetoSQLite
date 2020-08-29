package com.example.sqlitetecia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TonerActivity extends AppCompatActivity {

    List<Toner> listaToners;
    TonerAdapter tonerAdapter;
    SQLiteDatabase meuBancoDeDados;
    ListView listarViewToner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toner_layout);

        listarViewToner = findViewById(R.id.listarViewToner);
        listaToners = new ArrayList<>();

        meuBancoDeDados = openOrCreateDatabase(MainActivity.NOME_BANCO_DE_DADOS, MODE_PRIVATE, null);

        visualizarTonerDatabase();
    }

    //Irá listar todos os toners do banco de dados
    private void visualizarTonerDatabase() {

        Cursor cursorToner = meuBancoDeDados.rawQuery("SELECT * FROM Toner", null);
        if (cursorToner.moveToFirst()) {
            do {
                listaToners.add(new Toner(
                        cursorToner.getInt(0),
                        cursorToner.getString(1),
                        cursorToner.getString(2),
                        cursorToner.getString(3),
                        cursorToner.getString(4)
                ));
            } while (cursorToner.moveToNext());
        }
        cursorToner.close();

        //Verificar o layout
        tonerAdapter = new TonerAdapter(this, R.layout.lista_view_toner, listaToners, meuBancoDeDados);

        listarViewToner.setAdapter(tonerAdapter);
    }
}