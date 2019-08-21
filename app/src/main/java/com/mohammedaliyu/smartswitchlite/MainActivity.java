package com.mohammedaliyu.smartswitchlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elconfidencial.bubbleshowcase.BubbleShowCase;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private LightViewModel lightViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open2();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final LightAdapter adapter = new LightAdapter();
        recyclerView.setAdapter(adapter);

        final SharedPreferences sharedpreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

        lightViewModel = ViewModelProviders.of(this).get(LightViewModel.class);
        lightViewModel.getAllLights().observe(this, new Observer<List<Light>>() {
            @Override
            public void onChanged(List<Light> lights) {
                adapter.setLights(lights);
                Log.d("lighttttt", String.valueOf(lights.size()));

                if(lights.size()==0){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("light_add_tip", true);
                    editor.putBoolean("light_on_tip", true);
                    editor.putBoolean("light_on_tipX", true);
                    editor.commit();

                    new BubbleShowCaseBuilder(MainActivity.this)
                            .title("Add new light, from here")
                            .targetView(buttonAddNote).show();

                }else {
                    if(sharedpreferences.getBoolean("light_add_tip", true)){
                        show_alert_tip(sharedpreferences, "How to update a lights", "Tab on the light name to edit");
                        show_alert_tip(sharedpreferences, "How to delete/remove a lights","Swipe on the light name to remove/delete");
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("light_add_tip", false);
                        editor.commit();
                    }
                }



            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                final Light light = adapter.getLightAt(viewHolder.getAdapterPosition());
                lightViewModel.delete(light);
                Toast.makeText(MainActivity.this, "Light removed!!!", Toast.LENGTH_SHORT).show();
                /**
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Light")
                        .setMessage("Are you sure you want to delete this "+light.getName()+"?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //on success
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                 **/
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new LightAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Light light) {
                eidt_light_alert(light);
            }
        });


    }

    private void show_alert_tip(SharedPreferences sharedpreferences, String title, String message) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);

        // add a button
        builder.setPositiveButton("Okay, got it", null);

        //update sharedprefs
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("light_on_tip", false);
        editor.commit();

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override public void onBackPressed(){
        //do your stuff here
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    //Note note = new Note(title, description, priority);
    //noteViewModel.insert(note);

    private void open2(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(MainActivity.this).create();
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_custom, null);

        final int[] state = {1};
        final String[] light_id = {""};
        final String[] light_name = {""};


        final EditText editText = (EditText) dialogView.findViewById(R.id.alert_edit_text);
        final TextView textView = (TextView) dialogView.findViewById(R.id.alert_title_text_view);
        final TextView light_status = (TextView) dialogView.findViewById(R.id.alert_status_text_view);
        final Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        final Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);


        textView.setText("New Light");
        editText.setHint("Enter Light ID");
        button1.setText("ADD");
        light_status.setVisibility(View.GONE);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                //dialogBuilder.dismiss();

                if(state[0] ==1){
                    state[0] +=1;
                    light_id[0] = editText.getText().toString();
                    editText.setText("");

                    light_status.setVisibility(View.GONE);
                    editText.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    button1.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.VISIBLE);
                    editText.setHint("Enter Light Name");
                    button1.setText("Save");
                }else {
                    light_name[0] = editText.getText().toString();
                    //editText.setText("");
                    Light light = new Light(light_name[0], "1", true, light_id[0]);
                    lightViewModel.insert(light);
                    dialogBuilder.dismiss();
                    Toast.makeText(MainActivity.this, "Light add successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });


        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void eidt_light_alert(final Light light){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(MainActivity.this).create();
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_custom, null);


        final EditText editText = (EditText) dialogView.findViewById(R.id.alert_edit_text);
        final TextView textView = (TextView) dialogView.findViewById(R.id.alert_title_text_view);
        final TextView light_status = (TextView) dialogView.findViewById(R.id.alert_status_text_view);
        final Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        final Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);


        textView.setText("Update Light");
        editText.setHint("Enter Light Name");
        editText.setText(light.getName());
        button1.setText("Save");
        light_status.setVisibility(View.GONE);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                //dialogBuilder.dismiss();


                //editText.setText("");
                String light_name = editText.getText().toString();
                light.setName(light_name);
                lightViewModel.update(light);
                dialogBuilder.dismiss();
                Toast.makeText(MainActivity.this, "Light updated successfully", Toast.LENGTH_SHORT).show();

            }
        });


        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }


}
