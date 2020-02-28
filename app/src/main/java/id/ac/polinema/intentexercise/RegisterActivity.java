package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {


    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String CPASS_KEY = "confirmpassword";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUT_KEY = "about";


    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;
    private EditText fullNameInput, emailInput, passwordInput, confirmPassInput, homepageInput, aboutInput;
    private ImageView imageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageProfile = findViewById(R.id.image_profile);
        fullNameInput = findViewById(R.id.text_fullname);
        emailInput = findViewById(R.id.text_email);
        passwordInput = findViewById(R.id.text_password);
        confirmPassInput = findViewById(R.id.text_confirm_password);
        homepageInput = findViewById(R.id.text_homepage);
        aboutInput = findViewById(R.id.text_about);

        //imageView = findViewById(R.id.imageView);

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectImage(RegisterActivity.this);
//            }
//        });
    }

//    private void selectImage(Context context) {
//        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Choose your profile picture");
//
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (options[item].equals("Take Photo")) {
//                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePicture, 0);
//
//                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, GALLERY_REQUEST_CODE);
//
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageProfile.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageProfile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    imageProfile.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleChangeImage(View view) {
        selectImage(RegisterActivity.this);
    }

    public void handleRegister(View view) {
        String fullname = fullNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String pass = passwordInput.getText().toString();
        String cpass = confirmPassInput.getText().toString();
        String homepage = homepageInput.getText().toString();
        String about = aboutInput.getText().toString();

        if (fullname.equals("")) {
            fullNameInput.setError("Isi nama");
        } else if (email.equals("")) {
            emailInput.setError("Isi email!");
        } else if (pass.matches("")) {
            passwordInput.setError("Isi password!");
        } else if (pass.equals("")) {
            confirmPassInput.setError("Isi confirm password!");
        } else if (!pass.equals(cpass)) {
            confirmPassInput.setError("Password harus sama!");
        } else if (homepage.equals("")) {
            homepageInput.setError("Isi homepage!");
        } else if (about.equals("")) {
            aboutInput.setError("Isi about!");
        } else {
            Intent intent = new Intent(this, ProfileActivity.class);
            imageProfile.buildDrawingCache();
            Bitmap image = imageProfile.getDrawingCache();
            Bundle extras = new Bundle();
            extras.putParcelable("IMAGE_KEY", image);
            intent.putExtras(extras);

            intent.putExtra(FULLNAME_KEY, fullname);
            intent.putExtra(EMAIL_KEY, email);
            intent.putExtra(HOMEPAGE_KEY, homepage);
            intent.putExtra(ABOUT_KEY, about);

            startActivity(intent);
        }
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
