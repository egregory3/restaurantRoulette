package com.esquared.restaurantroulette;



public class Restaurant{
    private String placeId;
    private String name;
    private double lattitude;
    private double longitude;
    private boolean openNow;
    private boolean isOperational;
    private String formattedAddress;
    private String formattedPhone;
    private double rating;
    private int price;

    public Restaurant(){

    }
    public Restaurant(String name, String placeId, double lat, double lon, boolean openNow){
        this.name = name;
        this.placeId = placeId;
        this.isOperational = isOperational;
        this.lattitude = lat;
        this.longitude = lon;
        this.openNow = openNow;
        this.price = -1;
    }

    public void setAddress(String formattedAddress){
        this.formattedAddress = formattedAddress;
    }

    public void setPhone(String formattedPhone){
        this.formattedPhone = formattedPhone;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setPrice(int price){this.price = price;}

    String getName(){
        return this.name;
    }
    String getFormattedAddress(){
        return this.formattedAddress;
    }
    String getFormattedPhone(){
        return this.formattedPhone;
    }
    Double getRating(){
        return this.rating;
    }
    double getLattitude(){
        return this.lattitude;
    }
    double getLongitude(){
        return this.longitude;
    }
    String getLatLong(){
        return (this.lattitude + "," + this.longitude);
    }
    String getPlaceId(){
        return this.placeId;
    }
    boolean getOpenNow(){
        return this.openNow;
    }
    boolean getIsOperational(){
        return this.isOperational;
    }
    int getPrice(){ return this.price;}


}
