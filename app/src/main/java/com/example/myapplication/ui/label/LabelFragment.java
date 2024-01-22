package com.example.myapplication.ui.label;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class LabelFragment extends Fragment {

    private EditText editTextLabel;
    private Button buttonAddLabel;
    private LinearLayout linearLayoutLabels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_label, container, false);

        editTextLabel = rootView.findViewById(R.id.add_label_text);
        buttonAddLabel = rootView.findViewById(R.id.added);
        linearLayoutLabels = rootView.findViewById(R.id.linearLayout);

        buttonAddLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLabelAndCheckboxes();
            }
        });

        return rootView;
    }

    private void addLabelAndCheckboxes() {
        String label = editTextLabel.getText().toString().trim();

        if (!label.isEmpty()) {
            // Yeni bir TextView oluştur ve içeriğini kullanıcının girdiği label olarak ayarla
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(label);

            // Checkbox durumuna göre etiketi ekleyip etmeyeceğinizi kontrol edin
            linearLayoutLabels.addView(checkBox);

            // İstediğiniz başka bir işlemi yapabilirsiniz.
            Toast.makeText(getActivity(), "Etiket ve Checkbox başarıyla eklendi.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Lütfen bir etiket girin.", Toast.LENGTH_SHORT).show();
        }
    }
}