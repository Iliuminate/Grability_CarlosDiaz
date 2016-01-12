package com.grability.iliuminate.grabilityprueba.ControlClasses;

import android.app.Activity;
import android.content.Context;

import com.grability.iliuminate.grabilityprueba.R;

/**
 * Created by Iliuminate on 11/01/2016.
 */
public class ParameterImageView {

    public int   top, bottom, right, left;

    public ParameterImageView(Context context) {
        top=(int)context.getResources().getDimension(R.dimen.item_margin);
        bottom=(int)context.getResources().getDimension(R.dimen.item_margin);
        right=(int)context.getResources().getDimension(R.dimen.item_margin);
        left=(int)context.getResources().getDimension(R.dimen.item_margin);
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getRight() {
        return right;
    }

    public int getLeft() {
        return left;
    }
}
