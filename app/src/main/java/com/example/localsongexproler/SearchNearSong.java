package com.example.localsongexproler;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class SearchNearSong {

    public String selectSongID(List<LocationSongData> songDataArrayList, LocationInfo locationInfo){
        ArrayList<LocationSongData> matchList = new ArrayList<>();
        float distance[], result = 0;
        LocationSongData ans = null;

        for(LocationSongData i : songDataArrayList){
            if(i.getLocalityArea().equals(locationInfo.getCity())) {
                matchList.add(i);
            }
        }
        if(matchList.isEmpty()){
            for(LocationSongData j : songDataArrayList){
                if(j.getAdminArea().equals(locationInfo.getState())) {
                    matchList.add(j);
                }
            }
        }
        if(!matchList.isEmpty()){
            for(LocationSongData k : matchList){
                distance = getDistance(k.getLongitude(), k.getLatitude(), locationInfo.getLongitude(), locationInfo.getLatitude());
                if(result == 0 || result > distance[0]){
                    result = distance[0];
                    ans = k;
                }
            }
        }

        return  ans.getId();
    }


    /*
     * 2点間の距離（メートル）、方位角（始点、終点）を取得
     * ※配列で返す[距離、始点から見た方位角、終点から見た方位角]
     * https://qiita.com/a_nishimura/items/6c2642343c0af832acd4
     */
    public static float[] getDistance(double x, double y, double x2, double y2) {
        // 結果を格納するための配列を生成
        float[] results = new float[3];

        // 距離計算
        Location.distanceBetween(x, y, x2, y2, results);

        return results;
    }
}
