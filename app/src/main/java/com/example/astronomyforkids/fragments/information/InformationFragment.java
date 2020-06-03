package com.example.astronomyforkids.fragments.information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.astronomyforkids.DB;
import com.example.astronomyforkids.R;
import com.example.astronomyforkids.fragments.test.TestFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class InformationFragment extends Fragment implements View.OnClickListener {
    private ArrayList<String> titleList;
    private ArrayList<String> informationList;
    private ArrayList<String> testList;

    private Bundle bundle = new Bundle();

    private TestFragment testFragment = new TestFragment();
    private DB db = new DB();

    private ImageSwitcher imageSwitcher;

    private int position = 0;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String nameForSRList;
    private int id;
    private boolean topicHaveTest;

    /**
     * Конструктор інформаційного фрагмента ініціалізує зміні
     * @param databaseReference
     * @param storageReference
     * @param nameForSRList
     * @param id
     * @param topicHaveTest
     */
    public InformationFragment(DatabaseReference databaseReference, StorageReference storageReference,
                               String nameForSRList, int id, boolean topicHaveTest) {
        this.databaseReference = databaseReference;
        this.storageReference = storageReference;
        this.nameForSRList = nameForSRList;
        this.id = id;
        this.topicHaveTest = topicHaveTest;
    }

    /**
     * Створення інформаційного фрагменту та заватаження інформації і зображень
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_topic, container, false);
        final TextView textViewTopic = view.findViewById(R.id.textViewTopicFIT);
        imageSwitcher = view.findViewById(R.id.imageSwitcher);
        final Button buttonBack = view.findViewById(R.id.buttonBack);
        final Button buttonForward = view.findViewById(R.id.buttonForward);

        final WebView webView = view.findViewById(R.id.webViewFIT);
        final Button buttonTest = view.findViewById(R.id.buttonGoTestFIT);

        if (!topicHaveTest)
        {
            setVisibilityAllTextView(View.GONE, imageSwitcher, buttonBack, buttonForward);
        }

        FirebaseApp.initializeApp(Objects.requireNonNull(getContext()));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                titleList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "title");
                informationList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "information");
                if (topicHaveTest)
                    testList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "test");

                textViewTopic.setText(titleList.get(id));

                String text = db.toHtmlFormat(informationList.get(id));

                webView.loadDataWithBaseURL(null, text, "text/html", "UTF-8", null);

                imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                    @Override
                    public View makeView() {
                        ImageView imageView = new ImageView(getContext());
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT));
                        imageView.setBackgroundColor(0xFF031F3C);
                        return imageView;
                    }
                });

                db.getImageUriList(storageReference, nameForSRList, position, imageSwitcher);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        buttonForward.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        if (topicHaveTest) {
            buttonTest.setVisibility(View.VISIBLE);
            buttonTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bundle.putStringArrayList("testList", testList);
                    bundle.putInt("id", id);
                    bundle.putString("name", titleList.get(id));
                    testFragment.setArguments(bundle);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, testFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
        return view;
    }


    /**
     * Задає значення видимості
     * @param idVisibility
     * @param views
     */
    private void setVisibilityAllTextView(int idVisibility, View... views)
    {
        for(View view : views){
            view.setVisibility(idVisibility);
        }
    }

    /**
     * Обробка події кліку кнопок Вперед та Назад
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonForward:
                position = db.setPositionNext(position);
                db.getImageUriList(storageReference, nameForSRList, position, imageSwitcher);
                break;
            case R.id.buttonBack:
                position = db.setPositionBack(position);
                db.getImageUriList(storageReference, nameForSRList, position, imageSwitcher);
                break;
            default:
                break;
        }
    }
}
