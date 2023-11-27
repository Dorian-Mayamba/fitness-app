package uk.ac.aston.cs3mdd.fitnessapp.exercises.database.modules;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class HandlerModule {
    @Provides
    public static Handler provideHandler(){
        return HandlerCompat.createAsync(Looper.getMainLooper());
    }
}
