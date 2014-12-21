package com.jacmobile.technews.ui;


import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jacmobile.technews.R;

/**
 * Created by alex on 11/9/14.
 */
public class ActionBarManager implements AdapterView.OnItemClickListener
{
    private String[] drawerStrings = {"Featured", "Profile", "Settings"};
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private View actionBar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    public ActionBarManager(Toolbar toolbar)
    {
        this.toolbar = toolbar;
        onCreate();
    }

    private RootActivity getActivity()
    {
        return ((RootActivity) this.toolbar.getContext());
    }

    private void initDrawer()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        getActivity().setSupportActionBar(toolbar);
        this.actionBar = layoutInflater.inflate(R.layout.action_bar, toolbar, false);
        toolbar.addView(actionBar);

        ViewCompat.setElevation(toolbar, 5f);
        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setScrimColor(Color.parseColor("#66000000"));
        this.actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        final ListView mDrawerList = (ListView) getActivity().findViewById(R.id.list_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.drawer_item, drawerStrings));
        mDrawerList.setOnItemClickListener(this);
    }

    private String getDrawerTitle()
    {
        String brand = Build.BRAND.substring(0, 1).toUpperCase() + Build.BRAND.substring(1);
        return brand + " " + Build.MODEL;
    }

    public void closeDrawer()
    {
        this.drawer.closeDrawers();
    }

    public void onPostCreate()
    {
        actionBarDrawerToggle.syncState();
    }

    public void onCreate()
    {
        this.initDrawer();
    }

    public void onStop()
    {
        drawer.closeDrawers();
    }


    public void onConfigurationChanged(Configuration newConfig)
    { // <---- added
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setActionBarTitle(String title)
    {
        ((TextView) actionBar.findViewById(R.id.action_bar_title)).setText(title);
    }

    public boolean isDrawerOpen()
    {
        return drawer.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        drawer.closeDrawers();
        ((DrawerClickListener) getActivity()).navListClick(position);
    }
}