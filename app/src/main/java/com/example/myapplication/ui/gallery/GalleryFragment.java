package com.example.myapplication.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;

    private FirebaseFirestore firestore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        galleryAdapter = new GalleryAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(galleryAdapter);

        loadPosts();

        return rootView;
    }

    private void loadPosts() {
        firestore.collection("posts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<GalleryItem> galleryItems = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String username = document.getString("userId");
                            String label = document.getString("label");
                            String imageUrl = document.getString("photoUrl");
                            galleryItems.add(new GalleryItem(username, label, imageUrl));
                        }
                        galleryAdapter.setGalleryItems(galleryItems);
                    }
                });
    }
}