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

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordameEditText;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Toast.makeText(MainActivity.this,"El correeo electronico no ha sido verificado",Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText = (EditText) findViewById(R.id.MainCorreo);
        passwordameEditText = (EditText) findViewById(R.id.MainContraseña);
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Toast.makeText(MainActivity.this,"El correeo electronico no ha sido verificado",Toast.LENGTH_SHORT).show();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(!user.isEmailVerified()){
                    Toast.makeText(MainActivity.this,"El correeo electronico no ha sido verificado",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(user==null){
                        Toast.makeText(MainActivity.this,"No se ha iniciado sescion",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //Toast.makeText(MainActivity.this,"Se ha iniciado sesion",Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(MainActivity.this, Pagina1Activity.class);
                        //startActivity(intent);
                    }


                }




            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
        FirebaseUser currentUser = mAuth.getCurrentUser();

        /*FirebaseUser user2 = mAuth.getCurrentUser();
        if(user2!=null){
            //Intent intent = new Intent(MainActivity.this, Pagina1Activity.class);
            //startActivity(intent);
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            mAuth.removeAuthStateListener(authStateListener);
        }

    }

    public void funclogin(View view) {
        //Toast.makeText(this, "login",Toast.LENGTH_SHORT).show();
        String usermail = usernameEditText.getText().toString();
        String password = passwordameEditText.getText().toString();
        mAuth.signInWithEmailAndPassword(usermail,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();

                if(!task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"hubo error", Toast.LENGTH_LONG).show();
                }
                else{
                    if(!user.isEmailVerified()){
                        Toast.makeText(MainActivity.this,"El correeo electronico no ha sido verificado",Toast.LENGTH_SHORT).show();
                    }
                    else{

                        Toast.makeText(MainActivity.this,"Correcto", Toast.LENGTH_LONG).show();
                         Intent intent = new Intent(MainActivity.this, Pagina1Activity.class);
                         startActivity(intent);

                    }
                }
            }
        });

    }


    public void funcRegistro(View view) {

       // Toast.makeText(this, "Entre",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }
    public void fLogout(View view) {
        Toast.makeText(this, "Out",Toast.LENGTH_SHORT).show();
        mAuth.removeAuthStateListener(authStateListener);
        mAuth.addAuthStateListener(authStateListener);
        String useremmail = "pepe@outlook.com";
        String password = "1234567";
        mAuth.createUserWithEmailAndPassword(useremmail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "hubo error al intentar hacer el usuario", Toast.LENGTH_LONG).show();
                }
                else{

                    FirebaseUser usuario = mAuth.getCurrentUser();
                    usuario.sendEmailVerification();
                }

            }
        });
        //FirebaseAuth.getInstance().signOut();


    }


}
