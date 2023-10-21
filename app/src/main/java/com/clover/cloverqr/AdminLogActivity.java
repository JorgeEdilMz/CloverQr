package com.clover.cloverqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class AdminLogActivity extends AppCompatActivity {

    EditText Correo, Contraseña;
    Button INGRESAR;

    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Correo = findViewById(R.id.Correo);
        Contraseña = findViewById(R.id.Contraseña);
        INGRESAR = findViewById(R.id.INGRESAR);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(AdminLogActivity.this);
        dialog = new Dialog(AdminLogActivity.this);


        //ASIGNAMOS UN EVENTO AL BOTÓN INGRESAR
        INGRESAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CONVERTIR A STRING CORREO Y CONTRASEÑA
                String correo = Correo.getText().toString();
                String contraseña = Contraseña.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    Correo.setError("Correo inválido");
                    Correo.setFocusable(true);
                } else if (contraseña.length() < 6) {
                    Contraseña.setError("La contraseña debe ser mayor o igual a 6 caracteres");
                } else {
                    //se ejecuta el método
                    LOGINUSUARIO(correo, contraseña);
                }
            }
        });

    }

    private void LOGINUSUARIO(String correo, String contraseña) {
        progressDialog.setCancelable(false);
        progressDialog.show();
        auth.signInWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                user.getIdToken(true)
                                        .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<GetTokenResult> task) {
                                                if (task.isSuccessful()) {
                                                    String userToken = task.getResult().getToken();
                                                    // Guarda el token de autenticación en las preferencias compartidas
                                                    saveUserToken(userToken);

                                                    startActivity(new Intent(AdminLogActivity.this, PostPlantaActivity.class));
                                                    finish();
                                                    Toast.makeText(AdminLogActivity.this, "Hola! Bienvenido(a) " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                                    finish(); // Esto cerrará la actividad si lo deseas
                                                } else {
                                                    Toast.makeText(AdminLogActivity.this, "No se pudo obtener el token", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(AdminLogActivity.this, "Algo ha salido mal", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Dialog_No_Inicio();
                            Toast.makeText(AdminLogActivity.this, "Algo ha salido mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AdminLogActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Método para guardar el token de autenticación en las preferencias compartidas
    private void saveUserToken(String userToken) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_token", userToken);
        editor.apply();
    }

    private void Dialog_No_Inicio() {
        Button ok_no_inicio;

        dialog.setContentView(R.layout.no_sesion);

        ok_no_inicio = dialog.findViewById(R.id.ok_no_inicio);

        ok_no_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}