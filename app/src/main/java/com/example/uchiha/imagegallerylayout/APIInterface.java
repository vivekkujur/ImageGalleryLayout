package com.example.uchiha.imagegallerylayout;


import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("/ZS/json1/get_product_data.php?id_product=10000")
    Call<MultipleResources> getlist();

}
