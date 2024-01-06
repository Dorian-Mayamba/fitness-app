package uk.ac.aston.cs3mdd.fitnessapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.adapters.PlaceOpeningHoursAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentOpeningHoursDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpeningHoursDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpeningHoursDialogFragment extends DialogFragment {

    FragmentOpeningHoursDialogBinding binding;

    public interface OnDismissClickListener{
        void onDismissClick(DialogFragment fragment);
    }

    OnDismissClickListener listener;

    private LayoutInflater inflater;

    private RecyclerView recyclerView;

    private List<String> openingHours;

    private ImageView closeImage;

    public OpeningHoursDialogFragment(List<String> openingHours){
        this.openingHours = openingHours;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        inflater = getLayoutInflater();
        binding = FragmentOpeningHoursDialogBinding.inflate(inflater,null, false);
        recyclerView = binding.openingHoursView;
        closeImage = binding.closeImage;
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDismissClick(OpeningHoursDialogFragment.this);
            }
        });
        PlaceOpeningHoursAdapter adapter = new PlaceOpeningHoursAdapter(openingHours);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(binding.getRoot())
                .setTitle("Opening hours");
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try{
            listener = (OnDismissClickListener) context;
        }catch (ClassCastException ex){
            throw new ClassCastException("The class " + context.toString() + " must implement the OnDismissClickListener interface");
        }
        super.onAttach(context);
    }
}