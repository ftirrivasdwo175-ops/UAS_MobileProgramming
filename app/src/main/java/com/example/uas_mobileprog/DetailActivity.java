package com.example.uas_mobileprog;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.bumptech.glide.Glide;
import com.example.uas_mobileprog.database.AppDatabase;
import com.example.uas_mobileprog.model.Endemik;
import com.example.uas_mobileprog.model.Favorite;

public class DetailActivity extends AppCompatActivity {
    private boolean isFavorite = false;
    private AppDatabase db;
    private Endemik item;
    private ImageView btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String itemId = getIntent().getStringExtra("ITEM_ID");
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "endemik-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        item = db.endemikDao().getEndemikById(itemId);
        
        if (item != null) {
            TextView tvToolbarTitle = findViewById(R.id.tv_detail_toolbar_title);
            TextView tvName = findViewById(R.id.tv_detail_name);
            TextView tvDesc = findViewById(R.id.tv_detail_desc);
            ImageView ivImage = findViewById(R.id.iv_detail_image);
            btnFavorite = findViewById(R.id.btn_favorite_detail);

            tvToolbarTitle.setText(item.nama);
            tvName.setText(item.nama);
            tvDesc.setText(item.deskripsi);
            Glide.with(this).load(item.gambar).into(ivImage);

            checkFavoriteStatus();

            btnFavorite.setOnClickListener(v -> toggleFavorite());
        }

        findViewById(R.id.btn_back_detail).setOnClickListener(v -> finish());
    }

    private void checkFavoriteStatus() {
        Favorite fav = db.endemikDao().getFavoriteById(item.id);
        if (fav != null) {
            isFavorite = true;
            btnFavorite.setImageResource(R.drawable.ic_heart); // Filled
        } else {
            isFavorite = false;
            btnFavorite.setImageResource(R.drawable.ic_heart_outline); // Outline
        }
    }

    private void toggleFavorite() {
        if (isFavorite) {
            db.endemikDao().deleteFavoriteById(item.id);
            isFavorite = false;
            btnFavorite.setImageResource(R.drawable.ic_heart_outline);
            Toast.makeText(this, "Dihapus dari favorit", Toast.LENGTH_SHORT).show();
        } else {
            Favorite fav = new Favorite();
            fav.id = item.id;
            fav.nama = item.nama;
            fav.deskripsi = item.deskripsi;
            fav.gambar = item.gambar;
            fav.kategori = item.kategori;
            db.endemikDao().insertFavorite(fav);
            isFavorite = true;
            btnFavorite.setImageResource(R.drawable.ic_heart);
            
            Toast.makeText(this, "Berhasil ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
        }
    }
}
