package com.example.astronomyforkids.fragments.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.astronomyforkids.R;
import com.example.astronomyforkids.fragments.test.ResultsFragment;

import java.util.Objects;

public class MainMenuFragment extends Fragment implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Створення та ініціалазація головного меню
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        final Button informationButton = view.findViewById(R.id.buttonInfo);
        final Button testButton = view.findViewById(R.id.buttonTest);
        final Button exitButton = view.findViewById(R.id.buttonExit);

        informationButton.setOnClickListener(this);
        testButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        return view;
    }

    /**
     * Обробка події кліку кнопоки Інформація
     * @param view
     */
    private void infoClick(View view) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MenuPartFragment(), "informationFragment")
                .addToBackStack(null)
                .commit();
    }

    /**
     * Обробка події кліку кнопоки Результати
     * @param view
     */
    private void testClick(View view) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ResultsFragment(), "testFragment")
                .addToBackStack(null)
                .commit();
    }

    /**
     * Обробка події кліку кнопоки Вихід
     * @param view
     */
    private void exitClick(View view) {
        Objects.requireNonNull(getActivity()).finish();
    }

    /**
     * Обробка подій кліку кнопок
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonInfo:
                infoClick(view);
                break;
            case R.id.buttonTest:
                testClick(view);
                break;
            case R.id.buttonExit:
                exitClick(view);
                break;
            default:
                break;
        }
    }
}
