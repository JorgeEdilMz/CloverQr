package com.clover.cloverqr.Adapter;

import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.clover.cloverqr.Fragment.PlantaDetailFragment;
import com.clover.cloverqr.Fragment.PlantaDetailInfoFragment;
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
    private int currentState = 0;
    private Fragment previousFragment;
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

        if (planta != null) {
            // Verifica que la imagen no sea nula
            if (planta.getPostimage() != null && !planta.getPostimage().isEmpty()) {
                Glide.with(mContext).load(planta.getPostimage()).into(viewHolder.post_image);
            }

            if (planta.getDescription() == null || planta.getDescription().isEmpty()) {
                viewHolder.description.setVisibility(View.GONE);
            } else {
                viewHolder.description.setVisibility(View.VISIBLE);
                viewHolder.description.setText(planta.getDescription());
            }

            if (planta.getNombre() == null || planta.getNombre().isEmpty()) {
                viewHolder.nombre.setVisibility(View.GONE);
            } else {
                viewHolder.nombre.setVisibility(View.VISIBLE);
                viewHolder.nombre.setText(planta.getNombre());
            }
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cuando se hace clic en un elemento de la lista, muestra la vista expandida
                showExpandedView(planta);
            }
        });
    }

    private void showExpandedView(Planta planta) {
        // Crea un diálogo personalizado o un Fragment que muestre la vista expandida
        // Puedes usar un AlertDialog personalizado o crear un nuevo Fragment para mostrar la vista expandida
        // Pasa los datos del cactus (nombre, descripción, imagen) a la vista expandida

        // Por ejemplo, puedes usar un AlertDialog personalizado
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.plantadetail_item, null);

        // Configura la vista expandida con los datos del cactus
        ImageView expandedImage = dialogView.findViewById(R.id.post_image);
        TextView expandedName = dialogView.findViewById(R.id.nombre);
        TextView expandedDescription = dialogView.findViewById(R.id.description);

        // Carga la imagen y los datos del cactus
        Glide.with(mContext).load(planta.getPostimage()).into(expandedImage);
        expandedName.setText(planta.getNombre());
        expandedDescription.setText(planta.getDescription());

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    @Override
    public int getItemCount() {
        return mProducto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile, post_image;
        public TextView username, likes, publisher, description, nombre ;

        public ViewHolder (@NonNull View itemView){
            super(itemView);

            post_image = itemView.findViewById(R.id.post_image);
            likes = itemView.findViewById(R.id.likes);
            description = itemView.findViewById(R.id.description);
            nombre = itemView.findViewById(R.id.nombre);
        }
    }

    private void getText (String postid, EditText editText){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Plantas");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editText.setText(dataSnapshot.getValue(Planta.class).getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}