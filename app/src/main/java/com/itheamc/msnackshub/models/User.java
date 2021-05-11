package com.itheamc.msnackshub.models;

public class User {
    private String _id;
    private String _name;
    private String _phone;
    private String _email;
    private String _image;
    private Double _lat;
    private Double _long;

    // Constructor
    public User() {
    }

    // Constructor with parameters
    public User(String _id, String _name, String _phone, String _email, String _image, Double _lat, Double _long) {
        this._id = _id;
        this._name = _name;
        this._phone = _phone;
        this._email = _email;
        this._image = _image;
        this._lat = _lat;
        this._long = _long;
    }

    // Getters and Setters
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_image() {
        return _image;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public Double get_lat() {
        return _lat;
    }

    public void set_lat(Double _lat) {
        this._lat = _lat;
    }

    public Double get_long() {
        return _long;
    }

    public void set_long(Double _long) {
        this._long = _long;
    }

    // Overriding toString() method for debugging
    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", _name='" + _name + '\'' +
                ", _phone='" + _phone + '\'' +
                ", _email='" + _email + '\'' +
                ", _image='" + _image + '\'' +
                ", _lat=" + _lat +
                ", _long=" + _long +
                '}';
    }


}
