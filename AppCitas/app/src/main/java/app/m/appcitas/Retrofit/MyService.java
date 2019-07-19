package app.m.appcitas.Retrofit;

import java.util.ArrayList;

import app.m.appcitas.Models.Citas;
import app.m.appcitas.Models.Especialidades;
import app.m.appcitas.Models.Pets;
import app.m.appcitas.Models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MyService {

    //USER
    @POST("login/")
    Call<String> loginUserBody(@Body User user);

    @GET("user/{id}")
    Call<User> getUser(@Path("id") String id);

    //MASCOTAS
    @GET("pets/{id}")
    Call<ArrayList<Pets>> listaPets(@Path("id") String owner_id);

    //ESPECIALIDADES
    @GET("especialidades/")
    Call<ArrayList<Especialidades>> listaEspecialidades();

    //CITAS
    @POST("postcita/")
    Call<Citas> createCita(@Body Citas report);

    @GET("listado/{id}")
    Call<ArrayList<Citas>> listaCitas(@Path("id") String owner_id);

    @DELETE("deletecita/{id}")
    Call<Citas> deleteCita(@Path("id") String id);

    /*@PUT("reports/{id}")
    Call<Citas> updateCita(@Path("id") String id, @Body Citas report);*/

}
