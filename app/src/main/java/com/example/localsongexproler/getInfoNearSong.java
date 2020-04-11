package com.example.localsongexproler;

import java.util.List;

public class getInfoNearSong {

    private LocationSongData song;

    public getInfoNearSong(List<LocationSongData> songDataArrayList, String songID){
        for(LocationSongData i : songDataArrayList){
            if(i.getId().equals(songID)) { song = i; }
        }
    }

    public LocationSongData getSong() {
        return song;
    }

}
