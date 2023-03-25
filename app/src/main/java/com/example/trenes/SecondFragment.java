package com.example.trenes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        btn=binding.floatingActionButton;
        FrameLayout bottomSheet=binding.standardBottomSheet;
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setHideable(true);
        if(Alertas==null||Alertas.length<=0){
            binding.animationView.setVisibility(View.VISIBLE);
            binding.animationView.playAnimation();
        }
        else{
            binding.animationView.setVisibility(View.GONE);
            AdapterAlarmas adapterAlarmas=new AdapterAlarmas(Alertas);
            binding.recyclerAlertas.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerAlertas.setAdapter(adapterAlarmas);
            binding.recyclerAlertas.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null) {
                        int position = rv.getChildAdapterPosition(childView);
                        if (position != RecyclerView.NO_POSITION) {
                            TextView textView = childView.findViewById(R.id.AlertaHora); // Reemplaza "R.id.textview_alerta" con el ID del TextView en tu diseño de elemento de RecyclerView
                            TextView textView1=childView.findViewById(R.id.AlertaRamal);
                            String Rama=textView1.getText().toString();
                            String Hora = textView.getText().toString();
                            //-->   Muesto mis datos en el side Shat
                            binding.AlertasBTNHorario.setText(Hora);
                            binding.standardBottomSheet.setVisibility(View.VISIBLE);
                            binding.LayoutHorarioAlerta.setVisibility(View.VISIBLE);
                            binding.SelectorHora.setValue(Integer.parseInt(Hora.substring(0,2)));
                            binding.SelectorMin.setValue(Integer.parseInt(Hora.substring(3,5)));

                            String Rm=General.QueRamalEs(Integer.parseInt(Rama));
                            //-->   Que ramal es?   <--
                            for(int i=0;i<binding.ChipsRamas.getChildCount();i++){
                             View v=   binding.ChipsRamas.getChildAt(i);
                            if(v instanceof TextView){
                                TextView textView3=(TextView) v;
                                if(textView3.getText().equals(Rm)) {
                                    textView3.setPressed(true);
                                    textView3.setBackgroundColor(Color.parseColor("#3200EE"));
                                }
                            }
                            }
                            bottomSheetBehavior.setState((bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED)?BottomSheetBehavior.STATE_COLLAPSED:BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }
                    return true;
                }
                @Override
                public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {}

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
            });

        }
        //-->   Establesco los valores del intervalo de los tiempos <---
        binding.SelectorHora.setMinValue(0);
        binding.SelectorHora.setMaxValue(24);
        binding.SelectorMin.setMinValue(0);
        binding.SelectorMin.setMaxValue(60);

        //-->   Configuro el bottomSheet    <---

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