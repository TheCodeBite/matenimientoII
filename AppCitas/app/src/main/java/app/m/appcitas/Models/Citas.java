package app.m.appcitas.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Citas {
    @SerializedName("_id")
    @Expose
    private  String id;

    @SerializedName("owner_id")
    @Expose
    private  String owner_id;

    @SerializedName("fecha")
    @Expose
    private  String fecha;

    @SerializedName("hora")
    @Expose
    private  String hora;

    @SerializedName("mascota")
    @Expose
    private  String mascota;

    @SerializedName("especialidad")
    @Expose
    private  String especialidad;

    @SerializedName("confirmacion")
    @Expose
    private  String confirmacion;

    public  Citas(){
    }

    public Citas(String id, String owner_id, String fecha, String hora, String mascota, String especialidad, String confirmacion) {
        this.id = id;
        this.owner_id = owner_id;
        this.fecha = fecha;
        this.hora = hora;
        this.mascota = mascota;
        this.especialidad = especialidad;
        this.confirmacion = confirmacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }
}
