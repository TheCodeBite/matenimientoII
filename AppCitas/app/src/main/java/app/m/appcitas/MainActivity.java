package app.m.appcitas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;
import java.util.regex.Pattern;
import app.m.appcitas.Models.User;
import app.m.appcitas.Retrofit.MyService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    MaterialEditText edt_login_email, edt_login_password;
    Button btn_login;
    MyService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myService = Connection.getServiceRemote();

        edt_login_email = findViewById(R.id.edt_email);
        edt_login_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Objects.requireNonNull(edt_login_email.getText()).toString())) {
                    Toast.makeText(MainActivity.this, "El correo electrónico no puede ser nulo o vacío", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!validarEmail(edt_login_email.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Email no valido", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Objects.requireNonNull(edt_login_password.getText()).toString())) {
                    Toast.makeText(MainActivity.this, "La contraseña no puede ser nulo o vacío", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User();
                user.setEmail(edt_login_email.getText().toString());
                user.setPassword(edt_login_password.getText().toString());
                loginUser(user);

            }
        });
    }

    private void loginUser(User u){
        myService.loginUserBody(u).enqueue((new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String rpta = response.message();
                String res = response.body();
                //System.out.print(res);
                if(response.isSuccessful())
                {
                    if(res.isEmpty() || res.equals("  ") || res.length() <=3){
                        Toast.makeText(MainActivity.this, "Correo o contraseña incorrecta" + res , Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Ingreso exitoso ", Toast.LENGTH_SHORT).show();
                        //System.out.println("id del usuario:"+ res + "<-" + res.length());
                        Intent pantalla2 = new Intent(MainActivity.this, CitasActivity.class);
                        pantalla2.putExtra("usuario", res);
                        startActivity(pantalla2);
                    }
                }else{
                    //codigo de error HTTP
                    Toast.makeText(getApplicationContext(),rpta,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getMessage());
                Log.e("Error: ", t.getMessage());
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}