package com.example.myapplication.ui.photo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class PhotoFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Button buttonOpenCamera;
    private ImageView imageViewPhoto;
    private CheckBox checkboxDecoration, checkboxView, checkboxBeauty, checkboxNutrition;
    private Button buttonShare;

    private Uri photoUri;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private FirebaseStorage firebaseStorage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo, container, false);

        buttonOpenCamera = rootView.findViewById(R.id.select);
        imageViewPhoto = rootView.findViewById(R.id.imgView);
        checkboxDecoration = rootView.findViewById(R.id.car);
        checkboxView = rootView.findViewById(R.id.View);
        checkboxBeauty = rootView.findViewById(R.id.Football);
        checkboxNutrition = rootView.findViewById(R.id.nature);
        buttonShare = rootView.findViewById(R.id.share);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        buttonOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePhoto();
            }
        });

        return rootView;
    }

    // Kamerayı açma intent'ini başlatan metot
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // Kamera ile çekilen fotoğrafın alındığı metot
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                // Kameradan alınan fotoğrafı ImageView'e yerleştir
                photoUri = saveImageToFirebase((Uri) extras.get("data"));
                imageViewPhoto.setImageURI(photoUri);
            }
        }
    }

    // Firebase Storage'a fotoğrafı kaydeden ve download URL döndüren metot
    private Uri saveImageToFirebase(Uri imageUri) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        String imageName = "photo_" + System.currentTimeMillis() + ".jpg";

        StorageReference storageReference = firebaseStorage.getReference()
                .child("user_photos")
                .child(userId)
                .child(imageName);

        storageReference.putFile(imageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Fotoğraf başarıyla yüklendi.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Fotoğraf yüklenirken bir hata oluştu.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return storageReference.getDownloadUrl().getResult();
    }

    // Firebase Firestore'a veri ekleyen metot
    private void sharePhoto() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        String selectedLabel = getSelectedLabel();

        if (photoUri != null && !selectedLabel.isEmpty()) {
            Map<String, Object> post = new HashMap<>();
            post.put("userId", userId);
            post.put("photoUrl", photoUri.toString());
            post.put("label", selectedLabel);

            firestore.collection("posts")
                    .add(post)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Fotoğraf başarıyla paylaşıldı.", Toast.LENGTH_SHORT).show();
                                // İstediğiniz başka bir işlemi yapabilirsiniz.
                            } else {
                                Toast.makeText(getActivity(), "Fotoğraf paylaşılırken bir hata oluştu.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Lütfen fotoğraf çekin ve bir etiket seçin.", Toast.LENGTH_SHORT).show();
        }
    }

    // Seçilen etiketi döndüren metot
    private String getSelectedLabel() {
        if (checkboxDecoration.isChecked()) {
            return "Dekorasyon";
        } else if (checkboxView.isChecked()) {
            return "Manzara";
        } else if (checkboxBeauty.isChecked()) {
            return "Güzellik";
        } else if (checkboxNutrition.isChecked()) {
            return "Beslenme";
        }
        return "";
    }
}
