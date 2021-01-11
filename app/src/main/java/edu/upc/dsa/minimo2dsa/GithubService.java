package edu.upc.dsa.minimo2dsa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {


    @GET("{nombreUser}")
    Call<User> getUser(@Path("nombreUser") String nombreUser);

    @GET("{nombreUser}/repos")
    Call<List<Repos>> getRepos(@Path("nombreUser") String nombreUser);
}
