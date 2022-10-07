package com.example.expendablerecycler;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MarkaEkle extends AppCompatActivity {

    Button addNew;
    TextView editTextTradeMark;
    ImageView imageView;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marka_ekle);
        database = this.openOrCreateDatabase("Cars", MODE_PRIVATE, null);

        addNew = findViewById(R.id.addNew);
        editTextTradeMark = findViewById(R.id.editTextTradeMark);
        //imageView=findViewById(R.id.imageView);
    }
    public void addNewClick(View view){
        if (editTextTradeMark.getText().toString().isEmpty()){
            Toast.makeText(MarkaEkle.this,"Lütfen Boş Alan Bırakmayın!",Toast.LENGTH_LONG).show();

        }else{
            try {
                String tradeMark = (String) editTextTradeMark.getText().toString();
                String sqlInsert = "INSERT INTO car_trademark (car_name) VALUES (?)";
                SQLiteStatement sqLiteStatement = database.compileStatement(sqlInsert);
                sqLiteStatement.bindString(1, tradeMark);
                sqLiteStatement.execute();

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MarkaEkle.this,"Veri Eklerken Hata Oluştu!",Toast.LENGTH_LONG).show();


            }
        }



        this.finish();
    }
}