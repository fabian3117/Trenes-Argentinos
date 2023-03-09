package com.example.trenes.Apis;

import org.parceler.Parcel;
@Parcel
/* loaded from: classes2.dex */
public class Alerta {
    public String colorFondo;
    public String colorTexto;
    public Integer id;
    public String mensaje;
    public RamalDetalle ramal;

    public Integer getId() {
        return this.id;
    }

    public String getMensaje() {
        return this.mensaje;
    }
}
