package com.example.uas_mobileprog.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.uas_mobileprog.model.Endemik;
import com.example.uas_mobileprog.model.Favorite;
import java.util.List;

@Dao
public interface EndemikDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Endemik> endemikList);

    @Query("SELECT * FROM endemik WHERE kategori = :kategori")
    List<Endemik> getEndemikByKategori(String kategori);

    @Query("SELECT * FROM endemik WHERE id = :id")
    Endemik getEndemikById(String id);

    @Query("SELECT * FROM favorit WHERE id = :id")
    Favorite getFavoriteById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Favorite favorite);

    @Query("SELECT * FROM favorit")
    List<Favorite> getAllFavorites();

    @Query("DELETE FROM favorit WHERE id = :id")
    void deleteFavoriteById(String id);
}
