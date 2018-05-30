package geogram.tracker.bdmymoneytracker.mvp.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

import geogram.tracker.bdmymoneytracker.MyApplication;
import geogram.tracker.bdmymoneytracker.model.Items;
import geogram.tracker.bdmymoneytracker.mvp.view.BaseItemsView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by geogr on 08.02.2018.
 */
@InjectViewState
public class ItemsPresenter extends MvpPresenter<BaseItemsView> {
    @Inject
    Context context;
    private static int totalIncome = 0;

    public static int getTotalIncome() {
        return totalIncome;
    }

    private static int totalExpense;

    public static int getTotalExpense() {
        return totalExpense;
    }


    public ItemsPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    public List<Items> getItemsFromDb(String type) {
        String[] sortFields = {"id"};
        Sort[] sortOrder = {Sort.DESCENDING};
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Items> realmList = realm.where(Items.class).equalTo("type", type)
                .findAll()
                .sort(sortFields, sortOrder);

        List<Items> list = realm.copyFromRealm(realmList);
        if (type.equals(Items.TYPE_EXPENSE)) {
            totalExpense = 0;
            for (int i = 0; i < list.size(); i++) {
                totalExpense += list.get(i).getPrice();
            }
        } else {
            totalIncome = 0;
            for (int i = 0; i < list.size(); i++) {
                totalIncome += list.get(i).getPrice();
            }
        }
        return list;
    }

    public void deleteItemFromDB(Items item) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Items> removeItem = realm.where(Items.class).equalTo("id", item.getId()).findAll();
//        for (int i=removeItem.size();i>=0;i--){
        realm.executeTransaction(realm1 -> removeItem.deleteAllFromRealm());
//       }

    }

    public void addNewItemToBD(Items item) {
        int id = item.getId();

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Items> idItem = realm.where(Items.class).equalTo("id", item.getId()).findAll();
        do {
            item.setId(id++);
            idItem = realm.where(Items.class).equalTo("id", item.getId()).findAll();

        } while (idItem.size() > 0);
        realm.executeTransaction(realm1 -> realm.insert(item));

    }

    public int getBDSize() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Items> realmList = realm.where(Items.class)
                .findAll();
        

        return realmList.size();


    }
}
