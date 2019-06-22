package com.example.usuarios8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.usuarios8.Collection.Item;
import com.example.usuarios8.Collection.ListAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ArrayList<Item> list_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadComponents();

    }

    private void loadComponents() {
        Button button = findViewById(R.id.btn_user);
        button.setOnClickListener(this);
        final ListView list = (ListView) this.findViewById(R.id.listView);
        list.setOnItemClickListener(this);
        list_data = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://192.168.0.14:8000/users", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("usuario");
                    for (int i = 0; i < data.length(); i++){
                        Item p = new Item();
                        JSONObject obj = data.getJSONObject(i);
                        p.id = i;
                        p.name = " " + obj.getString("name");
                        list_data.add(p);
                    }
                    ListAdapter adapter = new ListAdapter(MainActivity.this, list_data);
                    list.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_user){
            Intent intent = new Intent(this, InsertActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, UpdateDeleteActivity.class);
        intent.putExtra("id", list_data.get(position).getId());
        startActivity(intent);
    }
}
