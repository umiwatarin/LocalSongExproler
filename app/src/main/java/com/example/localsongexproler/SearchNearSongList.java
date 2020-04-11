package com.example.localsongexproler;

import java.util.ArrayList;
import java.util.List;

public class SearchNearSongList {
    private ArrayList<LocationSongData> ansList = null;

    public ArrayList<LocationSongData> selectSongList(List<LocationSongData> songDataArrayList, LocationInfo locationInfo) {
        ArrayList<LocationSongData> matchList = new ArrayList<>();
        float distance[], result = 0;
        LocationSongData ans = null;

        for (LocationSongData i : songDataArrayList) {
            if (i.getLocalityArea().equals(locationInfo.getCity())) {
                matchList.add(i);
            }
        }
        if (matchList.isEmpty()) {
            for (LocationSongData j : songDataArrayList) {
                if (j.getAdminArea().equals(locationInfo.getState())) {
                    matchList.add(j);
                }
            }
        }
        if (!matchList.isEmpty()) {
            for (LocationSongData k : matchList) {
                distance = SearchNearSong.getDistance(k.getLongitude(), k.getLatitude(), locationInfo.getLongitude(), locationInfo.getLatitude());
                if (result == 0 || result >= distance[0]) {
                    result = distance[0];
                    ans = k;
                }
            }
        }

        for (LocationSongData l : matchList) {
            if (l.getLocationName().equals(ans.getLocationName())) {
                ansList.add(l);
            }
        }

        return ansList;
    }
}
