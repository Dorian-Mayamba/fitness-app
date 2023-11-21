package uk.ac.aston.cs3mdd.fitnessapp.exercises.listeners;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class TargetItemClickListener implements AdapterView.OnItemClickListener {

    private Context context;

    public TargetItemClickListener(Context ctx){
        this.context = ctx;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = (String) parent.getItemAtPosition(position);
        Toast.makeText(context, "Item "+ item, Toast.LENGTH_SHORT).show();
    }
}
