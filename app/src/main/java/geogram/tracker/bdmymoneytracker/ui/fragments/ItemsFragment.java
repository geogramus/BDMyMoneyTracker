package geogram.tracker.bdmymoneytracker.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import geogram.tracker.bdmymoneytracker.MyApplication;
import geogram.tracker.bdmymoneytracker.R;
import geogram.tracker.bdmymoneytracker.model.Items;
import geogram.tracker.bdmymoneytracker.mvp.presenter.ItemsPresenter;
import geogram.tracker.bdmymoneytracker.mvp.view.BaseItemsView;
import geogram.tracker.bdmymoneytracker.common.ItemsAdapter;
import geogram.tracker.bdmymoneytracker.ui.activity.AddItemActivity;

import static android.app.Activity.RESULT_OK;
import static geogram.tracker.bdmymoneytracker.ui.activity.AddItemActivity.RC_ADD_ITEM;

/**
 * Created by geogr on 08.02.2018.
 */

public class ItemsFragment extends BaseFragment implements BaseItemsView {
    public static final String ARG_TYPE = "type";
    @BindView(R.id.refresh)
    SwipeRefreshLayout swp;
    @BindView(R.id.add)
    FloatingActionButton fab;
    @BindView(R.id.items)
    RecyclerView recyclerView;
    @InjectPresenter
    ItemsPresenter mPresenter;
    private ActionMode actionMode;

    ItemsAdapter adapter = new ItemsAdapter();

    @Inject
    Context context;
    private String itemType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.items, container, false);
        ButterKnife.bind(this, view);
        MyApplication.getsApplicationComponent().inject(this);
        recyclerView.setAdapter(adapter);
        itemType= (String) getArguments().get(ARG_TYPE);
        showItems();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                if (actionMode != null) return;
                actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
                adapter.toggleSelection(recyclerView.getChildLayoutPosition(recyclerView.findChildViewUnder(e.getX(), e.getY())));
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (actionMode != null)
                    adapter.toggleSelection(recyclerView.getChildLayoutPosition(recyclerView.findChildViewUnder(e.getX(), e.getY())));
                return super.onSingleTapConfirmed(e);
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        showItems();
        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                showItems();
                hideRefreshing();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewItem();
            }
        });
    }

    @Override
    protected int getMainContentLayout() {
        return R.layout.items;
    }

    @Override
    public int onCreateToolbarTitle() {
        return R.string.incomes;
    }


    @Override
    public void hideRefreshing() {
        swp.setRefreshing(false);
    }

    @Override
    public void showError(String mewssage) {
        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
    }

    List<Items> list;

    @Override
    public void showItems() {
        adapter.clear();
        list = mPresenter.getItemsFromDb(itemType);
        adapter.addAll(list);
    }

    @Override
    public void addNewItem() {
        Intent intent = new Intent(context, AddItemActivity.class);
        startActivityForResult(intent, RC_ADD_ITEM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_ADD_ITEM && resultCode == RESULT_OK) {
            Items item = (Items) data.getSerializableExtra(AddItemActivity.RESULT_ITEM);
            item.setType(itemType);
            item.setId(mPresenter.getBDSize());
            mPresenter.addNewItemToBD(item);
            showItems();
            Toast toast = Toast.makeText(context, item.getName(), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.items, menu);
            fab.setVisibility(View.GONE);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_remove:
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.app_name)
                            .setMessage(R.string.confirm_remove)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    for (int i = adapter.getSelectedItems().size() - 1; i >= 0; i--)
                                        mPresenter.deleteItemFromDB(adapter.remove(adapter.getSelectedItems().get(i)));
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, null)
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    actionMode.finish();
                                }
                            })
                            .show();
                    return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            adapter.clearSeclections();
            fab.setVisibility(View.VISIBLE);
        }
    };
}
