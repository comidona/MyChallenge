package com.test.mlchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ObtenerItems> listaproductos;
    private RecyclerView.Adapter adapter;
    private EditText consulta;
    private Button buscar;
    private SeekBar cantidad;
    private TextView cant;
    private Switch nuevo , gratis;


    boolean nuevoq;
    boolean gratisq;

    // URL DE LA API, BUSCANDO EN MLA Y PUBLICACIONES ACTIVAS PARA OBTENER IMAGENES
    private String url = "https://api.mercadolibre.com/sites/MLA/search?limit=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = findViewById(R.id.main_list);
        consulta = findViewById(R.id.busca);
        buscar = findViewById(R.id.button);
        cantidad = findViewById(R.id.seekBar2);
        cant =  findViewById(R.id.textView7);
        nuevo =findViewById(R.id.switch1);
        gratis =findViewById(R.id.switch2);




        final Integer[] x = {0};


        listaproductos = new ArrayList<>();
        adapter = new ItemAdapter(getApplicationContext(),listaproductos);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        //cierra el keyboard virtual para la busqueda
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);



        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);




        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                getData(consulta.getEditableText().toString().trim());


            }
        });


        cantidad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                cant.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        nuevo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                 nuevoq = b;

            }
        });
        gratis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                gratisq = b;

            }
        });


    }
    private void getData(String q) {
        listaproductos.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();


        String consulta=String.valueOf(cantidad.getProgress())+"&q="+q;
        if(gratisq){
            consulta+="&shipping_cost=free";
        }
        if(nuevoq){
            consulta+="&condition=new";
        }




        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url+consulta,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                                JSONArray dataOBJ = response.getJSONArray("results");

                                for (int k = 0; k < dataOBJ.length(); k++) {
                                    ObtenerItems items = new ObtenerItems();
                                    JSONObject obj =  dataOBJ.getJSONObject(k);
                                    items.setName(obj.getString("title"));
                                    items.setPrice(obj.getInt("price"));
                                    items.setId(obj.getString("id"));
                                    items.setFoto(obj.getString("thumbnail"));

                                    listaproductos.add(items);

                                }

                        } catch (Exception exp) {
                            exp.printStackTrace();
                            progressDialog.dismiss();
                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}