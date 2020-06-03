package com.example.astronomyforkids.fragments.topic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.astronomyforkids.R;
import com.example.astronomyforkids.fragments.information.SolarSystemFragment;
import com.example.astronomyforkids.fragments.information.InformationFragment;
import com.example.astronomyforkids.adapters.TopicAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

public class TopicFragment extends Fragment {
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ArrayList<String> topicList;
    private ArrayList<String> nameForSRList;
    private String topicPart;
    private int idPart;

    /**
     * Конструктор фрагменту тем ініціалізує змінні
     * @param databaseReference
     * @param storageReference
     * @param topicList
     * @param nameForSRList
     * @param topicPart
     * @param idPart
     */
    public TopicFragment(DatabaseReference databaseReference, StorageReference storageReference,
                         ArrayList<String> topicList, ArrayList<String> nameForSRList,
                         String topicPart, int idPart) {
        this.databaseReference = databaseReference;
        this.storageReference = storageReference;
        this.topicList = topicList;
        this.nameForSRList = nameForSRList;
        this.topicPart = topicPart;
        this.idPart = idPart;
    }

    /**
     * Створоює об'єкт класу інформаційного фрагменту та передає індекс теми
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);

        TextView textViewTopic = view.findViewById(R.id.textViewTopicMenu);
        textViewTopic.setText(topicPart);

        TopicAdapter topicAdapter = new TopicAdapter(Objects.requireNonNull(getContext()), topicList);

        ListView listViewTopic = view.findViewById(R.id.listViewTopic);
        listViewTopic.setAdapter(topicAdapter);

        listViewTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(idPart == 0)
                {
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new SolarSystemFragment(databaseReference, storageReference,
                                    nameForSRList.get(i), i))
                            .addToBackStack(null)
                            .commit();
                } else if (idPart == 1) {
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new InformationFragment(databaseReference, storageReference,
                                    nameForSRList.get(i), i, true))
                            .addToBackStack(null)
                            .commit();
                } else {
                    boolean topicHaveTest = i != 2 && i != 5;
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new InformationFragment(databaseReference, storageReference,
                                    nameForSRList.get(i), i, topicHaveTest))
                            .addToBackStack(null)
                            .commit();
                }

            }
        });

        return view;
    }
}
