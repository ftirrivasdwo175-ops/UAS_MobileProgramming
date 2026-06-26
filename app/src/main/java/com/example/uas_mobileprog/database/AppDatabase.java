package com.example.uas_mobileprog.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.uas_mobileprog.model.Endemik;
import com.example.uas_mobileprog.model.Favorite;

@Database(entities = {Endemik.class, Favorite.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EndemikDao endemikDao();
}
