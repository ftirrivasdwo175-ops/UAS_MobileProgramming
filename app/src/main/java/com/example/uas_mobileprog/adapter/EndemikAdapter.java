package com.example.uas_mobileprog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.uas_mobileprog.R;
import com.example.uas_mobileprog.model.Endemik;
import java.util.List;

public class EndemikAdapter extends RecyclerView.Adapter<EndemikAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Endemik item);
    }

    private List<Endemik> list;
    private OnItemClickListener listener;

    public EndemikAdapter(List<Endemik> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_endemik, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Endemik item = list.get(position);
        holder.tvNama.setText(item.nama);
        Glide.with(holder.itemView.getContext())
                .load(item.gambar)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.ivGambar);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGambar;
        TextView tvNama;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGambar = itemView.findViewById(R.id.iv_item_gambar);
            tvNama = itemView.findViewById(R.id.tv_item_nama);
        }
    }
}
