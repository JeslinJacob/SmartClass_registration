package com.example.blackmask.smartclass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Submit_Assignment extends AppCompatActivity {
    private static final int FILE_SELECT_CODE = 0;
    File actualFile;
    EditText ass_nametxt,teacherNameTxt,desctxt;
    String ass_name,teacherName,desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_assignment);

        ass_nametxt=(EditText)findViewById(R.id.file_name);
        teacherNameTxt=(EditText)findViewById(R.id.teacher_nametxt);
        desctxt=(EditText)findViewById(R.id.decrtxt);
    }


    public void showFileChooser(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {

                    // Get the Uri of the selected file
                    try {
                        actualFile = FileUtil.from(this, data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Uri uri = data.getData();
                    Log.d("FILE URI : ", "File Uri: " + uri.toString());
                    // Get the path
                    String path = null;
                    try {
                        path = FileUtil.getPath(this, uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    Log.d("MY FILE PATH ", "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void upload_assignment(View v)
    {
        ass_name=ass_nametxt.getText().toString().trim();
        teacherName=teacherNameTxt.getText().toString().trim();
        desc=desctxt.getText().toString();
    }


}
