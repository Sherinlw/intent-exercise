package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
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
    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUTYOU_KEY = "about";

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
        if (extras!=null){
            String fullname = extras.getString(FULLNAME_KEY);
            String email = extras.getString(EMAIL_KEY);
            String homepage = extras.getString(HOMEPAGE_KEY);
            String about = extras.getString(ABOUTYOU_KEY);

            Bundle extras2 = getIntent().getExtras();
            Bitmap bmp = (Bitmap) extras2.getParcelable("IMAGE_KEY");

            img.setImageBitmap(bmp );

            fullNameOutput.setText(fullname);
            emailOutput.setText(email);
            btnHomepage.setText(homepage);
            aboutOutput.setText(about);
        }

//        String AboutValue = extras.getString("about");
//        String fullnameValue = extras.getString("fullname");
//        String emailValue = extras.getString("email");
//        String HomePageValue = extras.getString("homepage");
//
//
//        if (extras != null) {
//            aboutOutput.setText(AboutValue);
//            fullNameOutput.setText(fullnameValue);
//            emailOutput.setText(emailValue);
//            homepageOutput.setText(HomePageValue);
//
//        }
    }

    public void handleHomePage(View view) {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String homepageText = bundle.getString(HOMEPAGE_KEY);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+homepageText));
            startActivity(intent);
        }
//        btnHomepage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "http://" + homepageOutput.getText().toString();
//                Intent Browser = new Intent(Intent.ACTION_VIEW);
//                Browser.setData(Uri.parse(url));
//                startActivity(Browser);
//            }
//        });
    }

}

