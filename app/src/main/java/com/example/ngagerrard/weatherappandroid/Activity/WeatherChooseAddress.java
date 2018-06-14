package com.example.ngagerrard.weatherappandroid.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ngagerrard.weatherappandroid.Adapter.AdapterChooseAddress;
import com.example.ngagerrard.weatherappandroid.Model.Weather;
import com.example.ngagerrard.weatherappandroid.R;

import java.util.ArrayList;

public class WeatherChooseAddress extends AppCompatActivity {

    AutoCompleteTextView txtAddressSearch;
    ListView lvProvince;
    String[] arrayProvince;
    ArrayList<String> arrProvince = new ArrayList<String>();
    Button btnSearch;

    //Adapter cua Listview
    AdapterChooseAddress adapterProvince;

    //Adapter cua AutocompleteTextView
    ArrayAdapter<String> adapterAuCo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather_choose_address);
        addWidget();
        addEvent();
    }

    private void addWidget() {
        txtAddressSearch = (AutoCompleteTextView)findViewById(R.id.txtACSearchCity);
        btnSearch = (Button)findViewById(R.id.btnSearchCity);
        lvProvince = (ListView)findViewById(R.id.lvTinhThanh);
        arrayProvince = getResources().getStringArray(R.array.arrProvince);

        //adapter cho autocompleteTextView
        adapterAuCo = new ArrayAdapter<String>(WeatherChooseAddress.this, android.R.layout.simple_list_item_1, arrayProvince);
        txtAddressSearch.setAdapter(adapterAuCo);

        for(int i = 0; i < arrayProvince.length; i++){
            arrProvince.add(arrayProvince[i]);
        }

        //add Adapter cho listview cac tinh thanh
        adapterProvince = new AdapterChooseAddress(WeatherChooseAddress.this, R.layout.row_city, arrProvince);
        lvProvince.setAdapter(adapterProvince);
    }

    private void addEvent() {
        txtAddressSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Cai nay chua duoc nhu y :(((
                //adapterProvince.getFilter().filter(s);
                //adapterAuCo.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //su kien nhan nut tim kiem
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callIntent();
            }
        });

        //su kien chon mot item trong listview
        lvProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtAddressSearch.setText(arrProvince.get(position));
                callIntent();
            }
        });
    }
    private void callIntent(){
        Intent intent = new Intent(WeatherChooseAddress.this, WeatherByAddressActivity.class);
        //truyen dia chi sang activity WeatherByAddressActivity de xu ly
        intent.putExtra("ADDRESS", txtAddressSearch.getText().toString());
        startActivity(intent);
    }

}
