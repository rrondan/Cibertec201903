package com.rrondan.cibertec201903;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ClientApi {
    @GET("posts")
    Call<List<Post>> getPosts();
}
