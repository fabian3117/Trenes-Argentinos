package com.example.trenes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.trenes.Apis.General;
import com.example.trenes.databinding.FragmentSecondBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    ExtendedFloatingActionButton btn;
    TextView floating_action_butt;
    String[] Alertas;
   // SharedPreferences sharedPreferences;

    public SecondFragment(String[] param){
        Alertas=param;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);


       // floating_action_butt=binding.AlertaHorario;
        btn=binding.floatingActionButton;
        if(Alertas==null||Alertas.length<=0){
            binding.animationView.setVisibility(View.VISIBLE);
            binding.animationView.playAnimation();
        }
        else{
            binding.animationView.setVisibility(View.GONE);
            AdapterAlarmas adapterAlarmas=new AdapterAlarmas(Alertas);
            binding.recyclerAlertas.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerAlertas.setAdapter(adapterAlarmas);

        }
        //-->   Establesco los valores del intervalo de los tiempos <---
        binding.SelectorHora.setMinValue(0);
        binding.SelectorHora.setMaxValue(24);
        binding.SelectorMin.setMinValue(0);
        binding.SelectorMin.setMaxValue(60);

        //-->   Configuro el bottomSheet    <---
        FrameLayout bottomSheet=binding.standardBottomSheet;
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setHideable(true);
        binding.AlertasBTNHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.LayoutHorarioAlerta.setVisibility(View.VISIBLE);
            }
        });
        binding.SecondBTNListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.LayoutHorarioAlerta.setVisibility(View.GONE);
                binding.floatingActionButton.setIcon(getResources().getDrawable(R.drawable.document_check));
                binding.floatingActionButton.setText("GUARDAR");
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.setText("Nuevo");
                binding.floatingActionButton.setIcon(getResources().getDrawable(R.drawable.check));
                bottomSheetBehavior.setState((bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED)?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}