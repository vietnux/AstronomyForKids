package com.example.astronomyforkids;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.astronomyforkids.fragments.menu.LoadFragment;

public class MainActivity extends AppCompatActivity {

    /**
     * Створює об'єкт фрагменту завантаження
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoadFragment())
                    .commit();
        }
    }
}
