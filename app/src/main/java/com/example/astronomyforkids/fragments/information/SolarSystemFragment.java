package com.example.astronomyforkids.fragments.information;

import android.annotation.SuppressLint;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class SolarSystemFragment extends Fragment implements View.OnClickListener{
    private ArrayList<String> namePlanetsList;
    private ArrayList<String> titlePlanetsList;
    private ArrayList<String> massPlanetsList;
    private ArrayList<String> diameterPlanetsList;
    private ArrayList<String> temperaturePlanetsList;
    private ArrayList<String> distanceFromEarthPlanetsList; // for sun
    private ArrayList<String> distanceFromSunPlanetsList;
    private ArrayList<String> informationPlanetsList;
    private ArrayList<String> moonsPlanetsList;
    private ArrayList<String> yearPlanetsList;
    private ArrayList<String> dayPlanetsList;
    private ArrayList<String> testList;

    private Bundle bundle = new Bundle();

    private ImageSwitcher imageSwitcher;
    private int position = 0;

    private TestFragment testFragment = new TestFragment();

    private DB db = new DB();

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String nameForSRList;
    private int ID;

    /**
     * Конструктор інформаційного фрагмента Сонячної системи ініціалізує зміні
     * @param databaseReference
     * @param storageReference
     * @param nameForSRList
     * @param ID
     */
    public SolarSystemFragment(DatabaseReference databaseReference,
                               StorageReference storageReference,
                               String nameForSRList, int ID) {
        this.databaseReference = databaseReference;
        this.storageReference = storageReference;
        this.nameForSRList = nameForSRList;
        this.ID = ID;
    }

    /**
     * Створення інформаційного фрагменту Сонячної системи та заватаження інформації і зображень
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solar_system, container, false);

        imageSwitcher = view.findViewById(R.id.imageSwitcherSS);

        final Button buttonBack = view.findViewById(R.id.buttonBackSS);
        final Button buttonForward = view.findViewById(R.id.buttonForwardSS);
        final Button buttonGoTest = view.findViewById(R.id.buttonGoTest);

        final TextView textViewTitle = view.findViewById(R.id.textViewID);
        final TextView textViewMass = view.findViewById(R.id.textViewMass);
        final TextView textViewDiameter = view.findViewById(R.id.textViewDiameter);
        final TextView textViewTemperature = view.findViewById(R.id.textViewTemperature);
        final TextView textViewDistance = view.findViewById(R.id.textViewDistance);//d
        final TextView textViewMoons = view.findViewById(R.id.textViewMoons);
        final TextView textViewYear = view.findViewById(R.id.textViewYear);
        final TextView textViewDay = view.findViewById(R.id.textViewDay);

        final TextView[] textViewPlanetsList = {textViewMass, textViewDiameter, textViewTemperature, 
                                         textViewDistance, textViewMoons, textViewYear, textViewDay};

        final TextView[] textViewsSunList = {textViewMass, textViewDiameter, textViewTemperature, textViewDistance};
        
        final WebView webView = view.findViewById(R.id.webView);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namePlanetsList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "name");
                titlePlanetsList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "title");
                massPlanetsList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "mass");
                diameterPlanetsList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "diameter");
                temperaturePlanetsList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "temperature");
                distanceFromEarthPlanetsList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "distance_from_earth");
                distanceFromSunPlanetsList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "distance_from_the_sun");
                informationPlanetsList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "information");
                moonsPlanetsList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "moons");
                yearPlanetsList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "year");
                dayPlanetsList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "day");
                testList = db.getCollectFromDB((Map<String, Object>) dataSnapshot.getValue(), "test");

                textViewTitle.setText(titlePlanetsList.get(ID));
                String text = db.toHtmlFormat(informationPlanetsList.get(ID));
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

                if (ID == 0) {//SS
                    setVisibilityAllTextView(View.GONE, textViewPlanetsList);
                } else if (ID == 1) {//Sun
                    final String[] allSunData = {massPlanetsList.get(ID), diameterPlanetsList.get(ID),
                            temperaturePlanetsList.get(ID), distanceFromEarthPlanetsList.get(1)};

                    setVisibilityAllTextView(View.VISIBLE, textViewsSunList);
                    setTextAllTextView(setListAllString(allSunData), textViewsSunList);
                } else {// all planets
                    final String[] allPlanetsData = {massPlanetsList.get(ID), diameterPlanetsList.get(ID),
                                                    temperaturePlanetsList.get(ID), distanceFromSunPlanetsList.get(ID),
                                                    moonsPlanetsList.get(ID), yearPlanetsList.get(ID), dayPlanetsList.get(ID)};

                    setVisibilityAllTextView(View.VISIBLE, textViewPlanetsList);
                    setTextAllTextView(setListAllString(allPlanetsData), textViewPlanetsList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        buttonForward.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        buttonGoTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putStringArrayList("testList", testList);
                bundle.putInt("id", ID);
                bundle.putString("name", titlePlanetsList.get(ID));
                testFragment.setArguments(bundle);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, testFragment, "testFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    /**
     * Задає значення видимості
     * @param idVisibility
     * @param textViews
     */
    private void setVisibilityAllTextView(int idVisibility, TextView[] textViews)
    {
        for(TextView textViewItem : textViews){
            textViewItem.setVisibility(idVisibility);
        }
    }

    /**
     * Повертає список
     * @param strings
     * @return
     */
    private ArrayList<String> setListAllString(String... strings)
    {
        return new ArrayList<>(Arrays.asList(strings));
    }

    /**
     * Задає текст для всіх TextView
     * @param textList
     * @param textViews
     */
    @SuppressLint("SetTextI18n")
    private void setTextAllTextView(ArrayList<String> textList, TextView[] textViews)
    {
        for(int i = 0; i < textViews.length; i++){
            textViews[i].setText(textViews[i].getText() + textList.get(i));
        }
    }

    /**
     * Обробка події кліку кнопок Вперед та Назад
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonForwardSS:
                position = db.setPositionNext(position);
                db.getImageUriList(storageReference, nameForSRList, position, imageSwitcher);
                break;
            case R.id.buttonBackSS:
                position = db.setPositionBack(position);
                db.getImageUriList(storageReference, nameForSRList, position, imageSwitcher);
                break;
            default:
                break;
        }
    }
}
