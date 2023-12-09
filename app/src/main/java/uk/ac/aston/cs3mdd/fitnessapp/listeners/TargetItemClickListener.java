package uk.ac.aston.cs3mdd.fitnessapp.listeners;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import uk.ac.aston.cs3mdd.fitnessapp.models.BodyPartsExercisesViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.ExerciseRepository;

public class TargetItemClickListener implements AdapterView.OnItemClickListener {

    private Context context;

    private String selectedItem;

    private BodyPartsExercisesViewModel exercisesViewModel;

    private ExerciseRepository exerciseRepository;

    public TargetItemClickListener(Context ctx, BodyPartsExercisesViewModel model, ExerciseRepository repository){
        this.context = ctx;
        this.exercisesViewModel = model;
        this.exerciseRepository = repository;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.selectedItem = (String) parent.getItemAtPosition(position);
        Toast.makeText(context, "Item "+ selectedItem, Toast.LENGTH_SHORT).show();
        exercisesViewModel.requestExercisesFromBodyPart(this.exerciseRepository,this.selectedItem);
    }

    public String getSelectedItem() {
        return selectedItem;
    }
}
