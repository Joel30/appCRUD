package com.example.usuarios8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuarios8.Collection.Item;
import com.example.usuarios8.Collection.ListAdapter;
import com.example.usuarios8.Collection.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        id = "?id=" + getIntent().getStringExtra("id");

        String name = getIntent().getStringExtra("name");
        editText = (EditText) findViewById(R.id.editTextUD);
        editText.setText(name);

        Button btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);
        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
    }
    private void updateData() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("name", editText.getText().toString());
        client.patch(Utils.SERVICE + id, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response.has("update")){
                    Toast.makeText(UpdateDeleteActivity.this, "Usuario Actualizado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateDeleteActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UpdateDeleteActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
            }
        });
    }
    private void deleteData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.delete(Utils.SERVICE + id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response.has("delete")){
                    Toast.makeText(UpdateDeleteActivity.this, "Usuario Eliminado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateDeleteActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UpdateDeleteActivity.this, "Error al Eliminar", Toast.LENGTH_SHORT).show();
                }
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                if (errorResponse.has("delete")){
                    Toast.makeText(UpdateDeleteActivity.this, "Usuario Eliminado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateDeleteActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UpdateDeleteActivity.this, "Error al Eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_update){
            updateData();
        }
        if (v.getId() == R.id.btn_delete){
            deleteData();
        }
    }
}
