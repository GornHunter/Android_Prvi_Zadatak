package com.example.mp3;

public class SingleRow {
    String singer;
    String song;
    int image;
    int mp3;

    SingleRow(String singer, String song, int image, int mp3){
        this.singer = singer;
        this.song = song;
        this.image = image;
        this.mp3 = mp3;
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

    public int getMP3() { return this.mp3; }
}
