package com.mydogs.mydogs;

import android.app.AlertDialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private EditText raca;
    private Button salvar;
    private ListView listViewAnimals;

    DatabaseReference databaseAnimal;

    List<Animal> animalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseAnimal = FirebaseDatabase.getInstance().getReference("Animal");

        nome = findViewById(R.id.editTextName);
        raca = findViewById(R.id.editTextRaca);
        salvar = findViewById(R.id.buttonSalvar);
        listViewAnimals = findViewById(R.id.list_view_animals);

        animalList = new ArrayList<>();


        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDog();
            }
        });

        listViewAnimals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animal animal = animalList.get(position);
                showEditDeleteDialog(animal.getId(), animal.getNome(), animal.getRaca());
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        databaseAnimal.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                animalList.clear();

                for(DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    Animal animal = artistSnapshot.getValue(Animal.class);

                    animalList.add(animal);
                }

                AnimalListAdapter adapter = new AnimalListAdapter(MainActivity.this, animalList);

                listViewAnimals.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void salvarDog() {

        String nomeS = nome.getText().toString().trim();
        String racaS = raca.getText().toString().trim();

        if(!TextUtils.isEmpty(nomeS) && !TextUtils.isEmpty(racaS)){
            String id = databaseAnimal.push().getKey();

            Animal animal = new Animal(id, nomeS, racaS);

            databaseAnimal.child(id).setValue(animal);

            nome.setText("");
            raca.setText("");

            Toast.makeText(this, "Animal adicionado", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Por Favor Preencha Todos os Campos", Toast.LENGTH_LONG).show();

        }

    }

    private boolean editarAnimal(String id, String nome, String raca) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Animal").child(id);

        Animal animal = new Animal(id, nome, raca);
        dR.setValue(animal);
        Toast.makeText(getApplicationContext(), "Animal Editado com Sucesso", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteAnimal(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Animal").child(id);

        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Animal Excluido com Sucesso", Toast.LENGTH_LONG).show();

        return true;
    }

    private void showEditDeleteDialog(final String animaltId, String animalNome, String animalRaca) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_edit, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextNome = (EditText) dialogView.findViewById(R.id.editTextNomeEdit);
        final EditText editTextRaca = (EditText) dialogView.findViewById(R.id.editTextRacaEdit);
        final Button buttonEditar = (Button) dialogView.findViewById(R.id.buttonEditAnimal);
        final Button buttonExcluir = (Button) dialogView.findViewById(R.id.buttonDeleteAnimal);

        editTextNome.setText(animalNome);
        editTextRaca.setText(animalRaca);

        dialogBuilder.setTitle(animalNome);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextNome.getText().toString().trim();
                String raca = editTextRaca.getText().toString().trim();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(raca)) {
                    editarAnimal(animaltId, name, raca);
                    b.dismiss();
                }else{ Toast.makeText(MainActivity.this, "Por Favor Preencha Todos os Campos", Toast.LENGTH_LONG).show();}
            }
        });


        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteAnimal(animaltId);
                b.dismiss();

            }
        });
    }
}
