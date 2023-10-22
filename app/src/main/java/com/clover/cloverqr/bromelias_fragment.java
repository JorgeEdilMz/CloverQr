package com.clover.cloverqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.clover.cloverqr.Adapter.PlantaAdapter;
import com.clover.cloverqr.Model.Planta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class bromelias_fragment extends Fragment {

    private RecyclerView recyclerView;
    private PlantaAdapter postAdapter;
    private List<Planta> postList;
    EditText search_bar;
    private String category = "Bromelias"; // Categoría correspondiente

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bromelias_fragment, container, false);

        Button arrowNewsButton = view.findViewById(R.id.ArrowNews);

        search_bar = view.findViewById(R.id.search_bar);

        recyclerView = view.findViewById(R.id.recycler_view_bromelias);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postList = new ArrayList<>();
        postAdapter = new PlantaAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);

        readPosts();

        arrowNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene el FragmentManager de la actividad principal
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                // Realiza la acción "back press" para volver al fragmento anterior
                fragmentManager.popBackStack();
            }
        });
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchPlantas(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }



    private void readPosts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Plantas");
        Query query = reference.orderByChild("category").equalTo(category);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Planta post = snapshot.getValue(Planta.class);
                    postList.add(post);
                }
                postAdapter.notifyDataSetChanged();

                Log.d("CactusCategories", "readPosts: Data retrieved successfully. Post count: " + postList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CactusCategories", "readPosts: Error retrieving data from Firebase: " + error.getMessage());
            }
        });
    }
    private void searchPlantas(String s) {
        String searchQuery = s.toLowerCase();
        Query query = FirebaseDatabase.getInstance().getReference("Plantas")
                .orderByChild("nombre")
                .startAfter(s)
                .endAt(searchQuery + "\uf8ff");

        Query categoryQuery = FirebaseDatabase.getInstance().getReference("Plantas")
                .orderByChild("category")
                .equalTo(category);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Planta> filteredList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Planta planta = snapshot.getValue(Planta.class);
                    if (planta.getCategory().equals(category)) {


                        filteredList.add(planta);
                    }
                }
                postList.clear();
                postList.addAll(filteredList);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Maneja errores, si es necesario
            }
        });
    }
}
