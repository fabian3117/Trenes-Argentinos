package com.example.trenes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trenes.Apis.AdapterLineas;
import com.example.trenes.Apis.Horario;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdapterTrenVer extends RecyclerView.Adapter<AdapterTrenVer.ViewHolder>{
    List<Horario> Horar;
    public AdapterTrenVer(List<Horario> par){
        Horar=par;
    }
    @NonNull
    @Override
    public AdapterTrenVer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciclervertren, parent, false);
        return new AdapterTrenVer.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTrenVer.ViewHolder holder, int position) {
    holder.Destino.setText(Horar.get(position).servicio.getDestino().getNombre());
    holder.Sentido.setText(Horar.get(position).servicio.getDestino().getNombre());
    holder.Tipo.setText(Horar.get(position).servicio.getTipoServicio().getNombre());
    holder.Ramal.setText(Horar.get(position).servicio.getRamal().getNombre());
    Calendar calendar=Calendar.getInstance();
    Date dat=Horar.get(position).desde.getLlegada();
    int difMin=calendar.get(Calendar.MINUTE)-dat.getMinutes();
    holder.TiempoRestante.setText(String.valueOf(Math.abs(difMin)));

    }

    @Override
    public int getItemCount() {
        return Horar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView Sentido,Ramal,Destino,Tipo;
       TextView TiempoRestante;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Sentido=itemView.findViewById(R.id.SentidoT);
            Ramal=itemView.findViewById(R.id.Ramal);
            TiempoRestante=itemView.findViewById(R.id.TiempoRestante);
            Destino=itemView.findViewById(R.id.Destino);
            Tipo=itemView.findViewById(R.id.Tipo);
        }
    }
}
