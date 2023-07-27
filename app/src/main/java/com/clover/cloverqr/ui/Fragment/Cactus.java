package com.clover.cloverqr.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;

import com.clover.cloverqr.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.clover.cloverqr.databinding.ActivityMainBinding;

public class Cactus extends Fragment {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Configurar el Toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.appBarMain.toolbar);

        binding.appBarMain.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Configurar la navegación
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home , R.id.qsfragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(), navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        return inflater.inflate(R.layout.fragment_cactus, container, false);
    }
}
