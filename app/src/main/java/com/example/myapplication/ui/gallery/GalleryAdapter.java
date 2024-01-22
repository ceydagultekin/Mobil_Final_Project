package com.example.myapplication.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<GalleryItem> galleryItems;
    private Context context;

    public GalleryAdapter(Context context, List<GalleryItem> galleryItems) {
        this.context = context;
        this.galleryItems = galleryItems;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        GalleryItem galleryItem = galleryItems.get(position);
        holder.bind(galleryItem);
    }

    @Override
    public int getItemCount() {
        return galleryItems.size();
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        private final TextView textUsername;
        private final TextView textLabel;
        private final ImageView imagePost;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.textUsername);
            textLabel = itemView.findViewById(R.id.textLabel);
            imagePost = itemView.findViewById(R.id.imagePost);
        }

        public void bind(GalleryItem galleryItem) {
            textUsername.setText(galleryItem.getUsername());
            textLabel.setText(galleryItem.getLabel());
            Picasso.get().load(galleryItem.getImageUrl()).into(imagePost);
        }
    }
}
