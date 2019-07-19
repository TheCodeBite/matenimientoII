package app.m.appcitas.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Especialidades {
    @SerializedName("_id")
    @Expose
    private  String id;

    @SerializedName("nombre")
    @Expose
    private  String nombre;

    @Override
    public String toString() {
        return "_id = " + id + "\n" +
                "nombre = " + nombre;
    }

    public  Especialidades(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
