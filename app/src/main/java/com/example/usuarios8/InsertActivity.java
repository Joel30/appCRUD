package com.example.usuarios8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuarios8.Collection.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Button btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
    }

    private void sendData() {
        TextView txt_name = (TextView) findViewById(R.id.ptxt_name);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("name", txt_name.getText().toString());
        client.post(Utils.SERVICE, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response.has("post")){
                    Toast.makeText(InsertActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(InsertActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send){
            sendData();
        }
    }
}
