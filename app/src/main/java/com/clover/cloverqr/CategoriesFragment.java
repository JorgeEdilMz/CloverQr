package com.clover.cloverqr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.international.magic4rt.R;

public class CategoriesFragment extends Fragment {

    private Button Cactus;
    private Button Arboretum;
    private Button Heliconias;
    private Button Orquideas;
    private Button Palmas;
    private Button Bromelias;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Cactus = view.findViewById(R.id.btn_cactus);
        Arboretum = view.findViewById(R.id.btn_bastones);
        Heliconias = view.findViewById(R.id.btn_heliconias);
        Orquideas = view.findViewById(R.id.btn_orquideas);
        Palmas = view.findViewById(R.id.btn_palmas);
        Bromelias = view.findViewById(R.id.btn_quiches);

        Cactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new cactus_fragment());
            }
        });

        Arboretum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new arboretum_fragment());
            }
        });

        Heliconias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new heliconias_fragment());
            }
        });

        Orquideas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new EsculturasCategoriesFragment());
            }
        });

        Palmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new EsculturasCategoriesFragment());
            }
        });

        Bromelias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new EsculturasCategoriesFragment());
            }
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
