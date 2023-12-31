package uk.ac.aston.cs3mdd.fitnessapp.providers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.fitnessapp.services.ExercisesService;
import uk.ac.aston.cs3mdd.fitnessapp.services.PlacesServices;
import uk.ac.aston.cs3mdd.fitnessapp.services.ProductService;

public class ServiceProvider {
    private static ExercisesService exercisesService;

    private static PlacesServices placesServices;

    private static ProductService productService;

    public static ExercisesService getExerciseService(){
        if (exercisesService == null){
            exercisesService = new Retrofit.Builder()
                    .baseUrl("https://exercisedb.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ExercisesService.class);
        }
        return exercisesService;
    }

    public static PlacesServices getPlacesServices(){
        if (placesServices == null){
            placesServices = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/maps/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PlacesServices.class);
        }
        return placesServices;
    }

}
