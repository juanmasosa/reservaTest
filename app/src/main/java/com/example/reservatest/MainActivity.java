
package com.example.reservatest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario, etContrasena, etTelefono, etEmail, etFecha;
    Button btnAgregar;

    DatabaseReference databaseReference;

    List<Usuario> listaUsuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        btnAgregar = findViewById(R.id.btnAgregar);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarUsuario();
            }
        });
    }

    public void agregarUsuario(){
        listaUsuarios.clear();
        Usuario usuario = new Usuario(
                etUsuario.getText().toString(),
                etContrasena.getText().toString(),
                etTelefono.getText().toString(),
                etEmail.getText().toString()
        );

        databaseReference.child("usuario").push().setValue(usuario,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(MainActivity.this, "Usuario Añadido.", Toast.LENGTH_SHORT).show();
                    }
                });
                limpiarCampos();
    }
    public void limpiarCampos(){
        etUsuario.setText("");
        etContrasena.setText("");
        etEmail.setText("");
        etTelefono.setText("");
    }
}
