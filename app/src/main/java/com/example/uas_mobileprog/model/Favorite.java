package com.example.uas_mobileprog.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorit")
public class Favorite {
    @PrimaryKey
    @NonNull
    public String id;
    public String nama;
    public String deskripsi;
    public String gambar;
    public String kategori;
}
