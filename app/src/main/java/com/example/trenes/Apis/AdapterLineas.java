package com.example.trenes.Apis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trenes.AdapterTrenVer;
import com.example.trenes.EstacionVer;
import com.example.trenes.MainActivity;
import com.example.trenes.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterLineas extends RecyclerView.Adapter<AdapterLineas.ViewHolder> {
    List<LineaSimple> listado;
    ArrayList<String> Autocomplet = new ArrayList<>();
    ArrayList<Integer> Ides=new ArrayList<>();
    int I_Ram;
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
        holder.ID_Lin=this.listado.get(position).id;
        holder.txtLinea.setText(lineaSimple.getNombre());
        holder.imageEstado.setColorFilter(Color.parseColor("#eeeeee"));
        holder.vs.setText(lineaSimple.getEstado().getMensaje());
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
        TextInputLayout BusEstacion;
        boolean tocada=true;
        LinearLayout layoutLinea;
        AutoCompleteTextView TextEntrada;
        View separador;
        TextView txtEstado;
        TextView txtLinea;
        TextView vs;
        int ID_Lin;
        int ID_Rama;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            int[] IdEstacion = new int[1];
            txtLinea=itemView.findViewById(R.id.txt_linea);
            txtEstado=itemView.findViewById(R.id.txt_estado);
            imageEstado=itemView.findViewById(R.id.image_estado);
            separador=itemView.findViewById(R.id.separador);
            layoutLinea=itemView.findViewById(R.id.LayoutCard);
            BusEstacion=itemView.findViewById(R.id.textField);
            TextEntrada=itemView.findViewById(R.id.TextEntrada);
            vs=itemView.findViewById(R.id.MasDeta);
            layoutLinea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tocada){
                         Context contexto=view.getContext();
                        //-->   Ahora utilizando la ubicacion y la linea que se toco deberia mostrar la estacion mas cercana    <--
                        General.trenesApi.buscarCercanas(General.Token,General.localizacion.getLatitude(),General.localizacion.getLongitude(),ID_Lin,General.RadioTest).enqueue(new Callback<PaginationContainer<CercanasResponse>>() {
                            @Override
                            public void onResponse(Call<PaginationContainer<CercanasResponse>> call, Response<PaginationContainer<CercanasResponse>> response) {

                             IdEstacion[0] =response.body().results.get(0).estacion.getId();
                             for (int i=0;i<response.body().results.size();i++){
                                Autocomplet.add(response.body().results.get(i).estacion.nombre);
                                 Ides.add(response.body().results.get(i).estacion.getId());
                             }

                             //-->  Tengo que Asociar cada elemento del Autocompletar con el ID_Estacion    <--
                            //-->   Guardar todos los ID en orden   <--
                                //-->   Cuando se Luego verifico que indice del Autocomplet tiene el valor ingresado y atraves del indice obtengo el valor asociado <--

                             ArrayAdapter<String> adapter= new ArrayAdapter<String>(contexto, android.R.layout.simple_dropdown_item_1line,Autocomplet);
                             TextEntrada.setAdapter(adapter);
                             BusEstacion.setHint(response.body().getResults().get(0).estacion.getNombre());

                            }

                            @Override
                            public void onFailure(Call<PaginationContainer<CercanasResponse>> call, Throwable t) {

                            }
                        });
                        itemView.findViewById(R.id.Interrupcion).setVisibility(View.VISIBLE);

                       // vs.setText(txtEstado.getText());
                    }
                    else{
                        itemView.findViewById(R.id.Interrupcion).setVisibility(View.GONE);
                    }
                    tocada=!tocada;
                }
            });
            itemView.findViewById(R.id.SearchBTN).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //-->   Voy a pantalla de busqueda puntual de la estacion deseada   <--
                    //-->   Utilizando bundle le pasare la informacion Ramal-Estacion   <--
                    Bundle Datos=new Bundle();
                    Datos.putInt("Rama",ID_Lin);    //-->   Paso el ID_Ramal    <--
                    //-->   Tengo que pasar el ID asociado al texto <--
                    Log.e("MIRA",TextEntrada.getText().toString());

                    for(int i=0;i<Autocomplet.size();i++){
                        if(Autocomplet.get(i).contains(TextEntrada.getText().toString())){
                        IdEstacion[0]= Ides.get(i);
                        i+=Autocomplet.size();  //--->  Para anular la condicion del for y salir    <--
                        }
                    }
                    Datos.putInt("Estacion",IdEstacion[0]); //-->   ID de estacion de interes   <--
                    Intent Cambio=new Intent(view.getContext(), EstacionVer.class);
                    Cambio.putExtras(Datos);
                    view.getContext().startActivity(Cambio);
                }
            });
        }

    }
}
