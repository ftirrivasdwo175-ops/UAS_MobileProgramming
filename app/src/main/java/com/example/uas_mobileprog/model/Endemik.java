package com.example.uas_mobileprog.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "endemik")
public class Endemik {
    @PrimaryKey
    @NonNull
    public String id;
    
    @SerializedName("tipe")
    public String kategori; // Di JSON "tipe"
    
    public String nama;
    public String deskripsi;
    
    @SerializedName("foto")
    public String gambar; // Di JSON "foto"
}
