package com.jacmobile.technews.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jacmobile.technews.R;

/**
 * Created by dgaffey on 12/1/14.
 */
public class LoadingRelativeLayout extends RelativeLayout
{
    public LoadingRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.progress, this);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LoadingRelativeLayout,
                0, 0);

        try {
            String loadingText = a.getString(R.styleable.LoadingRelativeLayout_loadingText);
            ((TextView) this.findViewById(R.id.txt_progress)).setText(loadingText);
        } finally {
            a.recycle();
        }
    }

    public LoadingRelativeLayout(Context context, String loadingText)
    {
        super(context);
        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        LayoutInflater.from(context).inflate(R.layout.progress, this);
        ((TextView) this.findViewById(R.id.txt_progress)).setText(loadingText);
    }

    /**
     * Set the layout view into a loading state
     *
     * @param loadingText The text to display while loading
     */
    public void setLoadingView(String loadingText)
    {
        this.removeAllViews();
        LayoutInflater.from(getContext()).inflate(R.layout.progress, this);
        ((TextView) this.findViewById(R.id.txt_progress)).setText(loadingText);
    }

    public void updateView(int layoutId)
    {
        this.removeAllViews();
        LayoutInflater.from(getContext()).inflate(layoutId, this);
    }

    public void updateView(View update)
    {
        this.removeAllViews();
        this.addView(update);
    }

//    public void setEmptyView(String text, String btnText, OnClickListener clickListener)
//    {
//        this.removeAllViews();
//        LayoutInflater.from(getContext()).inflate(R.layout.empty_action, this);
//        ((TextView) this.findViewById(R.id.txt_empty_action)).setText(text);
//        Button emptyBtn = (Button) this.findViewById(R.id.btn_empty_action);
//        emptyBtn.setText(btnText);
//        emptyBtn.setOnClickListener(clickListener);
//    }
}
