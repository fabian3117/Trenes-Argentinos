package com.example.trenes.Apis;

import java.util.List;
/* loaded from: classes2.dex */
public class RamalAlerta {
    public List<Alerta> alertas;
    public String cabeceraFinal;
    public String cabeceraInicial;
    public Integer id;
    public String nombre;

    public Integer getId() {
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

    public List<Alerta> getAlertas() {
        return this.alertas;
    }
}
