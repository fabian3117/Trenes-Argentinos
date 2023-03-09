package com.example.trenes.Apis;

import java.util.List;
import org.parceler.Parcel;
@Parcel
/* loaded from: classes2.dex */
public class Servicio {
    String anden;
    Boolean ascendente;
    EstacionParada cabeceraFinal;
    EstacionParada cabeceraInicial;
    Boolean despachado;
    EstacionParada destino;
    List<EstacionParada> estaciones;
    Formacion formacion;
    Integer id;
    EstacionParada origen;
    RamalDetalle ramal;
    Boolean realtime;
    Salida salida;
    TipoServicio tipoServicio;
    String tren;

    public Integer getId() {
        return this.id;
    }

    public String getTren() {
        return this.tren;
    }

    public RamalDetalle getRamal() {
        return this.ramal;
    }

    public String getAnden() {
        return this.anden;
    }

    public EstacionParada getOrigen() {
        return this.origen;
    }

    public EstacionParada getDestino() {
        return this.destino;
    }

    public List<EstacionParada> getEstaciones() {
        return this.estaciones;
    }

    public TipoServicio getTipoServicio() {
        return this.tipoServicio;
    }

    public Salida getSalida() {
        return this.salida;
    }

    public Boolean getAscendente() {
        return this.ascendente;
    }

    public Boolean getDespachado() {
        return this.despachado;
    }

    public Boolean isRealTime() {
        return this.realtime;
    }

    public Formacion getFormacion() {
        return this.formacion;
    }

    public EstacionParada getCabeceraInicial() {
        return this.cabeceraInicial;
    }

    public EstacionParada getCabeceraFinal() {
        return this.cabeceraFinal;
    }
}
