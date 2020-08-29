package com.example.sqlitetecia;

public class Toner {

        int id_toner;
        String nomeToner, nomeEntrada, nomeSaida,nomeSpnImpressoras;

    public Toner() {
    }

    public Toner(int id_toner, String nomeToner, String nomeEntrada, String nomeSaida, String nomeSpnImpressoras) {
        this.id_toner = id_toner;
        this.nomeToner = nomeToner;
        this.nomeEntrada = nomeEntrada;
        this.nomeSaida = nomeSaida;
        this.nomeSpnImpressoras = nomeSpnImpressoras;
    }

    public int getId_toner() {
        return id_toner;
    }

    public void setId_toner(int id_toner) {
        this.id_toner = id_toner;
    }

    public String getNomeToner() {
        return nomeToner;
    }

    public void setNomeToner(String nomeToner) {
        this.nomeToner = nomeToner;
    }

    public String getNomeEntrada() {
        return nomeEntrada;
    }

    public void setNomeEntrada(String nomeEntrada) {
        this.nomeEntrada = nomeEntrada;
    }

    public String getNomeSaida() {
        return nomeSaida;
    }

    public void setNomeSaida(String nomeSaida) {
        this.nomeSaida = nomeSaida;
    }

    public String getNomeSpnImpressoras() {
        return nomeSpnImpressoras;
    }

    public void setNomeSpnImpressoras(String nomeSpnImpressoras) {
        this.nomeSpnImpressoras = nomeSpnImpressoras;
    }
}
