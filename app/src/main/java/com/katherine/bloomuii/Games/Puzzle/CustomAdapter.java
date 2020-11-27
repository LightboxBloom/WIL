package com.katherine.bloomuii.Games.Puzzle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

/*
Class name: Custom Adapter
Reason: This class is used to organize the data in the Array list
        of buttons.
Parameters:
Team Member: Cameron White
Date: 10 June 2020
Version: 2
*/


public class CustomAdapter extends BaseAdapter {
    private ArrayList<Button> mButtons;
    private int mColumnWidth, mColumnHeight;

    //constructor method
    public CustomAdapter(ArrayList<Button> buttons, int columnWidth, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;

    }

    //return size of current array list
    @Override
    public int getCount() {
        return mButtons.size();
    }

    //return the current item
    @Override
    public Object getItem(int position) {
        return (Object) mButtons.get(position);
    }

    //return the current item ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    //render and recycle view for each button
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null){
            button = mButtons.get(position);
        }else{
            button = (Button) convertView;
        }

        android.widget.AbsListView.LayoutParams params = new android.widget.
                AbsListView.LayoutParams(mColumnWidth,mColumnHeight);
        button.setLayoutParams(params);

        return button;
    }
}
