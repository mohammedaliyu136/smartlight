package com.mohammedaliyu.smartswitchlite.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.mohammedaliyu.smartswitchlite.Light;
import com.mohammedaliyu.smartswitchlite.LightViewModel;
import com.mohammedaliyu.smartswitchlite.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private Light mLight;
    private LightViewModel lightViewModel;

    public static PlaceholderFragment newInstance(String index) {
        PlaceholderFragment fragment = new PlaceholderFragment();


        Bundle bundle = new Bundle();
        bundle.putString(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        String index = "";
        if (getArguments() != null) {
            index = getArguments().getString(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        lightViewModel = ViewModelProviders.of(this).get(LightViewModel.class);
        final ImageView imageView = root.findViewById(R.id.imageView);
        final LinearLayout click_on_btn = root.findViewById(R.id.click_on_btn);
        final TextView textView = root.findViewById(R.id.textView);


        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Log.d("foodme", String.valueOf(sharedpreferences.getBoolean("light_on_tip", true)));
        //final SharedPreferences sharedpreferences = context.getSharedPreferences("pref", 0);

        alert_how_to_operate_light(sharedpreferences);


        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                String[] ss = s.split("#"); //id#status#light_id#name

                int id = Integer.parseInt(ss[0]);
                Boolean status = Boolean.valueOf(ss[1]);
                //status = !status;

                //String light_id = ss[2];
                //String name = ss[3];
                if(status){
                    imageView.setImageResource(R.drawable.light_off_v5);
                }else{
                    imageView.setImageResource(R.drawable.light_on_v5);
                }
            }
        });

        click_on_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tt = textView.getText().toString();
                String[] ss = tt.split("#"); //id#status#light_id#name

                int id = Integer.parseInt(ss[0]);
                Boolean status = Boolean.valueOf(ss[1]);
                status = !status;

                String light_id = ss[2];
                String name = ss[3];

                Light light = new Light(name, "1", status, light_id);
                light.setId(id);
                lightViewModel.update(light);

                //pageViewModel.setIndex(tt);

                String str = String.valueOf(light.getId())+"#"+
                        light.getStatus().toString()+"#"+
                        light.getLight_id()+"#"+
                        light.getName();

                //textView.setText(str);
                pageViewModel.setIndex(str);
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private void alert_how_to_operate_light(final SharedPreferences sharedpreferences) {
        if(sharedpreferences.getBoolean("light_on_tipX", true)) {
            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("How to switch on and off your lights");
            builder.setMessage("Tab or touch the light bulb on the screen to switch on or off any light");

            // add a button
            builder.setPositiveButton("Okay, got it", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //update sharedprefs
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("light_on_tipX", false);
                    editor.commit();
                }
            });



            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}