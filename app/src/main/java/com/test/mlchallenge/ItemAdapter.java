package com.test.mlchallenge;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<ObtenerItems> list;

    public ItemAdapter(Context context, List<ObtenerItems> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ObtenerItems items = list.get(position);

        holder.textNombre.setText(items.getName());
        holder.textId.setText(String.valueOf(items.getId()));
        holder.textFoto.setText("$" + String.valueOf(items.getPrice()));
        Picasso.get().load(String.valueOf(items.getFoto())).into( holder.foto);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textNombre, textId, textFoto;
        public ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);

            textNombre = itemView.findViewById(R.id.main_nombre);
            textId = itemView.findViewById(R.id.main_id);
            textFoto = itemView.findViewById(R.id.main_foto);
            foto = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(v -> {
                Intent i = new Intent(context, Producto.class);
                String idkey  = textId.getText().toString();
                i.putExtra("ID", idkey );
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            });
        }
    }

}