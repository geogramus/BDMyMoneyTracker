package geogram.tracker.bdmymoneytracker.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by geogr on 08.02.2018.
 */
@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }
    @Singleton
    @Provides
    public Context provideContext(){return application;}
}
