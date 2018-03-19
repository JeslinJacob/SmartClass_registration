package com.example.blackmask.smartclass;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.blackmask.smartclass.Retro.APIClient;
import com.example.blackmask.smartclass.Retro.APIInterface;

import com.example.blackmask.smartclass.Retro.Regclass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationPage extends AppCompatActivity {
   FrameLayout select_image;
   android.app.AlertDialog dialog;
   CircleImageView profilepic;
   File actualImage;
   CheckBox agreebox;
   private AwesomeValidation awesomeValidation;

   String image,Name,Email,Phone,Address,Password;

   Bitmap bitmap;

   EditText nametxt,emailtxt,phonetxt,addrtxt,passtxt;


    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initializing the validation library
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //spots dialog box declairation
        dialog = new SpotsDialog(this,R.style.Custom);

        select_image = (FrameLayout) findViewById(R.id.frame_image_select);
        profilepic=(CircleImageView)findViewById(R.id.profile_image) ;
        nametxt=(EditText) findViewById(R.id.input_name);
        emailtxt=(EditText) findViewById(R.id.input_email);
        phonetxt=(EditText) findViewById(R.id.input_ph);
        addrtxt=(EditText) findViewById(R.id.input_address);
        passtxt=(EditText) findViewById(R.id.input_pass);
        agreebox=(CheckBox)findViewById(R.id.agreecheck);

        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.input_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.input_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.input_ph, "^[0-9]{10}$", R.string.mobileerror);



        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri path = data.getData();


            try {

                actualImage = FileUtil.from(this, data.getData());
                //putting the image from obtained path into a bitmap
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                //setting the bitmap on the image view
                profilepic.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(this, "error while selecting", Toast.LENGTH_SHORT).show();
        }


    }


    public void RegisterStudent(View v) {


        Animation a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up);
        v.startAnimation(a);

        Vibrator vibr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibr.vibrate(100);

        Name = nametxt.getText().toString().trim();
        Email = emailtxt.getText().toString().trim();
        Phone = phonetxt.getText().toString().trim();
        Address = addrtxt.getText().toString().trim();
        Password = passtxt.getText().toString().trim();


        if (Name.matches("") || Email.matches("")
                || Phone.matches("") || Address.matches("")
                || Password.matches("") ||!awesomeValidation.validate()|| bitmap == null||!agreebox.isChecked()) {
            Toast.makeText(this, "please fill up  the compleate form and also upload profile pic to complete registration", Toast.LENGTH_SHORT).show();
        } else {

//
//        String image = ImageToString();

            new AsyncTask<Void, Void, String>() {

                @Override
                protected String doInBackground(Void... voids) {

                    // using the compress library to compress huge images before sending them to the server

                    try {
                        bitmap = new Compressor(getApplicationContext())
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(75)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .compressToBitmap(actualImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("error when using lirary to compress image");
                    }

                    // finished using the copression library

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    //complressing the bitmap to a jpg file
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                    //inserting the bytearrayoutputstream into byte array
                    byte[] imagebyte = byteArrayOutputStream.toByteArray();

                    image = "" + Base64.encodeToString(imagebyte, Base64.DEFAULT);
                    return image;

                }

                @Override
                protected void onPreExecute() {
                    dialog.show();
                }

                @Override
                protected void onPostExecute(String s) {

                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                    Call<Regclass> call = apiInterface.uploadStudentDetails(Name, Email, Phone, Address, Password, image);

                    call.enqueue(new Callback<Regclass>() {
                        @Override
                        public void onResponse(Call<Regclass> call, Response<Regclass> response) {
                            dialog.dismiss();
                            Regclass reg = response.body();
                            System.out.println("$$$$$$$$$$$$$$$$ RESPONSE : " + reg.getResponse());
                            Toast.makeText(RegistrationPage.this, " " + reg.getResponse(), Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Regclass> call, Throwable t) {
                            dialog.dismiss();

                            Toast.makeText(RegistrationPage.this, "The web server is down", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }.execute();


        }
    }

}


