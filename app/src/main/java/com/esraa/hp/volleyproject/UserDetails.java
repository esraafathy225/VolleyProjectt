package com.esraa.hp.volleyproject;



public class UserDetails {
    private String name;
    private int likes;
    private String imageUrl;


    public UserDetails(String name, int likes, String imageUrl){
        this.name=name;
        this.likes=likes;
        this.imageUrl=imageUrl;
    }

    public int getLikes() {
        return likes;
    }

    public String getName() {
        return name;
    }
    public String getImageUrl(){
        return imageUrl;
    }
}
