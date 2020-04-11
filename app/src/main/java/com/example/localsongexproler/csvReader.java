/**
 * https://qiita.com/mii-chang/items/94fad3a778377a18ccf5
 */

package com.example.localsongexproler;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class csvReader {
    List<LocationSongData> objects = new ArrayList<>();
    public List<LocationSongData> reader(Context context) {
        AssetManager assetManager = context.getResources().getAssets();
        boolean header = true;
        try {
            // CSVファイルの読み込み
            InputStream inputStream = assetManager.open("sample.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferReader.readLine()) != null) {

                if (header) {
                    header = false;
                } else {
                    //カンマ区切りで１つづつ配列に入れる
                    String[] RowData = line.split(",");

                    //CSVの左([0]番目)から順番にセット
                    String locationName = RowData[0];
                    String music = RowData[1];
                    String id = RowData[2];
                    String artist = RowData[3];
                    String year = RowData[4];
                    String adminArea = RowData[5];
                    String localityArea = RowData[6];
                    Double latitude = Double.parseDouble(RowData[7]);
                    Double longitude = Double.parseDouble(RowData[8]);

                    objects.add(new LocationSongData(locationName, music, id, artist, year, adminArea, localityArea, latitude, longitude));
                }
            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }
}
