package com.example.myapplicationtranslator.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.marktony.translator.R;

/**
 * Created by liuht on 2017/3/13.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private Toolbar toolbar;

    private NotebookFragment notebookFragment;
    private DailyOneFragment dailyOneFragment;
    private TranslateFragment translateFragment;

    private static final String ACTION_NOTEBOOK = "com.example.myapplicationtranslator.notebook";
    private static final String ACTION_DAILY_ONE = "com.example.myapplicationtranslator.dailyone";

    @Override
    public void onCreate(Bundle savedInstacneState){
       super.onCreate(savedInstacneState);
        setContentView(R.layout.activity_main);

        initViews();

        if(savedInstacneState!=null){
            FragmentManager manager = getSupportFragmentManager();
            notebookFragment = (NotebookFragment) manager.getFragment(savedInstacneState,"notebookFragment");
            dailyOneFragment = (DailyOneFragment) manager.getFragment(savedInstacneState,"dailyoneFragment");
            translateFragment = (TranslateFragment) manager.getFragment(savedInstacneState,"translateFragment");
        }else{
            notebookFragment = new NotebookFragment();
            dailyOneFragment = new DailyOneFragment();
            translateFragment = new TranslateFragment();
        }

        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction()
                .add(R.id.container_main,translateFragment,"translateFragment")
                .commit();

        manager.beginTransaction()
                .add(R.id.container_main,dailyOneFragment,"dailyoneFragment")
                .commit();

        manager.beginTransaction()
                .add(R.id.container_main,notebookFragment,"notebookFragment")
                .commit();

        Intent intent = getIntent();
        if(intent.getAction().equals(ACTION_NOTEBOOK)){
            showHideFragment(2);
        }else if(intent.getAction().equals(ACTION_DAILY_ONE)){
            showHideFragment(1);
        }else{
            showHideFragment(0);
        }
    }

    private void initViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close
        );
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_search){
            startActivity(new Intent(MainActivity.this,SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.nav_translate){
            showHideFragment(0);
        }else if(id == R.id.nav_daily){
            showHideFragment(1);
        }else if(id == R.id.nav_notebook){
            showHideFragment(2);
        }else if(id == R.id.nav_setting){
            startActivity(new Intent(MainActivity.this,SettingsPreferenceActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if(translateFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"translateFragment",translateFragment);
        }
        if(notebookFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"notebookFragment",notebookFragment);
        }
        if(dailyOneFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"dailyoneFragment",dailyOneFragment);
        }
    }

    /**
     * show or hide the fragment
     * and handle other operations like set toolbar's title
     * set the navigation's checked item
     * @param position which fragment to show, only 3 vlaues at this time
     *                 0  for translateFragment
     *                 1  for dailyoneFragment
     *                 2  for notebookFragment
     */
    private void showHideFragment(@IntRange(from = 0, to = 2) int position){
       FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().hide(translateFragment).commit();
        manager.beginTransaction().hide(dailyOneFragment).commit();
        manager.beginTransaction().hide(notebookFragment).commit();

        if(position == 0){
            manager.beginTransaction().show(translateFragment).commit();
            toolbar.setTitle(R.string.app_name);
            navigationView.setCheckedItem(R.id.nav_translate);
        }else if(position == 1){
            manager.beginTransaction().show(dailyOneFragment).commit();
            toolbar.setTitle(R.string.daily_one);
            navigationView.setCheckedItem(R.id.nav_daily);
        }else if(position == 2){
            manager.beginTransaction().show(notebookFragment).commit();
            toolbar.setTitle(R.string.notebook);
            navigationView.setCheckedItem(R.id.nav_notebook);
        }
   }
}
