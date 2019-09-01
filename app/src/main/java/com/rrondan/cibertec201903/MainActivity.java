package com.rrondan.cibertec201903;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = findViewById(R.id.textViewResult);
        recyclerViewPosts = findViewById(R.id.recyclerViewPosts);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList);
        recyclerViewPosts.setAdapter(postAdapter);
        callService();
    }
    private void callService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientApi jsonPlaceHolderApi = retrofit.create(ClientApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                } else {
                    List<Post> posts = response.body();
                    postList.addAll(posts);
                    postAdapter.notifyDataSetChanged();
                    /*for (Post post: posts) {
                        String content = "";
                        content += "Id: " + post.getId() + "\n";
                        content += "userId: " + post.getUserId() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Body: " + post.getText() + "\n\n";
                        textViewResult.append(content);
                    }*/
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
