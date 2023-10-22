package com.clover.cloverqr.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.clover.cloverqr.Fragment.PlantaDetailFragment;
import com.clover.cloverqr.Model.Planta;
import com.clover.cloverqr.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class PlantaDetailAdapter extends RecyclerView.Adapter<PlantaDetailAdapter.ViewHolder> {

    private Context mContext;
    private List<Planta> mPlantaList;
    private FirebaseUser firebaseUser;

    public PlantaDetailAdapter(Context context, List<Planta> plantaList) {
        mContext = context;
        mPlantaList = plantaList;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.plantadetail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Planta planta = mPlantaList.get(position);

        if (planta != null) {
            if (planta.getPostimage() != null && !planta.getPostimage().isEmpty()) {
                Glide.with(mContext).load(planta.getPostimage()).into(holder.postImage);
            }

            if (planta.getDescription() == null || planta.getDescription().isEmpty()) {
                holder.description.setVisibility(View.GONE);
            } else {
                holder.description.setVisibility(View.VISIBLE);
                holder.description.setText(planta.getDescription());
            }

            if (planta.getNombre() == null || planta.getNombre().isEmpty()) {
                holder.nombre.setVisibility(View.GONE);
            } else {
                holder.nombre.setVisibility(View.VISIBLE);
                holder.nombre.setText(planta.getNombre());
            }

        }
    }

    @Override
    public int getItemCount() {
        return mPlantaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView postImage;
        public TextView description, nombre;

        public ViewHolder(View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.post_image);
            description = itemView.findViewById(R.id.description);
            nombre = itemView.findViewById(R.id.nombre);
        }
    }
}