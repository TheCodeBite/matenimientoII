package app.m.appcitas;

import app.m.appcitas.Retrofit.MyService;
import app.m.appcitas.Retrofit.RetrofitClient;

public class Connection {

    private Connection(){}

    public static final String API_URL = "http://192.168.43.219:3000/API/"; //Aqui pones tu ip de tu compu

    public static MyService getServiceRemote(){
        return RetrofitClient.getClient(API_URL).create(MyService.class);
    }
}
