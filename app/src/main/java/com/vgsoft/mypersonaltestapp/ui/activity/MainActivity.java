package com.vgsoft.mypersonaltestapp.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.vgsoft.mypersonaltestapp.R;
import com.vgsoft.mypersonaltestapp.realm.RealmController;
import com.vgsoft.mypersonaltestapp.ui.adapters.DrawerAdapter;
import com.vgsoft.mypersonaltestapp.ui.fragments.BaseFragment;
import com.vgsoft.mypersonaltestapp.ui.fragments.InfoFragment;
import com.vgsoft.mypersonaltestapp.ui.fragments.MainFragment;
import com.vgsoft.mypersonaltestapp.ui.models.ItemModel;

public class MainActivity extends AppCompatActivity {

    private String[] mItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private Toolbar mToolbar;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = getTitle();
        mItemTitles = getResources().getStringArray(R.array.drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        new RealmController(getApplicationContext()).removeAll();
        setupToolbar();

        ItemModel[] dItems = fillDataModel();

        header = createHeader(null);

        DrawerAdapter adapter = new DrawerAdapter(this, R.layout.item_row, dItems);
        mDrawerListView.addHeaderView(header);
        mDrawerListView.setAdapter(adapter);
        mDrawerListView.setOnItemClickListener(new ItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();
        setFragment(new MainFragment(), 1);

    }

    // формируем массив с данными для адаптера
    private ItemModel[] fillDataModel() {
        return new ItemModel[]{
                new ItemModel(R.drawable.ic_video_rate, mItemTitles[0]),
                new ItemModel(R.drawable.ic_info, mItemTitles[1])
        };
    }

    // по клику на элемент списка устанавливаем нужный фрагмент в контейнер
    private class ItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BaseFragment fragment = null;

            // на основании выбранного элемента меню
            // вызываем соответственный ему фрагмент
            switch (position) {
                case 0:
                    mDrawerLayout.closeDrawer(mDrawerListView);
                    break;
                case 1:
                    fragment = new MainFragment();
                    break;
                case 2:
                    fragment = new InfoFragment();
                    break;

                default:
                    break;
            }
            setFragment(fragment, position);
        }
    }

    private void setFragment(BaseFragment fragment, int position) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().addToBackStack(fragment.getClass().getName()).replace(R.id.content_frame, fragment).commit();

            mDrawerListView.setItemChecked(position, true);
            mDrawerListView.setSelection(position);
            setTitle(mItemTitles[--position]);
            mDrawerLayout.closeDrawer(mDrawerListView);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count <= 1) {
            //additional code
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        // Это необходимо для изменения иконки на основании текущего состояния
        mDrawerToggle.syncState();
    }

    View createHeader(String text) {
        View v = getLayoutInflater().inflate(R.layout.drawer_header_layout, null);
        if (text != null) {
            ((TextView) v.findViewById(R.id.tvText)).setText(text);
        }
        return v;
    }
}
