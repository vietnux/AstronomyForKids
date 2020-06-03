package com.example.astronomyforkids.fragments.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.astronomyforkids.R;

import java.util.Objects;

public class LoadFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Повертає рагмент заватаження
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                .add(R.id.container, new MainMenuFragment())
                                .commit();
                    }
                },
                1500
        );

        return inflater.inflate(R.layout.fragment_load, container, false);
    }
}