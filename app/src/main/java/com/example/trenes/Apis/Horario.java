package com.example.trenes.Apis;
import android.content.Context;

import com.example.trenes.R;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.parceler.Parcel;
@Parcel
public class Horario {
    public EstacionParada desde;
    public EstacionParada hasta;
    public Salida salida;
    public Servicio servicio;

    public String getTextoCompartir(Context context) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy 'a las' HH:mm ' hs'");
        TimeZone timeZone = TimeZone.getTimeZone(context.getString(R.string.timezone));
        simpleDateFormat.setTimeZone(timeZone);
        simpleDateFormat2.setTimeZone(timeZone);
        if (this.servicio.isRealTime().booleanValue()) {
            if (this.salida != null) {
                if (this.hasta != null) {
                    return String.format(context.getString(R.string.compartir_realtime_circulando), this.desde.getNombreCorto(), this.hasta.getNombreCorto(), simpleDateFormat.format(this.desde.getLlegada()));
                }
                return String.format(context.getString(R.string.compartir_realtime_circulando_sin_destino), this.desde.getNombreCorto(), simpleDateFormat.format(this.desde.getLlegada()));
            } else if (this.hasta != null) {
                return String.format(context.getString(R.string.compartir_realtime_salida), this.desde.getNombreCorto(), this.hasta.getNombreCorto(), String.valueOf(Math.round(this.desde.getSegundos().intValue() / 60)));
            } else {
                return String.format(context.getString(R.string.compartir_realtime_salida_sin_destino), this.desde.getNombreCorto(), String.valueOf(Math.round(this.desde.getSegundos().intValue() / 60)));
            }
        }
        return String.format(context.getString(R.string.compartir_programado), this.desde.getNombreCorto(), this.hasta.getNombreCorto(), simpleDateFormat2.format(this.desde.getLlegada()), simpleDateFormat2.format(this.hasta.getLlegada()));

    }

    public String getSubjectCompartir(Context context) {
        return this.hasta != null ? String.format("Tren desde %s a %s.", this.desde.getNombreCorto(), this.hasta.getNombreCorto()) : String.format("Tren desde %s.", this.desde.getNombreCorto());
}}
