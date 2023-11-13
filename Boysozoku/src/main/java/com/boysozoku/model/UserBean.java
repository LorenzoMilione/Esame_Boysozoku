package com.boysozoku.model;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.File;

public class UserBean {
    File propic = null;
    private String username;
    private String email;
    private String indirizzo;
    private String appartamento;
    private String citta;
    private String stato;
    private int cap;

    public File getPropic() {
        return propic;
    }

    public void setPropic(File propic) {
        this.propic = propic;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public UserBean(){
        propic = null;
        username = "";
        email = "";
        indirizzo = "";
        appartamento = "";
        citta = "";
        stato = "";
    }

    public UserBean(String username, String email, String indirizzo, String appartamento, String citta, String provincia, int cap, String propic) {
        this.username = username;
        this.email = email;
        this.indirizzo = indirizzo;
        this.appartamento = appartamento;
        this.citta = citta;
        this.stato = provincia;
        this.cap = cap;
        if(propic!=null) this.propic = new File(propic);
    }

    public String getAppartamento() {
        return appartamento;
    }

    public void setAppartamento(String appartamento) {
        this.appartamento = appartamento;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return stato;
    }

    public void setProvincia(String provincia) {
        this.stato = provincia;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
