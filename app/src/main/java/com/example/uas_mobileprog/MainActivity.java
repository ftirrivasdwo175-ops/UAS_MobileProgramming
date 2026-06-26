package com.example.uas_mobileprog;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.uas_mobileprog.adapter.EndemikAdapter;
import com.example.uas_mobileprog.api.ApiService;
import com.example.uas_mobileprog.database.AppDatabase;
import com.example.uas_mobileprog.model.Endemik;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EndemikAdapter adapter;
    private List<Endemik> endemikList = new ArrayList<>();
    private AppDatabase db;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "endemik-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hendroprwk08.github.io/data_endemik/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new EndemikAdapter(endemikList, item -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("ITEM_ID", item.id);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_hewan) {
                    loadData("Hewan");
                    return true;
                } else if (id == R.id.nav_tumbuhan) {
                    loadData("Tumbuhan");
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.btn_profile).setOnClickListener(v -> 
            startActivity(new Intent(MainActivity.this, ProfileActivity.class)));

        findViewById(R.id.btn_search).setOnClickListener(v -> 
            startActivity(new Intent(MainActivity.this, SearchActivity.class)));

        findViewById(R.id.btn_favorite).setOnClickListener(v -> 
            startActivity(new Intent(MainActivity.this, FavoriteActivity.class)));

        fetchDataFromApi();
        Toast.makeText(this, "Sedang mengambil data...", Toast.LENGTH_SHORT).show();
    }

    private void fetchDataFromApi() {
        apiService.getEndemikList().enqueue(new Callback<List<Endemik>>() {
            @Override
            public void onResponse(Call<List<Endemik>> call, Response<List<Endemik>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    db.endemikDao().insertAll(response.body());
                    loadData("Hewan");
                } else {
                    Toast.makeText(MainActivity.this, "Gagal ambil data API", Toast.LENGTH_SHORT).show();
                    loadData("Hewan");
                }
            }

            @Override
            public void onFailure(Call<List<Endemik>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Koneksi Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                loadData("Hewan");
            }
        });
    }

    private void loadData(String kategori) {
        endemikList.clear();
        endemikList.addAll(db.endemikDao().getEndemikByKategori(kategori));
        adapter.notifyDataSetChanged();
    }
}
