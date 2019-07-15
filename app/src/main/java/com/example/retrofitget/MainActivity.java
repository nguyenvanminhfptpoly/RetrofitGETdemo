package com.example.retrofitget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    private JsonPlaceHodelAPI jsonPlaceHodelAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         jsonPlaceHodelAPI = retrofit.create(JsonPlaceHodelAPI.class);

         //GetPosts();
       // GetComment();
        CreatePost();

    }

    private void CreatePost() {
        Post post = new Post(23, "new title", "new title");

        Map<String,String> map = new HashMap<>();
        map.put("userId", "25");
        map.put("title", "26");
        Call<Post> postCall = jsonPlaceHodelAPI.createPost( map);

        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textView.setText("COde" + response.code());
                    return;
                }

                Post post1 = response.body();
                String content="";
                content += "Code: " + response.code() + "\n";
                content += "ID" + post1.getId() +"\n";
                content += "User Id" + post1.getUserId() +"\n";
                content += "title" + post1.getTitle() +"\n";
                content += "Text: "+ post1.getBody() + "\n\n";

                textView.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void GetComment(){
        Call<List<Comment>> listCall = jsonPlaceHodelAPI.getComment();

        listCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textView.setText("COde" + response.code());
                    return;
                }
                List<Comment> posts = response.body();
                for (Comment post: posts){
                    String content="";
                    content += "Post Id: " + post.getPostId() +"\n";
                    content += "Id: " + post.getId() +"\n";
                    content += "Name: " + post.getName() +"\n";
                    content += "Email: " + post.getEmail() +"\n";
                    content += "Text: "+ post.getBody() + "\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }
    public  void  GetPosts(){
        Call<List<Post>> call = jsonPlaceHodelAPI.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textView.setText("Code:" + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for (Post post: posts){
                    String content="";
                    content += "ID" + post.getId() +"\n";
                    content += "User Id" + post.getUserId() +"\n";
                    content += "title" + post.getTitle() +"\n";
                    content += "Text: "+ post.getBody() + "\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}
