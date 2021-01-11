package edu.upc.dsa.minimo2dsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //crear retrofit
    Retrofit retrofit;


    GithubService gitservice;
    Button btnAceptar;
    TextView nombreUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreUser = (TextView) findViewById(R.id.Name);


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

        btnAceptar = findViewById(R.id.button);

       if (existSharedPreferences()){
           SharedPreferences settings  = getSharedPreferences("UserInfo", 0);
           String user = settings.getString("user","");
           nombreUser.setText(user);
           NotifyUser("Existe Shared Preferences");
        }
        else{
           NotifyUser("No Existe Shared Preferences");
       }

    }

    public void onButtonLoginClick(View view) {
        try{
        Call<User> callUsers = gitservice.getUser(nombreUser.getText().toString());
            callUsers.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {


                if (response.isSuccessful()) {
                    User usuario = response.body();
                   // spinner.setVisibility(View.GONE);
                    //elementList = museums.getElements();
                    // non empty response, Mapping Json via Gson...
                    NotifyUser("Server Response Ok");
                    getPrefer(usuario.getLogin());

                    Intent intent = new Intent(MainActivity.this, RepositoriosActivity.class);
                    intent.putExtra("myUser", usuario);
                    startActivityForResult(intent,1);
                }
                else{
                    NotifyUser("Request failed");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                NotifyUser("Request failed");

            }
        });


    }catch(Exception e){
        NotifyUser("Exception: " + e.toString());
    }

    }



    private void NotifyUser(String MSG){
        Toast toast = Toast.makeText(MainActivity.this,MSG,Toast.LENGTH_SHORT);
        toast.show();
    }

    private void getPrefer(String login){
        SharedPreferences settings  = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("user", login);
        editor.commit();
    }


    private boolean existSharedPreferences() {
        SharedPreferences settings  = getSharedPreferences("UserInfo", 0);
        String user = settings.getString("user","");

        return !user.equals("");
    }



}