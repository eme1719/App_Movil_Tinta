package com.example.nuevaparte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistroActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText ConfirmacionpasswordEditText;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
   // private FirebaseUser usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        usernameEditText = (EditText) findViewById(R.id.RegCorreo);
        nameEditText = (EditText) findViewById(R.id.RegNombre);
        passwordEditText = (EditText) findViewById(R.id.RegContraseña);
        ConfirmacionpasswordEditText = (EditText) findViewById(R.id.RegConfirmacionContraseña);
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //Toast.makeText(RegistroActivity.this, "El usuario fue creado", Toast.LENGTH_SHORT).show();

                }
                else {


                }
            }
        };

    }

    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }

    }

    public void funcRegistrarse(View view) {

        //Toast.makeText(this, "Te registraste",Toast.LENGTH_SHORT).show();
        // Intent intent = new Intent(this, MainActivity.class);
        // startActivity(intent);

        String useremmail = usernameEditText.getText().toString();
        String username = nameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String Confirmacion = ConfirmacionpasswordEditText.getText().toString();
        if ( (password).equals(Confirmacion) ) {
            if (password.length() >= 6) {
                mAuth.createUserWithEmailAndPassword(useremmail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegistroActivity.this, "hubo error al intentar hacer el usuario", Toast.LENGTH_LONG).show();
                        }
                        else{

                            FirebaseUser usuario = mAuth.getCurrentUser();
                            usuario.sendEmailVerification();
                        }

                    }
                });

            } else {
                Toast.makeText(this, "Las contraseña debe ser igual o mayor que 6 caracteres", Toast.LENGTH_SHORT).show();
            }

        } else{
            Toast.makeText(this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
        }


    }



}