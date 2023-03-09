package com.example.trenes.Apis;

import java.util.List;
import org.parceler.Parcel;
@Parcel
/* loaded from: classes2.dex */
public class RamalDetalle {
    String cabeceraFinal;
    String cabeceraInicial;
    List<Estacion> estaciones;
    int id;
    LineaSimple linea;
    String nombre;
    Boolean publico;

    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getCabeceraInicial() {
        return this.cabeceraInicial;
    }

    public String getCabeceraFinal() {
        return this.cabeceraFinal;
    }

    public Boolean isPublico() {
        return this.publico;
    }

    public LineaSimple getLinea() {
        return this.linea;
    }

    public List<Estacion> getEstaciones() {
        return this.estaciones;
    }

    public String getNombreLargo() {
        return String.format("%s - %s", getCabeceraInicial(), getCabeceraFinal());
    }
}
