package com.clover.cloverqr.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.clover.cloverqr.R;
import com.clover.cloverqr.cactus_fragment;
import com.clover.cloverqr.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtener referencia al botón en el layout
        Button btnChangeFragment = root.findViewById(R.id.btn_cactus);

        // Establecer el click listener para el botón
        btnChangeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear e inicializar el fragmento que deseas mostrar
                Fragment cactusFragment = new cactus_fragment();

                // Reemplazar el fragmento actual (HomeFragment) con el nuevo fragmento (cactusFragment)
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, cactusFragment)
                        .addToBackStack(null) // Agregar a la pila de retroceso para volver al fragmento anterior al presionar el botón "Atrás"
                        .commit();
            }
        });

        // Obtener referencia al botón en el layout
        Button btnChangeBastones = root.findViewById(R.id.btn_bastones);

        // Establecer el click listener para el botón
        btnChangeBastones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear e inicializar el fragmento que deseas mostrar
                Fragment cactusFragment = new cactus_fragment();

                // Reemplazar el fragmento actual (HomeFragment) con el nuevo fragmento (cactusFragment)
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, cactusFragment)
                        .addToBackStack(null) // Agregar a la pila de retroceso para volver al fragmento anterior al presionar el botón "Atrás"
                        .commit();
            }
        });

        // Obtener referencia al botón en el layout
        Button btnHeliconias = root.findViewById(R.id.btn_heliconias);

        // Establecer el click listener para el botón
        btnHeliconias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear e inicializar el fragmento que deseas mostrar
                Fragment cactusFragment = new cactus_fragment();

                // Reemplazar el fragmento actual (HomeFragment) con el nuevo fragmento (cactusFragment)
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, cactusFragment)
                        .addToBackStack(null) // Agregar a la pila de retroceso para volver al fragmento anterior al presionar el botón "Atrás"
                        .commit();
            }
        });


        // Obtener referencia al botón en el layout
        Button btnOrquideas = root.findViewById(R.id.btn_orquideas);

        // Establecer el click listener para el botón
        btnOrquideas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear e inicializar el fragmento que deseas mostrar
                Fragment cactusFragment = new cactus_fragment();

                // Reemplazar el fragmento actual (HomeFragment) con el nuevo fragmento (cactusFragment)
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, cactusFragment)
                        .addToBackStack(null) // Agregar a la pila de retroceso para volver al fragmento anterior al presionar el botón "Atrás"
                        .commit();
            }
        });


        // Obtener referencia al botón en el layout
        Button btnPalmas = root.findViewById(R.id.btn_palmas);

        // Establecer el click listener para el botón
        btnPalmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear e inicializar el fragmento que deseas mostrar
                Fragment cactusFragment = new cactus_fragment();

                // Reemplazar el fragmento actual (HomeFragment) con el nuevo fragmento (cactusFragment)
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, cactusFragment)
                        .addToBackStack(null) // Agregar a la pila de retroceso para volver al fragmento anterior al presionar el botón "Atrás"
                        .commit();
            }
        });


        // Obtener referencia al botón en el layout
        Button btnQuiches = root.findViewById(R.id.btn_quiches);

        // Establecer el click listener para el botón
        btnQuiches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear e inicializar el fragmento que deseas mostrar
                Fragment cactusFragment = new cactus_fragment();

                // Reemplazar el fragmento actual (HomeFragment) con el nuevo fragmento (cactusFragment)
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_content_main, cactusFragment)
                        .addToBackStack(null) // Agregar a la pila de retroceso para volver al fragmento anterior al presionar el botón "Atrás"
                        .commit();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}