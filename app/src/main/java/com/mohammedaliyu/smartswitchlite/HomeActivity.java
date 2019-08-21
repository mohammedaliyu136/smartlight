package com.mohammedaliyu.smartswitchlite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.elconfidencial.bubbleshowcase.BubbleShowCase;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseSequence;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mohammedaliyu.smartswitchlite.ui.main.SectionsPagerAdapter;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private LightViewModel lightViewModel;
    private Boolean empty_lights=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        final FloatingActionButton fab = findViewById(R.id.fab);
        final SharedPreferences sharedpreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

        lightViewModel = ViewModelProviders.of(this).get(LightViewModel.class);
        lightViewModel.getAllLights().observe(this, new Observer<List<Light>>() {
            @Override
            public void onChanged(List<Light> lights) {
                if(lights.size()==0){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("light_on_tip", true);
                    editor.putBoolean("light_on_tipX", true);
                    editor.commit();
                    new BubbleShowCaseBuilder(HomeActivity.this)
                            .title("You can add more light here.")
                            .targetView(fab).show();
                }
                sectionsPagerAdapter.setLights(lights);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
            }
        });

    }
}