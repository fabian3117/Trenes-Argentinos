package com.example.trenes.Apis;

import java.util.List;
/* loaded from: classes2.dex */
public class AlertaResponse {
    public List<Alerta> alertas;
    public EstadoOperacion estado;
    public Integer id;
    public String nombre;
    public List<RamalAlerta> ramales;

    public Integer getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public EstadoOperacion getEstado() {
        return this.estado;
    }

    public List<Alerta> getAlertas() {
        return this.alertas;
    }

    public List<RamalAlerta> getRamales() {
        return this.ramales;
    }
}
