package uk.ac.aston.cs3mdd.fitnessapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentAddExerciseErrorDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddExerciseErrorDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExerciseErrorDialogFragment extends DialogFragment {

    FragmentAddExerciseErrorDialogBinding binding;

    private TextView errorMessageView;

    private String message;

    private Button okButton;

    public AddExerciseErrorDialogFragment(String message){
        this.message = message;
    }

    public interface OnOkClickListerner{
        void onOkClick(DialogFragment dialogFragment);
    }

    OnOkClickListerner listerner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        binding = FragmentAddExerciseErrorDialogBinding.inflate(inflater, null, false);
        errorMessageView = binding.errorMessage;
        errorMessageView.setText(message);
        okButton = binding.okButton;
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onOkClick(AddExerciseErrorDialogFragment.this);
            }
        });
        return builder
                .setView(binding.getRoot())
                .setTitle("Add exercise error")
                .create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try {
            listerner = (OnOkClickListerner) context;
        }catch (ClassCastException ex){
            throw new ClassCastException("The fragment "+ context.toString() + " should implement the OnOkClickListener interface");
        }
        super.onAttach(context);
    }
}