
package com.example.reservatest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario, etContrasena, etTelefono, etEmail, etFecha;
    Button btnCOnsultar, btnConsultarUsuario, btnAgregar, btnEditar;
    RecyclerView rvUsuarios;

    DatabaseReference databaseReference;

    List<Usuario> listaUsuarios = new ArrayList<>();

    AdaptadorUsuario adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        btnCOnsultar = findViewById(R.id.btnConsultar);
        btnConsultarUsuario = findViewById(R.id.btnConsultarUsuario);
        rvUsuarios = findViewById(R.id.rvUsuarios);
        rvUsuarios.setLayoutManager(new GridLayoutManager(this,1));


        databaseReference = FirebaseDatabase.getInstance().getReference();


        obtenerUsuario();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarUsuario();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarUsuario();
            }
        });

        btnCOnsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerUsuario();
            }
        });

        btnConsultarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerUsuario();
            }
        });

    }

    public void obtenerUsuariuo(){
        listaUsuarios.clear();
        String usuario = etUsuario.getText().toString();

        Query query = databaseReference.child("usuario").orderByChild("usuario").equalTo(usuario);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                    listaUsuarios.add(objeto.getValue(Usuario.class));
                }

                adaptador = new AdaptadorUsuario(MainActivity.this, listaUsuarios);
                rvUsuarios.setAdapter(adaptador);

                limpiarCampos();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void obtenerUsuario(){
        listaUsuarios.clear();
        databaseReference.child("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objeto : dataSnapshot.getChildren()){
                    listaUsuarios.add(objeto.getValue(Usuario.class));

                }

                adaptador = new AdaptadorUsuario(MainActivity.this, listaUsuarios);
                rvUsuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        limpiarCampos();
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

    public void editarUsuario(){
        listaUsuarios.clear();

        Usuario usuario = new Usuario(
            etUsuario.getText().toString(),
            etContrasena.getText().toString(),
            etTelefono.getText().toString(),
            etEmail.getText().toString()
        );

        Query query = databaseReference.child("usuario").orderByChild("usuario").equalTo(usuario.getUsuario());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = "";
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    key = ds.getKey();//obtiene del id del registro para editarlo
                }
                databaseReference.child("usuario").child(key).setValue(usuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
