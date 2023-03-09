package com.example.trenes.Apis;

import java.util.Date;
import org.parceler.Parcel;
@Parcel
/* loaded from: classes2.dex */
public class Salida {
    String anden;
    boolean confirmada;
    EstadoServicio estado;
    Date fechaHora;
    int id;

    public int getId() {
        return this.id;
    }

    public Date getFechaHora() {
        return this.fechaHora;
    }

    public EstadoServicio getEstado() {
        return this.estado;
    }

    public String getAnden() {
        return this.anden;
    }

    public boolean isConfirmada() {
        return this.confirmada;
    }
}
