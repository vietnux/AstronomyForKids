package com.example.astronomyforkids.fragments.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.astronomyforkids.DB;
import com.example.astronomyforkids.R;
import com.example.astronomyforkids.fragments.topic.TopicFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MenuPartFragment extends Fragment {
    private ArrayList<String> topicList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();

    private DB db = new DB();

    /**
     * Створення та ініціалазація інформаційного меню
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_part, container, false);
        Button buttonSolarSystem = view.findViewById(R.id.buttonSolarSystem);
        Button buttonUniverse = view.findViewById(R.id.buttonUniverse);
        Button buttonSpace = view.findViewById(R.id.buttonSpace);

        buttonSolarSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopicFromDB("Solar System", "Solar_system", "Сонячна Система", 0);
            }
        });

        buttonUniverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopicFromDB("Universe", "Universe", "Всесвіт", 1);
            }
        });

        buttonSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopicFromDB("Space Exploration", "Space_exploration", "Дослідження космосу", 2);
            }
        });

        return view;
    }

    /**
     * Створення об'єкту класу фрагмент тем та передача в нього списку тем
     * @param dbChild
     * @param storageChild
     * @param topicPart
     * @param idPart
     */
    private void loadTopicFromDB(String dbChild, String storageChild, final String topicPart, final int idPart){
        FirebaseApp.initializeApp(Objects.requireNonNull(getContext()));
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(dbChild);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageReference = storage.getReferenceFromUrl(DB.URLStorage).child(storageChild);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                topicList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "title");
                nameList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "name");

                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new TopicFragment(databaseReference, storageReference,
                                topicList, nameList, topicPart, idPart))
                        .addToBackStack(null)
                        .commit();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
