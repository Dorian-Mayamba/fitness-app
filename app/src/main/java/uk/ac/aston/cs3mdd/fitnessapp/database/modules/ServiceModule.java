package uk.ac.aston.cs3mdd.fitnessapp.database.modules;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.fitnessapp.services.ExercisesService;

@Module
@InstallIn(FragmentComponent.class)
public class ServiceModule {

    @Provides
    public static ExercisesService providesExerciseService() {
        return new Retrofit.Builder()
                .baseUrl("https://exercisedb.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ExercisesService.class);
    }
}
