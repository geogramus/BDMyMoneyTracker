package geogram.tracker.bdmymoneytracker;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import geogram.tracker.bdmymoneytracker.di.component.ApplicationComponent;
import geogram.tracker.bdmymoneytracker.di.component.DaggerApplicationComponent;
import geogram.tracker.bdmymoneytracker.di.module.ApplicationModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by geogr on 08.02.2018.
 */

public class MyApplication extends Application {

    private static ApplicationComponent sApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
//        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
//            @Override
//            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
//                super.set(imageView, uri, placeholder, tag);
//                Glide.with(imageView.getContext()).load(uri).into(imageView);
//            }
//        });
    }

    private void initComponent(){
        sApplicationComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }
    public static ApplicationComponent getsApplicationComponent(){
        return sApplicationComponent;
    }
}

