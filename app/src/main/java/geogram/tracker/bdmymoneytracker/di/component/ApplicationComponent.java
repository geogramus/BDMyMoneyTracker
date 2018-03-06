package geogram.tracker.bdmymoneytracker.di.component;

import javax.inject.Singleton;

import dagger.Component;
import geogram.tracker.bdmymoneytracker.di.module.ApplicationModule;
import geogram.tracker.bdmymoneytracker.mvp.presenter.ItemsPresenter;
import geogram.tracker.bdmymoneytracker.ui.fragments.ItemsFragment;

/**
 * Created by geogr on 08.02.2018.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(ItemsFragment itemsFragment);
    void inject(ItemsPresenter presenter);
}
