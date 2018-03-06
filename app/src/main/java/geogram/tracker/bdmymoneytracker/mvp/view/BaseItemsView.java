package geogram.tracker.bdmymoneytracker.mvp.view;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import geogram.tracker.bdmymoneytracker.model.Items;

/**
 * Created by geogr on 08.02.2018.
 */

public interface BaseItemsView extends MvpView {
    void hideRefreshing();
    void showError(String message);
    void showItems();
    void addNewItem();
}
