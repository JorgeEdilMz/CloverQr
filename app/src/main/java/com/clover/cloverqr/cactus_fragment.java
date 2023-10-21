package com.clover.cloverqr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clover.cloverqr.Model.Planta;
import com.clover.cloverqr.Adapter.PlantaAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cactus_fragment extends Fragment {

    private RecyclerView recyclerView;
    private PlantaAdapter plantaAdapter;
    private List<Planta> plantaList;
    private List<String> followingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cactus_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_cactus);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        plantaList = new ArrayList<>();
        plantaAdapter = new PlantaAdapter(getContext(), plantaList);
        recyclerView.setAdapter(plantaAdapter);


        readPosts();

        ImageView arrowNews = view.findViewById(R.id.ArrowNews);
        arrowNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void readPosts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Plantas");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                plantaList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Planta planta = snapshot.getValue(Planta.class);
                    if (planta != null && planta.getPublisher() != null) {
                        plantaList.add(planta);
                    }
                }
                plantaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores en caso de que ocurra algún problema con la lectura de datos
            }
        });
    }
}
