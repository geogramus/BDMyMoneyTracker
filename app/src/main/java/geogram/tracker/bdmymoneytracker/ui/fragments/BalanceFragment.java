package geogram.tracker.bdmymoneytracker.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import geogram.tracker.bdmymoneytracker.common.DiagramView;
import geogram.tracker.bdmymoneytracker.R;
import geogram.tracker.bdmymoneytracker.mvp.presenter.ItemsPresenter;

/**
 * Created by geogr on 08.02.2018.
 */

public class BalanceFragment extends Fragment {

    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.expense)
    TextView expense;
    @BindView(R.id.income)
    TextView income;
    @BindView(R.id.diagram)
    DiagramView diagramView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.balance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        balance.setText(getString(R.string.price, ItemsPresenter.getTotalIncome() - ItemsPresenter.getTotalExpense()));
        expense.setText(getString(R.string.price, ItemsPresenter.getTotalExpense()));
        income.setText(getString(R.string.price, ItemsPresenter.getTotalIncome()));
        diagramView.update(ItemsPresenter.getTotalExpense(), ItemsPresenter.getTotalIncome());
    }



}
