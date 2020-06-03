package com.example.astronomyforkids.fragments.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.astronomyforkids.R;
import com.example.astronomyforkids.adapters.ResultsAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ResultsFragment extends Fragment {
    private static final String APP_PREFERENCES = "settings";

    /**
     * Створення та ініціалазація фрагменту результатів
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> hashSet = sharedPreferences.getStringSet("hashSet", new HashSet<String>());

        ArrayList<String> list = new ArrayList<>(hashSet);

        ResultsAdapter resultsAdapter = new ResultsAdapter(Objects.requireNonNull(getContext()), list);

        ListView listViewResults = view.findViewById(R.id.listViewResults);
        listViewResults.setAdapter(resultsAdapter);
        
        return view;
    }
}
