package com.example.trenes.Apis;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trenes.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterLineas extends RecyclerView.Adapter<AdapterLineas.ViewHolder> {
    List<LineaSimple> listado;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lineas, parent, false);
        return new ViewHolder(view);
//        return new ViewHolder(view);
    }

    public void setData(List<LineaSimple> list) {
        this.listado = list;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterLineas.ViewHolder holder, int position) {
        LineaSimple lineaSimple = this.listado.get(position);
        holder.txtLinea.setText(lineaSimple.getNombre());
        holder.imageEstado.setColorFilter(Color.parseColor("#eeeeee"));
        holder.txtEstado.setText("Normal");
        if (lineaSimple.getEstado() != null) {
            try {
                holder.imageEstado.setColorFilter(Color.parseColor(lineaSimple.getEstado().getColor()));
            } catch (Exception unused) {
                holder.imageEstado.setColorFilter(Color.parseColor("#eeeeee"));
            }
            holder.txtEstado.setText(lineaSimple.getEstado().getMensaje());
        } else {
            holder.imageEstado.setColorFilter(Color.parseColor("#eeeeee"));
            holder.txtEstado.setText("Normal");
        }
    }


    @Override
    public int getItemCount() {
        return(listado!=null)?listado.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageEstado;
//        TextView BusEstacion;
        TextInputLayout BusEstacion;
        boolean tocada=false;
        LinearLayout layoutLinea;
        View mainView;
        View separador;
        TextView txtEstado;
        TextView txtLinea;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLinea=itemView.findViewById(R.id.txt_linea);
            txtEstado=itemView.findViewById(R.id.txt_estado);
            imageEstado=itemView.findViewById(R.id.image_estado);
            separador=itemView.findViewById(R.id.separador);
            layoutLinea=itemView.findViewById(R.id.LayoutCard);
            BusEstacion=itemView.findViewById(R.id.textField);
            layoutLinea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("TAG","TOCAMOS :  " +txtLinea.getText());
                    if(tocada){
                        //-->   Ahora utilizando la ubicacion y la linea que se toco deberia mostrar la estacion mas cercana    <--
                        General.trenesApi.buscarCercanas(General.Token,General.localizacion.getLatitude(),General.localizacion.getLongitude(),5,100).enqueue(new Callback<PaginationContainer<CercanasResponse>>() {
                            @Override
                            public void onResponse(Call<PaginationContainer<CercanasResponse>> call, Response<PaginationContainer<CercanasResponse>> response) {
                             Log.e("Ubicacion",General.localizacion.getLatitude()+","+General.localizacion.getLongitude());

                             Log.e("SALIDA",response.body().getResults().get(0).estacion.getNombre());
                             BusEstacion.setHint(response.body().getResults().get(0).estacion.getNombre());
                            // BusEstacion.setText(response.body().getResults().get(0).estacion.getNombre());
                            }

                            @Override
                            public void onFailure(Call<PaginationContainer<CercanasResponse>> call, Throwable t) {

                            }
                        });
                        itemView.findViewById(R.id.Interrupcion).setVisibility(View.VISIBLE);
                        TextView vs=itemView.findViewById(R.id.MasDeta);
                        vs.setText(txtEstado.getText());
                    }
                    else{
                        itemView.findViewById(R.id.Interrupcion).setVisibility(View.GONE);
                        //itemView.findViewById(R.id.MasDeta).setVisibility(View.GONE);

                    }
                    tocada=!tocada;
                  // view.getContext().startActivity(new Intent(view.getContext(),Estado.class));

                }
            });
        }
    }
}
