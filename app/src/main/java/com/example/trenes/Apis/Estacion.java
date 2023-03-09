package com.example.trenes.Apis;

import java.util.List;
import org.parceler.Parcel;
@Parcel
/* loaded from: classes2.dex */
public class Estacion {
    int id;
    String nombre;
    String nombreCorto;
    Posicion posicion;
    boolean publico;
    List<RamalDetalle> ramales;

    public String toString() {
        return this.nombre;
    }

    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getNombreCorto() {
        return this.nombreCorto;
    }

    public boolean isPublico() {
        return this.publico;
    }

    public Posicion getPosicion() {
        return this.posicion;
    }

    public List<RamalDetalle> getRamales() {
        return this.ramales;
    }
}
