package com.example.trenes.Apis;

import java.util.Date;
import org.parceler.Parcel;
@Parcel
/* loaded from: classes2.dex */
public class EstacionParada {
    Boolean activa;
    Boolean alcanzada;
    String anden;
    Integer id;
    Date llegada;
    String nombre;
    String nombreCorto;
    Integer orden;
    Boolean parada;
    Float progreso;
    Boolean publico;
    Integer segundos;

    public int getId() {
        return this.id.intValue();
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getNombreCorto() {
        return this.nombreCorto;
    }

    public int getOrden() {
        return this.orden.intValue();
    }

    public Date getLlegada() {
        return this.llegada;
    }

    public Integer getSegundos() {
        return this.segundos;
    }

    public boolean isParada() {
        return this.parada.booleanValue();
    }

    public boolean isAlcanzada() {
        return this.alcanzada.booleanValue();
    }

    public boolean isPublico() {
        return this.publico.booleanValue();
    }

    public boolean isActiva() {
        return this.activa.booleanValue();
    }

    public String getAnden() {
        return this.anden;
    }

    public Float getProgreso() {
        return this.progreso;
    }
}
