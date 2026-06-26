package com.example.uas_mobileprog;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.uas_mobileprog.adapter.EndemikAdapter;
import com.example.uas_mobileprog.database.AppDatabase;
import com.example.uas_mobileprog.model.Endemik;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EndemikAdapter adapter;
    private List<Endemik> allList = new ArrayList<>();
    private List<Endemik> filteredList = new ArrayList<>();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "endemik-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        // Load all data for searching
        allList.addAll(db.endemikDao().getEndemikByKategori("Hewan"));
        allList.addAll(db.endemikDao().getEndemikByKategori("Tumbuhan"));
        filteredList.addAll(allList);

        recyclerView = findViewById(R.id.rv_search_results);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new EndemikAdapter(filteredList, item -> {
            Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
            intent.putExtra("ITEM_ID", item.id);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        EditText etSearch = findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filter(String query) {
        filteredList.clear();
        for (Endemik item : allList) {
            if (item.nama.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
