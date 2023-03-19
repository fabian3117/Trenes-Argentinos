package com.example.trenes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterAlarmas extends RecyclerView.Adapter<AdapterAlarmas.ViewHolder> {
    String[] Parametros;
    public AdapterAlarmas(String[] Para){
        Parametros=Para;
    }
    @NonNull
    @Override
    public AdapterAlarmas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recicleralarmas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAlarmas.ViewHolder holder, int position) {
    String Hora=Parametros[position].substring(0,2)+":"+Parametros[position].substring(2,4);
    String Ramal=Parametros[position].substring(4,Parametros[position].length());
    holder.AlertHora.setText(Hora);
    holder.AlertaRamal.setText(Ramal);
    }

    @Override
    public int getItemCount() {
        return Parametros.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView AlertaRamal,AlertHora;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            AlertaRamal=itemView.findViewById(R.id.AlertaRamal);
            AlertHora=itemView.findViewById(R.id.AlertaHora);
        }
    }
}
