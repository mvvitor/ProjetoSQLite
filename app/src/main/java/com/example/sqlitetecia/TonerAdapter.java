package com.example.sqlitetecia;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.List;


public class TonerAdapter extends ArrayAdapter<Toner> {

    //Variáveis globais
    Context mCtx;
    int listaLayoutRes;
    List<Toner> listaToners;
    SQLiteDatabase meuBancoDeDados;

    public TonerAdapter(@NonNull Context mCtx, int listaLayoutRes, List<Toner> listaToners, SQLiteDatabase meuBancoDeDados) {
        super(mCtx, listaLayoutRes, listaToners);
        this.mCtx = mCtx;
        this.listaLayoutRes = listaLayoutRes;
        this.listaToners = listaToners;
        this.meuBancoDeDados = meuBancoDeDados;
    }

    //Inflar layout com o modelo e suas ações
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listaLayoutRes, null);

        final Toner toner = listaToners.get(position);

        TextView txtNomeViewToner = view.findViewById(R.id.txtNomeViewToner);
        TextView txtViewModeloImpressoras = view.findViewById(R.id.txtViewModeloImpressoras);
        TextView txtViewDataEntrada = view.findViewById(R.id.txtViewDataEntrada);
        TextView txtViewDataSaida = view.findViewById(R.id.txtViewDataSaida);

        txtNomeViewToner.setText(toner.getNomeToner());
        txtViewModeloImpressoras.setText(toner.getNomeSpnImpressoras());
        txtViewDataEntrada.setText(toner.getNomeEntrada());
        txtViewDataSaida.setText(toner.getNomeSaida());


        Button btnExcluirViewModeloToner = view.findViewById(R.id.btnExcluirViewModeloToner);
        Button btnEditarViewModeloToner = view.findViewById(R.id.btnEditarViewModeloToner);

        btnEditarViewModeloToner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterarToner(toner);
            }
        });

        btnExcluirViewModeloToner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Deseja excluir?");
                builder.setIcon(android.R.drawable.ic_input_delete);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM Toner WHERE id_toner = ?";
                        meuBancoDeDados.execSQL(sql, new Integer[]{toner.getId_toner()});
                        //chamar o método para atualizar a lista de toners
                        recarregarTonerDB();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //não irá executar nada
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;

    }

    public void alterarToner(final Toner toner) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.caixa_alterar_toner, null);
        builder.setView(view);

        final EditText txtEditarModeloToner = view.findViewById(R.id.txtEditarModeloToner);
        final Spinner spnCadModeloImpressora = view.findViewById(R.id.spnCadModeloImpressora);
        final EditText txtEditarDataEntrada = view.findViewById(R.id.txtEditarDataEntrada);
        final EditText txtEditarDataSaida = view.findViewById(R.id.txtEditarDataSaida);

        txtEditarModeloToner.setText(toner.getNomeToner());
        txtEditarDataSaida.setText(toner.getNomeSaida());

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.btnAlterarToner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeToner = txtEditarModeloToner.getText().toString().trim();
                String nomeSpnImpressoras = spnCadModeloImpressora.getSelectedItem().toString().trim();
                String nomeEntrada = txtEditarDataEntrada.getText().toString().trim();
                String nomeSaida = txtEditarDataSaida.getText().toString().trim();


                if (nomeToner.isEmpty()) {
                    txtEditarModeloToner.setError("Nome está em branco");
                    txtEditarModeloToner.requestFocus();
                    return;
                }
                if (nomeEntrada.isEmpty()) {
                    txtEditarDataEntrada.setError("Data de entrada está em branco");
                    txtEditarDataEntrada.requestFocus();
                    return;
                }
                if (nomeSaida.isEmpty()) {
                    txtEditarDataSaida.setError("Data de saída está em branco");
                    txtEditarDataSaida.requestFocus();
                    return;
                }

                String sql = "UPDATE Toner SET nomeToner = ?, nomeSpnImpressoras = ?, nomeEntrada = ?, nomeSaida = ? WHERE id_toner = ?";
                meuBancoDeDados.execSQL(sql,
                        new String[]{nomeToner, nomeSpnImpressoras, nomeEntrada, nomeSaida, String.valueOf(toner.getId_toner())});
                Toast.makeText(mCtx, "Toner alterado com sucesso!!!", Toast.LENGTH_LONG).show();

                //chamar o método para atualizar a lista de toners
                recarregarTonerDB();

                //limpa a estrutura do AlertDialog
                dialog.dismiss();
            }
        });
    }

    public void recarregarTonerDB() {
        Cursor cursorToner = meuBancoDeDados.rawQuery("SELECT * FROM Estoque_de_TonerBD", null);
        if (cursorToner.moveToFirst()) {
            listaToners.clear();
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
        notifyDataSetChanged();
    }
}

