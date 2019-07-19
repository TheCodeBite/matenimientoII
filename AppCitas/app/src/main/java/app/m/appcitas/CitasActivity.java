package app.m.appcitas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import app.m.appcitas.Models.Citas;
import app.m.appcitas.Models.Especialidades;
import app.m.appcitas.Models.Pets;
import app.m.appcitas.Models.User;
import app.m.appcitas.Retrofit.MyService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitasActivity extends AppCompatActivity {
    TextView userText,emailText,contador_reportes;
    EditText fecha,hora;
    MyService myService;
    FloatingActionButton btn_create_reports;
    private ListView lv;
    int numEspecialidad = 0;

    private ArrayList<String> Todos_Las_Citas;
    ArrayList<Citas> listaCitas;
    private ArrayList<String> Todas_Las_Mascotas;
    ArrayList<Pets> listaMascotas;
    private ArrayList<String> Todas_Las_Especialidades;
    ArrayList<Especialidades> listaEspecialidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        myService = Connection.getServiceRemote();

        userText = findViewById(R.id.user);
        emailText = findViewById(R.id.email);
        contador_reportes = findViewById(R.id.contador_reportes);

        lv = findViewById(R.id.lv);
        final String id_usuario = getIntent().getStringExtra("usuario");
        getAllCitas(id_usuario);


        myService.getUser(id_usuario).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    final User user = response.body();
                    userText.setText("Usuario: " + user.getFirst_name() + " " + user.getLast_name());
                    userText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(CitasActivity.this);
                            View form = getLayoutInflater().inflate(R.layout.details_user, null);

                            final EditText first_name = form.findViewById(R.id.firts_name),
                                    last_name = form.findViewById(R.id.last_name),
                                    email = form.findViewById(R.id.email),
                                    telephone = form.findViewById(R.id.telephone),
                                    city = form.findViewById(R.id.city);

                            first_name.setText(user.getFirst_name());
                            last_name.setText(user.getLast_name());
                            email.setText(user.getEmail());
                            telephone.setText(user.getTelephone());
                            city.setText(user.getCity());


                            mBuilder.setPositiveButton("Regresar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            mBuilder.setNegativeButton("", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).setView(form);

                            AlertDialog dialog = mBuilder.create();
                            dialog.show();
                        }
                    });
                    emailText.setText("Email: " + user.getEmail());
                }else{
                    userText.setText(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                userText.setText(t.getMessage());
            }
        });

        System.out.println("ENTRE A BUSCAR LAS MASCOTAS");
        myService.listaPets(id_usuario).enqueue(new Callback<ArrayList<Pets>>() {
            @Override
            public void onResponse(Call<ArrayList<Pets>> call, Response<ArrayList<Pets>> response) {
                if(response.isSuccessful()){
                    listaMascotas = response.body();
                    System.out.println("EL USUARIO CUENTA CON " + listaMascotas.size() + " Mascotas");
                }else{
                    System.out.println("EL USUARIO NO CUENTA CON MASCOTAS");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Pets>> call, Throwable t) {

            }
        });

        System.out.println("ENTRE A BUSCAR LAS ESPECIALIDADES");
        myService.listaEspecialidades().enqueue(new Callback<ArrayList<Especialidades>>() {
            @Override
            public void onResponse(Call<ArrayList<Especialidades>> call, Response<ArrayList<Especialidades>> response) {
                if(response.isSuccessful()){
                    listaEspecialidades = response.body();
                    System.out.println("EXISTE " + listaEspecialidades.size() + " Especialidades");
                }else{
                    System.out.println("No hay especialidades");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Especialidades>> call, Throwable t) {

            }
        });

        btn_create_reports = findViewById(R.id.btn_create_report);

        btn_create_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CitasActivity.this);
                final View form = getLayoutInflater().inflate(R.layout.create_cita, null);

                final Spinner mascota = form.findViewById(R.id.mascota);
                Todas_Las_Mascotas = new ArrayList<>();
                for (int i = 0 ; i < listaMascotas.size(); i++){
                    String detalles ="";
                    detalles = listaMascotas.get(i).getName();
                    Todas_Las_Mascotas.add(detalles);
                }
                mascota.setAdapter(new ArrayAdapter<String>(CitasActivity.this, android.R.layout.simple_list_item_1, Todas_Las_Mascotas));
                mascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,final int pos, long id)
                    {
                        //Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
                        TextView infoPets = form.findViewById(R.id.infoPets);
                        String nacimiento = "Fecha de nacimiento: " + listaMascotas.get(pos).getBirth_date();
                        infoPets.setText(nacimiento);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {    }
                });
                final Spinner especialidad = form.findViewById(R.id.spinner);
                Todas_Las_Especialidades = new ArrayList<>();
                for (int i = 0 ; i < listaEspecialidades.size(); i++){
                    String detalles ="";
                    detalles = listaEspecialidades.get(i).getNombre();
                    Todas_Las_Especialidades.add(detalles);
                }
                especialidad.setAdapter(new ArrayAdapter<String>(CitasActivity.this, android.R.layout.simple_list_item_1, Todas_Las_Especialidades));
                /*especialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
                    {
                        Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(pos) + " " + pos, Toast.LENGTH_SHORT).show();
                        numEspecialidad = pos;
                        Log.e("Error: ", String.valueOf(numEspecialidad));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {    }
                });*/

                fecha = form.findViewById(R.id.fecha);
                fecha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.fecha:
                                showDatePickerDialog();
                                break;
                        }
                    }
                });

                hora = form.findViewById(R.id.hora);
                hora.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.hora:
                                showTimePickerDialog();
                                break;
                        }
                    }
                });

                mBuilder.setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(mascota.getSelectedItem().toString())){
                            Toast.makeText(CitasActivity.this, "Elige el nombre de tu mascota.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(TextUtils.isEmpty(especialidad.getSelectedItem().toString())){
                            Toast.makeText(CitasActivity.this, "Elige una especialidad", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(TextUtils.isEmpty(fecha.getText().toString())){
                            Toast.makeText(CitasActivity.this, "Elige una fecha.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(TextUtils.isEmpty(hora.getText().toString())){
                            Toast.makeText(CitasActivity.this, "Elige un horario.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Citas cita = new Citas();
                        cita.setMascota(mascota.getSelectedItem().toString());
                        cita.setEspecialidad(especialidad.getSelectedItem().toString());
                        cita.setFecha(fecha.getText().toString());
                        cita.setHora(hora.getText().toString());
                        cita.setOwner_id(id_usuario);
                        createCita(cita, id_usuario);

                    }
                });

                mBuilder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setView(form);

                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int posicion, long l) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CitasActivity.this);
                View form = getLayoutInflater().inflate(R.layout.details_citas, null);

                final EditText mascota = form.findViewById(R.id.mascota),
                        especialidad = form.findViewById(R.id.especialidad),
                        fecha = form.findViewById(R.id.fecha),
                        hora = form.findViewById(R.id.hora);

                mascota.setText(listaCitas.get(posicion).getMascota());
                especialidad.setText(listaCitas.get(posicion).getEspecialidad());
                fecha.setText(listaCitas.get(posicion).getFecha());
                hora.setText(listaCitas.get(posicion).getHora());


                mBuilder.setPositiveButton("Regresar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                mBuilder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //SE ELMINARÁN LOS CAMBIOS
                        deleteCita(listaCitas.get(posicion).getId(),id_usuario);
                    }
                }).setView(form);

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }

    /////////////////////////////////////////// RECUPERANDO LOS REPORTES ////////////////////////////////////
    private void getAllCitas(String idUsuario){
        System.out.println("ENTRE A BUSCAR LOS REPORTES");
        myService.listaCitas(idUsuario).enqueue(new Callback<ArrayList<Citas>>() {
            @Override
            public void onResponse(Call<ArrayList<Citas>> call, Response<ArrayList<Citas>> response) {
                if(response.isSuccessful()){
                    listaCitas = response.body();
                    System.out.println("EL USUARIO CUENTA CON " + listaCitas.size() + " CITAS");
                    ShowReports();
                }else{
                    System.out.println("EL USUARIO NO CUENTA CON CITAS");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Citas>> call, Throwable t) {

            }
        });
    }

    private void ShowReports(){
        System.out.println("en total hay " + listaCitas.size());
        Todos_Las_Citas = new ArrayList<>();
        for (int i = 0 ; i < listaCitas.size(); i++){
            String detalles ="";
            detalles = "Mascota: " + listaCitas.get(i).getMascota() + "\n" + "Fecha: \n" + listaCitas.get(i).getFecha();
            Todos_Las_Citas.add(detalles);
        }

        if(listaCitas.size() == 0){
            contador_reportes.setVisibility(View.VISIBLE);
            lv.setVisibility(View.INVISIBLE);
        }else{
            contador_reportes.setVisibility(View.INVISIBLE);
            lv.setVisibility(View.VISIBLE);

        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CitasActivity.this, android.R.layout.simple_list_item_1, Todos_Las_Citas);

        lv.setAdapter(arrayAdapter);
    }
    //////Fecha///////////////////////////
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                String dayZero = (day >=10)? Integer.toString(day):String.format("0%s",Integer.toString(day));
                String monthZero = ((month + 1) >=10)? Integer.toString(month + 1):String.format("0%s",Integer.toString(month + 1));
                final String selectedDate = dayZero + "/" + monthZero + "/" + year;
                fecha.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    ////////////Hora/////////////////////////////
    private void showTimePickerDialog() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                //Get the AM or PM for current time
                String aMpM = "AM";
                if(hourOfDay >11)
                {
                    aMpM = "PM";
                }
                //Make the 24 hour time format to 12 hour time format
                int currentHour;
                if(hourOfDay>11)
                {
                    currentHour = hourOfDay - 12;
                }
                else
                {
                    currentHour = hourOfDay;
                }
                String hourZero = (currentHour >=10)? Integer.toString(currentHour):String.format("0%s",Integer.toString(currentHour));
                String minuteZero = (minute >=10)? Integer.toString(minute):String.format("0%s",Integer.toString(minute));
                final String selectedTime = hourZero
                        + ":" + minuteZero + " " + aMpM;
                hora.setText(selectedTime);
            }
        });
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    //////////////////////CREAR CITAS///////////////////////////////
    private void createCita(Citas c, final String id_usuario){
        myService.createCita(c).enqueue((new Callback<Citas>() {
            @Override
            public void onResponse(Call<Citas> call, Response<Citas> response) {
                String rpta = response.message();
                if(response.isSuccessful())
                {
                    Toast.makeText(CitasActivity.this, "Cita creada con exito.", Toast.LENGTH_SHORT).show();
                    getAllCitas(id_usuario);
                }else{
                    //codigo de error HTTP
                    Toast.makeText(getApplicationContext(),rpta,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Citas> call, Throwable t) {
                System.out.print(t.getMessage());
                Log.e("Error: ", t.getMessage());
            }
        }));
    }
    ///////////////////////ELIMINAR CITAS////////////////////
    private void deleteCita(String id,final String id_usuario){
        System.out.println("-------->" + id);
        myService.deleteCita(id).enqueue((new Callback<Citas>() {
            @Override
            public void onResponse(Call<Citas> call, Response<Citas> response) {
                String rpta = response.message();
                if(response.isSuccessful())
                {
                    Toast.makeText(CitasActivity.this, "¡Cita Eliminado!", Toast.LENGTH_SHORT).show();
                    getAllCitas(id_usuario);
                }else{
                    //codigo de error HTTP
                    Toast.makeText(getApplicationContext(),rpta,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Citas> call, Throwable t) {
                System.out.print(t.getMessage());
                Log.e("Error: ", t.getMessage());
            }
        }));
    }
}
