package app.m.appcitas.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pets {

    @SerializedName("_id")
    @Expose
    private  String id;

    @SerializedName("name")
    @Expose
    private  String name;

    @SerializedName("birth_date")
    @Expose
    private  String birth_date;

    @SerializedName("owner_id")
    @Expose
    private  String owner_id;

    @Override
    public String toString() {
        return "_id = " + id + "\n" +
                "name = " + name + "\n" +
                "birth_date = " + birth_date + "\n" +
                "owner_id = " + owner_id;
    }

    public  Pets(){
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }
}
