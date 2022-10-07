package com.example.expendablerecycler;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;

public class ModelEkle extends AppCompatActivity {
    private ArrayAdapter<String> adapterCars;
    private ArrayList<String> carList;
    private SQLiteDatabase database;
    private String[] getTradeMark;

    ImageView imageView;
    Bitmap selectedImage;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;

    Spinner spinner;
    EditText editTextModelYear, editTextModelName, editTextPower,editTextYakıt,editTextVites;
    String selectedCarTradeMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_ekle);
        database = this.openOrCreateDatabase("Cars", MODE_PRIVATE, null);
        Cursor cursor2 = database.rawQuery("SELECT * FROM car_trademark ", null);

        spinner = findViewById(R.id.spinner2);
        editTextModelYear = findViewById(R.id.editTextModelYear);
        editTextModelName = findViewById(R.id.editTextModelName);
        editTextPower = findViewById(R.id.editTextPower);
        editTextYakıt = findViewById(R.id.editTextYakıt);
        editTextVites = findViewById(R.id.editTextVites);


        carList = new ArrayList<String>();

        while (cursor2.moveToNext()){
            carList.add(cursor2.getString(0) + "-" + cursor2.getString(1));

        }


        adapterCars = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carList);
        adapterCars.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCars);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCarTradeMark = (parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCarTradeMark = (parent.getItemAtPosition(0).toString());
            }
        });
    }

    public void addModelClick(View view){
        if (editTextModelYear.getText().toString().isEmpty() || editTextModelName.getText().toString().isEmpty() || editTextPower.getText().toString().isEmpty() || editTextModelYear.getText().toString().isEmpty() || editTextVites.getText().toString().isEmpty() ){
            Toast.makeText(ModelEkle.this,"Lütfen Boş Alan Bırakmayın!",Toast.LENGTH_LONG).show();

        }else{
            getTradeMark = selectedCarTradeMark.split("-");

            try {
                String tradeMark = (String) editTextModelYear.getText().toString();
                String sqlInsert = "INSERT INTO car_models (car_trademark_id, car_model, car_hp, model_year, fuel_type, gearbox_type) VALUES (?,?,?,?,?,?)";
                SQLiteStatement sqLiteStatement = database.compileStatement(sqlInsert);
                sqLiteStatement.bindString(1, getTradeMark[0]);
                sqLiteStatement.bindString(2, editTextModelName.getText().toString());
                sqLiteStatement.bindString(3, editTextPower.getText().toString());
                sqLiteStatement.bindString(4, editTextModelYear.getText().toString());
                sqLiteStatement.bindString(5, editTextYakıt.getText().toString());
                sqLiteStatement.bindString(6, editTextVites.getText().toString());

                sqLiteStatement.execute();
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        this.finish();



    }
}