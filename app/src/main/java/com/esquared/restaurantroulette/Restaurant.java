package com.esquared.restaurantroulette;


public class Restaurant {
    private String placeId;
    private String name;
    private double lattitude;
    private double longitude;
    private boolean openNow;
    private boolean isOperational;
    private String streetNum;
    private String street;
    private String locality;
    private String state;
    private String country;
    private String postalCode;
    private String formattedAddress;
    private String formattedPhone;
    private double rating;

    public Restaurant(String name, String placeId, double lattitude, double longitude, boolean openNow, boolean isOperational){
        this.name = name;
        this.placeId = placeId;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.openNow = openNow;
        this.isOperational = isOperational;
    }

    public void setAddress(String streetNum, String street, String locality, String state, String country, String postalCode, String formattedAddress){
        this.streetNum = streetNum;
        this.street = street;
        this.locality = locality;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.formattedAddress = formattedAddress;
    }

    public void setPhone(String formattedPhone){
        this.formattedPhone = formattedPhone;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

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


}
