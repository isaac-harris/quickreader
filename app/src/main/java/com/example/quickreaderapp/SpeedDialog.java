package com.example.quickreaderapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SpeedDialog extends AppCompatDialogFragment {
    private EditText editTextSpeed;
    private SpeedDialogListner listner;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater  = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        builder.setView(view)
                .setTitle("Modify Speed")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String speed = editTextSpeed.getText().toString();
                        listner.applyTexts(speed);

                    }
                });
        editTextSpeed = view.findViewById(R.id.speed_input);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listner =  (SpeedDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement SpeedDialogListner");
        }
    }

    public interface SpeedDialogListner{
        void applyTexts(String speed);
    }
}
