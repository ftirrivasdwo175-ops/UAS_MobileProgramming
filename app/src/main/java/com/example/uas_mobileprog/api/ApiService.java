package com.example.uas_mobileprog.api;

import com.example.uas_mobileprog.model.Endemik;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("endemik.json")
    Call<List<Endemik>> getEndemikList();
}
