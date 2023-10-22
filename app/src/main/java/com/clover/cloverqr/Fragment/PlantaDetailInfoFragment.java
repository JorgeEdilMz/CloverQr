package com.clover.cloverqr.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.clover.cloverqr.Model.Planta;
import com.clover.cloverqr.R;

import java.io.Serializable;

public class PlantaDetailInfoFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planta_detail_info, container, false);


        return view;
    }
}
