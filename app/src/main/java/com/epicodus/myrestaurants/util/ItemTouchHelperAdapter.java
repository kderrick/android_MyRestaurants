package com.epicodus.myrestaurants.util;

/**
 * Created by kylederrick on 5/9/16.
 */
public interface ItemTouchHelperAdapter {
        boolean onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
}
