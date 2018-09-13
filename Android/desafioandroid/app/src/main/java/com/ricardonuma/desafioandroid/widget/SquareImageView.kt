package com.ricardonuma.desafioandroid.widget

import android.content.Context
import android.util.AttributeSet
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by ricardonuma on 12/19/17.
 */

class SquareImageView : RoundedImageView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        //setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}