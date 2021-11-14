package com.example.ui_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ShowWordListActivity extends AppCompatActivity {
    TextView textView;
    ListView listExcel;
    Button button;
    TextView definition;

    ArrayAdapter<String> arrayAdapter;
    CustomWordAdapter customWordAdapter;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_word_list);



        customWordAdapter=new CustomWordAdapter();

        textView=findViewById(R.id.textView);

        button=findViewById(R.id.btn);

        definition=findViewById(R.id.bottomText);

        


        Intent it3= this.getIntent();
        String var= it3.getStringExtra("it_listData");

        textView.setText(var);
        //------------------------------------------------
        listExcel=findViewById(R.id.wordExcel);

        //-----------------------------------------------

        Excel(var);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                
            }
        });



        //CustomAdapter customAdapter= new CustomAdapter(this,R.layout.custom_word_list,wordList);
        //listExcel.setAdapter(customAdapter);



    }
    public void Excel(String var) {
        Workbook workBook = null;
        Sheet sheet=null;
        try{
            InputStream is = getBaseContext().getResources().getAssets().open(var+".xls");
            workBook= Workbook.getWorkbook(is);
            sheet= workBook.getSheet(0);

            int maxColumn=2,rowStart=0,rowEnd=sheet.getColumn(maxColumn-1).length-1,
                    columStart=0,columnEnd=sheet.getRow(2).length-1;

            String excelLoad="";
            for(int row=rowStart+1;row<=rowEnd;row++){
                customWordAdapter.addItem(new Word(sheet.getCell(columStart,row).getContents(),sheet.getCell(columStart+1,row).getContents()));
                //arrayAdapter.add(excelLoad);
                //wordList.add(new Word(sheet.getCell(columStart,row).getContents(),sheet.getCell(columStart+1,row).getContents()));

            }


        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
        finally {
            listExcel.setAdapter(customWordAdapter);
            workBook.close();
        }

    }







}