package com.jacmobile.technews.ui;

/**
 * Created by alex on 10/20/14.
 */
public interface Navigator
{
    public void onTransition(int... which);
    public void navListClick(int position);
    public void setNavigationTitle(String title);
}