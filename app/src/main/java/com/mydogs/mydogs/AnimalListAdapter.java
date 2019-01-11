package com.mydogs.mydogs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by brunoferreira on 04/03/18.
 */

public class AnimalListAdapter extends ArrayAdapter<Animal> {

    private Activity context;
    private List<Animal> animalList;

    public AnimalListAdapter(Activity context, List<Animal> animals) {

        super(context, R.layout.linha_layout, animals);
        this.context = context;
        this.animalList = animals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.linha_layout, null, true);

        TextView textViewNomeLinha = (TextView) listViewItem.findViewById(R.id.textViewNomeLinha);
        TextView textViewRacaLinha = (TextView) listViewItem.findViewById(R.id.textViewRacaLinha);

        Animal animal = animalList.get(position);

        textViewNomeLinha.setText(animal.getNome());
        textViewRacaLinha.setText(animal.getRaca());

        return listViewItem;
    }
}

