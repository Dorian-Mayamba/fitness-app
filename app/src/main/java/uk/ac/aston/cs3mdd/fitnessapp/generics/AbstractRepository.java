package uk.ac.aston.cs3mdd.fitnessapp.generics;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executor;

import uk.ac.aston.cs3mdd.fitnessapp.FitnessApplication;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;

public abstract class AbstractRepository<T> {

    protected final Executor executor;
    protected final Handler resultHandler;
    public AbstractRepository(Executor executor, Handler handler){
        this.executor = executor;
        this.resultHandler = handler;
    }

    public void notifyDataSetChange(IViewModel<T> model, List<T> exercises){
        this.resultHandler.post(new Runnable() {
            @Override
            public void run() {
                // Main task update the model exercise list
                Log.i(MainActivity.TAG, "Updated the exercise model list");
                model.updateListFromResultQuery(exercises);
            }
        });
    }



}
