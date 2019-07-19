package app.m.appcitas.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    @Expose
    private  String id;

    @SerializedName("first_name")
    @Expose
    private  String first_name;

    @SerializedName("last_name")
    @Expose
    private  String last_name;

    @SerializedName("email")
    @Expose
    private  String email;

    @SerializedName("password")
    @Expose
    private  String password;

    @SerializedName("telephone")
    @Expose
    private  String telephone;

    @SerializedName("city")
    @Expose
    private  String city;

    @Override
    public String toString() {
        return "_id = " + id + "\n" +
                "first_name = " + first_name + "\n" +
                "last_name = " + last_name + "\n" +
                "email = " + email + "\n" +
                "telephone = " + telephone + "\n" +
                "city = " + city;
    }

    public  User(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
