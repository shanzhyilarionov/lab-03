package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    public interface EditCityDialogListener {
        void editCity(City updatedCity, int position);
    }

    private static final String ARG_CITY = "city";
    private static final String ARG_POSITION = "position";

    public static EditCityFragment newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        args.putInt(ARG_POSITION, position);

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_edit_city, null);

        EditText cityEditText = view.findViewById(R.id.edit_text_city_text);
        EditText provinceEditText = view.findViewById(R.id.edit_text_province_text);

        Bundle args = requireArguments();
        City city = (City) args.getSerializable(ARG_CITY);
        int position = args.getInt(ARG_POSITION);

        if (city != null) {
            cityEditText.setText(city.getName());
            provinceEditText.setText(city.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String newCity = cityEditText.getText().toString().trim();
                    String newProvince = provinceEditText.getText().toString().trim();

                    City updated = new City(newCity, newProvince);

                    EditCityDialogListener listener = (EditCityDialogListener) getActivity();
                    if (listener != null) {
                        listener.editCity(updated, position);
                    }
                });

        return builder.create();
    }
}