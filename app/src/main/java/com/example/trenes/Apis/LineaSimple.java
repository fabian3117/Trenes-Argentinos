package com.example.trenes.Apis;

import org.parceler.Parcel;
@Parcel
/* loaded from: classes2.dex */
public class LineaSimple {
    EstadoOperacion estado;
    int id;
    String imageUrl;
    String nombre;
    Boolean publico;

    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public boolean isPublico() {
        Boolean bool = this.publico;
        if (bool == null) {
            return true;
        }
        return bool.booleanValue();
    }

    public EstadoOperacion getEstado() {
        return this.estado;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }
}
