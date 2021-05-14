package com.example.mp3;

public class SingleRow {
    String singer;
    String song;
    int image;

    SingleRow(String singer, String song, int image){
        this.singer = singer;
        this.song = song;
        this.image = image;
    }

    public String getSinger(){
        return this.singer;
    }

    public String getSong(){
        return this.song;
    }

    public int getImage(){
        return this.image;
    }
}
