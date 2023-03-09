package com.example.trenes.Apis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trenes.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Lineas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Lineas extends Fragment {



    public Lineas() {
        // Required empty public constructor
    }
public static Lineas newInstance(String param1, String param2) {
        Lineas fragment = new Lineas();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lineas, container, false);
    }
}