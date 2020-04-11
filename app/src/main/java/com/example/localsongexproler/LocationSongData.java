package com.example.localsongexproler;

public class LocationSongData {
    private String locationName;
    private String music;
    private String name;
    private String id;
    private String artist;
    private String year;
    private String adminArea;
    private String localityArea;
    private Double latitude;
    private Double longitude;


    public LocationSongData(String locationName, String music, String id, String artist, String year, String adminArea, String localityArea, Double latitude, Double longitude) {
        this.locationName = locationName;
        this.music = music;
        this.id = id;
        this.artist = artist;
        this.year = year;
        this.adminArea = adminArea;
        this.localityArea = localityArea;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocationName() { return locationName; }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public void setAdminArea(String adminArea) {
        this.adminArea = adminArea;
    }

    public String getLocalityArea() {
        return localityArea;
    }

    public void setLocalityArea(String localityArea) {
        this.localityArea = localityArea;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = Double.valueOf(latitude);
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = Double.valueOf(longitude);
    }


}
