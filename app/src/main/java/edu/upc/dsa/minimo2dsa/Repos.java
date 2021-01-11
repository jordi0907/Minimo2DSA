package edu.upc.dsa.minimo2dsa;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Repos {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("language")
    @Expose
    private String language;

    public Repos(String name, String language) {
        this.name = name;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguaje(String language) {
        this.language = language;
    }

    public Repos() {
    }


    @Override
    public String toString() {
        return "Repos{" +
                "name='" + name + '\'' +
                ", languaje='" + language + '\'' +
                '}';
    }
}
