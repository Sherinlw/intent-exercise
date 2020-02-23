package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView fullNameOutput, emailOutput, homepageOutput, aboutOutput;
    private ImageView img;
    private Button btnHomepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullNameOutput = findViewById(R.id.label_fullname);
        emailOutput = findViewById(R.id.label_email);
        homepageOutput = findViewById(R.id.label_homepage);
        aboutOutput = findViewById(R.id.label_about);
        img = findViewById(R.id.image_profile);
        btnHomepage = findViewById(R.id.button_homepage);

        Bundle extras = getIntent().getExtras();


        String AboutValue = extras.getString("about");
        String fullnameValue = extras.getString("fullname");
        String emailValue = extras.getString("email");
        String HomePageValue = extras.getString("homepage");


        if (extras != null) {
            aboutOutput.setText(AboutValue);
            fullNameOutput.setText(fullnameValue);
            emailOutput.setText(emailValue);
            homepageOutput.setText(HomePageValue);

        }
    }

    public void handleHomePage(View view) {
        btnHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://" + homepageOutput.getText().toString();
                Intent Browser = new Intent(Intent.ACTION_VIEW);
                Browser.setData(Uri.parse(url));
                startActivity(Browser);
            }
        });
    }

}

