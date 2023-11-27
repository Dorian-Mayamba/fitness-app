package uk.ac.aston.cs3mdd.fitnessapp.database.modules;

import androidx.room.Room;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.fitnessapp.FitnessApplication;
import uk.ac.aston.cs3mdd.fitnessapp.database.FitnessDatabase;
import uk.ac.aston.cs3mdd.fitnessapp.database.executors.ExerciseQueryExecutor;
import uk.ac.aston.cs3mdd.fitnessapp.exercises.services.ExercisesService;

@Module
@InstallIn({ActivityComponent.class, FragmentComponent.class})
public abstract class ExercisesModule {
    @Binds
    public abstract Executor bindExecutor(
            ExerciseQueryExecutor exerciseQueryExecutor
    );

    @Provides
    public static FitnessDatabase providesFitnessDatabase() {
        return Room.databaseBuilder(FitnessApplication.getAppContext(), FitnessDatabase.class,
                        "fitness_db").fallbackToDestructiveMigration()
                .build();
    }
}
