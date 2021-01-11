package edu.upc.dsa.minimo2dsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoriosActivity extends AppCompatActivity {


    TextView nomT;
    TextView followersT;
    TextView followingT;
    ImageView imagenT;

    List<Repos> reposList;


    Retrofit retrofit;
    User user;
    GithubService gitservice;

    //Para crear el recycler
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositorios);
        spinner = findViewById(R.id.progressBar);
        //Esto es configuracion del recycler
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        user = (User) getIntent().getParcelableExtra("myUser");
        nomT =  (TextView)findViewById(R.id.NomText);
        followersT =  (TextView)findViewById(R.id.followersText);
        followingT = (TextView)findViewById(R.id.followingText);
        imagenT = (ImageView) findViewById(R.id.imagen);

        nomT.setText(user.getLogin());
        followersT.setText(String.valueOf(user.getFollowers()));
        followingT.setText(String.valueOf(user.getFollowing()));
        Picasso.get().load(user.getAvatarUrl()).into(imagenT);



        //Configuracion del retrofit
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/")
                // .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        gitservice = retrofit.create(GithubService.class);
        getRepos();



    }

    private void getRepos()
    {
        try{
            spinner.setVisibility(View.VISIBLE);
            Call<List<Repos>> repositorios = gitservice.getRepos(user.getLogin());
            NotifyUser("Server nombre" + user.getLogin() + "nombre");
            repositorios.enqueue(new Callback<List<Repos>>() {
                @Override
                public void onResponse(Call<List<Repos>> call, Response<List<Repos>> response) {


                    if (response.isSuccessful()) {
                        spinner.setVisibility(View.GONE);
                        reposList = response.body();
                        //spinner.setVisibility(View.GONE);

                        // non empty response, Mapping Json via Gson...
                        NotifyUser("Server Response Ok" + reposList.get(0).getLanguage());
                        mAdapter = new MyAdapter(reposList);
                        recyclerView.setAdapter(mAdapter);
                    }
                    else{
                        NotifyUser("Request failed");
                    }
                }

                @Override
                public void onFailure(Call<List<Repos>> call, Throwable t) {
                    NotifyUser("Request failed");

                }
            });


        }catch(Exception e){
            NotifyUser("Exception: " + e.toString());
        }

    }
    public void onButtonCerrarClick(View view) {
    }





    private void NotifyUser(String MSG){
        Toast toast = Toast.makeText(RepositoriosActivity.this,MSG,Toast.LENGTH_SHORT);
        toast.show();
    }
}