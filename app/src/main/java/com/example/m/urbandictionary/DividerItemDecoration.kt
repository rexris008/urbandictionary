package com.example.m.urbandictionary

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

public class DividerItemDecoration(divider: Drawable) : RecyclerView.ItemDecoration() {
    var mDivider: Drawable

    init {
        mDivider = divider
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) == 0) {
            return
        }

        outRect.top = mDivider.intrinsicHeight
    }

    override fun onDraw(canvas:Canvas, parent:RecyclerView, state:RecyclerView.State) {
        val dividerLeft = parent.getPaddingLeft();
        val dividerRight = parent.getWidth() - parent.getPaddingRight();
        val childCount = parent.getChildCount();
        var i = 0;

        while (i < childCount - 1) {
            val child: View = parent.getChildAt(i);
            val params: RecyclerView.LayoutParams = child.getLayoutParams() as RecyclerView.LayoutParams;
            val dividerTop = child.getBottom() + params.bottomMargin;
            val dividerBottom = dividerTop + mDivider.getIntrinsicHeight();
            mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            mDivider.draw(canvas);
            i++;
        }
    }
}
