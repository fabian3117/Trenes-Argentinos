package com.example.trenes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trenes.Apis.AdapterLineas;
import com.example.trenes.Apis.LineaSimple;

import java.util.List;

public class AdapterFragmentPrincipal extends Fragment {
    public AdapterLineas adapterLineas=new AdapterLineas();
    public RecyclerView recycler_viewLineas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        recycler_viewLineas=view.findViewById(R.id.recycler_viewLineas);
        recycler_viewLineas.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_viewLineas.setAdapter(adapterLineas);
        return view;
    }
    public void Datos(List<LineaSimple> list){
        adapterLineas.setData(list);

    }
}
