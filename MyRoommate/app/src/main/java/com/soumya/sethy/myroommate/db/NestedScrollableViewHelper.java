package com.soumya.sethy.myroommate.db;

import android.support.v4.widget.NestedScrollView;
import android.view.View;

import com.sothree.slidinguppanel.ScrollableViewHelper;

/**
 * Created by soumy on 7/26/2016.
 */

public class NestedScrollableViewHelper extends ScrollableViewHelper {
    View mScrollableView =null;
    public int getScrollableViewScrollPosition(View scrollableView, boolean isSlidingUp) {

        if (mScrollableView instanceof NestedScrollView) {
            if(isSlidingUp){
                return mScrollableView.getScrollY();
            } else {
                NestedScrollView nsv = ((NestedScrollView) mScrollableView);
                View child = nsv.getChildAt(0);
                return (child.getBottom() - (nsv.getHeight() + nsv.getScrollY()));
            }
        } else {
            return 0;
        }
    }
}