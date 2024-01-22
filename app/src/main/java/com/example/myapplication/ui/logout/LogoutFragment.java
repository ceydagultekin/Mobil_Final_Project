package com.example.myapplication.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.Giris;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_logout, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        Button logoutButton = rootView.findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                Intent intent = new Intent(getActivity(), Giris.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return rootView;
    }
}
