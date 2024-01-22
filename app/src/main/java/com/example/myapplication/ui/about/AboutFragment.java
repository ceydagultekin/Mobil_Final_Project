import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class AboutFragment extends Fragment {

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        ImageView imageView = rootView.findViewById(R.id.photoImageView);
        imageView.setImageResource(R.drawable.cv);

        Button linkedinButton = rootView.findViewById(R.id.buttonlinkedin);
        linkedinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String linkedinProfileUrl = "https://www.linkedin.com/in/";
                Intent linkedinIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedinProfileUrl));
                startActivity(linkedinIntent);
            }
        });

        Button githubButton = rootView.findViewById(R.id.buttongithup);
        githubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String githubProfileUrl = "https://github.com/ceydagultekin";
                Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubProfileUrl));
                startActivity(githubIntent);
            }
        });

        return rootView;
    }
}