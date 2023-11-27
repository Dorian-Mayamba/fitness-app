package uk.ac.aston.cs3mdd.fitnessapp.database.modules;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn({ActivityComponent.class, FragmentComponent.class})
public class HandlerModule {
    @Provides
    public static Handler provideHandler(){
        return HandlerCompat.createAsync(Looper.getMainLooper());
    }
}
