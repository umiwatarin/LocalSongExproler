package com.example.localsongexproler;

/**
 * This app was made by umiwatarin supported by rayarc Co.Ltd.
 * If you want better, please contact umiwatarin.
 * © umiwatarin, 5th August, 2019.
 */

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends YouTubeBaseActivity implements LocationListener {
    private TextView tv1;
    private TextView tv2;
    private TextView location, song, artist, release;
    private LocationManager mLocationManager;
    private YouTubeActivity youtube;
    private static final int REQUEST_CODE_GEO = 10000;
    private LocationInfo locationInfo = new LocationInfo();
    private LocationSongData songData;
    private List<LocationSongData> songList;
    private csvReader csvReader = new csvReader();
    private SearchNearSong sns = new SearchNearSong();
    private String prev_id = null;
    private YouTubePlayerView youTubeView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //縦画面固定

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        youTubeView = findViewById(R.id.youtube_player);
        song = (TextView) findViewById(R.id.song);
        artist = (TextView) findViewById(R.id.artist);
        release = (TextView) findViewById(R.id.release);
        location = (TextView) findViewById(R.id.location);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //パーミッションが無いので、リクエストします。
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, REQUEST_CODE_GEO);
        } else{
            //パーミッションが有る
            locationStart();
        }

        songList = csvReader.reader(getApplicationContext());

        // ボタンを設定
        final Button button = findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                button.setText("Locationg...");
                locationStart();
                //マッチする曲を探す
                String id = sns.selectSongID(songList, locationInfo); //同じロケーションに複数の曲があることは現在想定外
                //マッチする曲を再生する
                if(id == null){
                    artist.setText("周辺にないっぽい");
                }else{
                    tv1.setText(id);
                    if(youtube == null) {
                        youtube = new YouTubeActivity(youTubeView, id);
                    } else if( prev_id != id ) {
                        youtube.setNewPlayer(id);
                    }
                    songData = new getInfoNearSong(songList, id).getSong();
                    song.setText(songData.getMusic());
                    artist.setText("Artist : " + songData.getArtist());
                    release.setText("(" + songData.getYear() + ")");
                    location.setText("Location : " + songData.getLocationName());
                    prev_id = id;
                }
                button.setEnabled(true);
                button.setText("Locate");
            }
        });

    }

    private void locationStart(){
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //パーミッションが無いので、リクエストします。
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300, 1, this);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 300, 1, this);

    }

    // パーミッション許可の結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode,  @NonNull String[]permissions,  @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GEO) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();

            } else {
                // それでも拒否された時の対応
                Toast toast = Toast.makeText(this,"これ以上なにもできません", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("debug", "onLocationChanged");

        String str1 = "経度:" + location.getLongitude() + "\n" + "緯度:" + location.getLatitude();
        tv1.setText(str1);
        String address = getCompleteAddressString(location.getLatitude(),location.getLongitude());
        tv2.setText(address);
        Log.d("",address);

        // 情報をすべて取得
        getCompleteAddressList(location.getLatitude(), location.getLongitude());

        // 取得後停止
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("debug", "onStatusChanged");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("DEBUG", "onProviderDisabled");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("DEBUG", "onProviderEnabled");
    }

    //位置情報から住所の一部を返します
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();           //市区町村
                String state = addresses.get(0).getAdminArea();         //都道府県
                String country = addresses.get(0).getCountryName();     //国名
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                strAdd = addresses.get(0).getAdminArea() + addresses.get(0).getLocality();
                Log.d("",strAdd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    //位置情報を格納したリストを作成する
    private void getCompleteAddressList(double LATITUDE, double LONGITUDE) {
        //LocationInfo locationInfo = new LocationInfo();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();           //市区町村
                String state = addresses.get(0).getAdminArea();         //都道府県
                String country = addresses.get(0).getCountryName();     //国名
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();   //名称


                locationInfo.setLatitude(LATITUDE);
                locationInfo.setLongitude(LONGITUDE);
                locationInfo.setAddress(address);
                locationInfo.setCity(city);
                locationInfo.setState(state);
                locationInfo.setCountry(country);
                locationInfo.setPostalCode(postalCode);
                locationInfo.setKnownName(knownName);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}