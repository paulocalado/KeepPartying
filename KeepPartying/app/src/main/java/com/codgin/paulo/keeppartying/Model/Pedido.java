package com.codgin.paulo.keeppartying.Model;

import java.util.List;

/**
 * Created by Paulo on 02/08/2017.
 */

public class Pedido {
    int id;
    double total;
    double lat;
    double lon;
    String contato;
    String referencia;
    String userId;

    public Pedido(int id, double total, double lat, double lon, String contato, String referencia, String userId) {
        this.id = id;
        this.total = total;
        this.lat = lat;
        this.lon = lon;
        this.contato = contato;
        this.referencia = referencia;
        this.userId = userId;
    }

    public Pedido(String contato, String referencia){
        this.contato = contato;
        this.referencia = referencia;
    }
    public Pedido(){

    }
    public Pedido(int id, double total, double lat, double lon) {
        this.id = id;
        this.total = total;
        this.lat = lat;
        this.lon = lon;
    }


    public Pedido(int id, double total) {
        this.id = id;
        this.total = total;
    }

    public double getLat() {
        return lat;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
