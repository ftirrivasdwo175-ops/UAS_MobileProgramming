package com.example.uas_mobileprog;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.uas_mobileprog.adapter.EndemikAdapter;
import com.example.uas_mobileprog.database.AppDatabase;
import com.example.uas_mobileprog.model.Endemik;
import com.example.uas_mobileprog.model.Favorite;
import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EndemikAdapter adapter;
    private List<Endemik> favoriteList = new ArrayList<>();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "endemik-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        recyclerView = findViewById(R.id.rv_favorite);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        
        adapter = new EndemikAdapter(favoriteList, item -> {
            Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
            intent.putExtra("ITEM_ID", item.id);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btn_back_fav).setOnClickListener(v -> finish());

        loadFavorites();
    }

    private void loadFavorites() {
        favoriteList.clear();
        List<Favorite> favorites = db.endemikDao().getAllFavorites();
        for (Favorite f : favorites) {
            Endemik e = new Endemik();
            e.id = f.id;
            e.nama = f.nama;
            e.deskripsi = f.deskripsi;
            e.gambar = f.gambar;
            e.kategori = f.kategori;
            favoriteList.add(e);
        }
        adapter.notifyDataSetChanged();
    }
}
