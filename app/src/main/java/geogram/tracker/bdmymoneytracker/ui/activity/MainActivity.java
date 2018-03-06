package geogram.tracker.bdmymoneytracker.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import geogram.tracker.bdmymoneytracker.R;
import geogram.tracker.bdmymoneytracker.model.Items;
import geogram.tracker.bdmymoneytracker.ui.fragments.BalanceFragment;
import geogram.tracker.bdmymoneytracker.ui.fragments.ItemsFragment;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.pages)
    ViewPager pages;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private static int fragmetnIndex=0;
    public static String Income="income";
    public static String Expense="expense";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @Override
    protected void onResume() {
        super.onResume();

        setSupportActionBar(toolbar);
        pages.setAdapter(new MainPagerAdapter());
        tabs.setupWithViewPager(pages);
        //    }
    }
    public static int getFragmentIndex(){
        return fragmetnIndex;
    }

    private class MainPagerAdapter extends FragmentPagerAdapter {
        
        private final String[] titles;

        MainPagerAdapter() {
            super(getSupportFragmentManager());
            titles = getResources().getStringArray(R.array.main_pager_titles);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == getCount() - 1)
                return new BalanceFragment();
            final ItemsFragment fragment = new ItemsFragment();
            Bundle args = new Bundle();
            if (position == getCount() - 2) {
                args.putString(ItemsFragment.ARG_TYPE, Items.TYPE_INCOME);
            } else {
                args.putString(ItemsFragment.ARG_TYPE, Items.TYPE_EXPENSE);
            }
            fragment.setArguments(args);
            return fragment;

        }


        @Override
        public CharSequence getPageTitle(int position) {

            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;

        }
    }
}
