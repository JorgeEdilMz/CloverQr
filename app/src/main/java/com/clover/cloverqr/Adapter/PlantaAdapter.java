package com.clover.cloverqr.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.clover.cloverqr.Model.Planta;
import com.clover.cloverqr.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PlantaAdapter extends RecyclerView.Adapter<PlantaAdapter.ViewHolder> {

    public Context mContext;
    public List<Planta> mProducto;

    private FirebaseUser firebaseUser;

    public PlantaAdapter (Context mContext, List<Planta> mProducto) {
        this.mContext = mContext;
        this.mProducto = mProducto;
    }


    @NonNull
    @Override
    public PlantaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.planta_item, viewGroup, false);
        return new PlantaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Planta planta = mProducto.get(position);

        Glide.with(mContext).load(planta.getPostimage()).into(viewHolder.post_image);

        if (planta.getDescripcion() == null || planta.getDescripcion().isEmpty()) {
            viewHolder.descripcion.setVisibility(View.GONE);
        } else {
            viewHolder.descripcion.setVisibility(View.VISIBLE);
            viewHolder.descripcion.setText(planta.getDescripcion());
        }

        if (planta.getNombre() == null || planta.getNombre().isEmpty()) {
            viewHolder.nombre.setVisibility(View.GONE);
        } else {
            viewHolder.nombre.setVisibility(View.VISIBLE);
            viewHolder.nombre.setText(planta.getNombre());

        }
    }


    @Override
    public int getItemCount() {
        return mProducto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile, post_image;
        public TextView username, likes, publisher, descripcion, nombre ;

        public ViewHolder (@NonNull View itemView){
            super(itemView);

            post_image = itemView.findViewById(R.id.post_image);
            likes = itemView.findViewById(R.id.likes);
            descripcion = itemView.findViewById(R.id.description);
            nombre = itemView.findViewById(R.id.nombre);
        }
    }

    private void getText (String postid, EditText editText){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Plantas");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editText.setText(dataSnapshot.getValue(Planta.class).getDescripcion());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}