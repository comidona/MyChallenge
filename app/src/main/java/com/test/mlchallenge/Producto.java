package com.test.mlchallenge;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Producto extends AppCompatActivity {
    private String produ_id;
    private ImageView foto;
    private TextView title,precio,desc;
    private Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto);
        Bundle bundle = getIntent().getExtras();




        if(bundle.getString("ID")!= null)
        {
            produ_id=bundle.getString("ID");
        }
        String url = "https://api.mercadolibre.com/items/"+produ_id;

        title = findViewById(R.id.textView3);
        foto =findViewById(R.id.imageView4);
        precio = findViewById(R.id.textView5);
        desc =findViewById(R.id.textView2);
        volver = findViewById(R.id.button2);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject dataOBJ = response;

                            title.setText(dataOBJ.getString("title"));
                            Picasso.get().load(dataOBJ.getString("thumbnail")).into(foto);
                            precio.setText("$ "+ dataOBJ.getString("price"));



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

}
}
