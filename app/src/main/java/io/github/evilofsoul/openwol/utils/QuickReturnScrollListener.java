package io.github.evilofsoul.openwol.utils;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Yevhenii on 28.08.2016.
 */
public abstract class QuickReturnScrollListener extends RecyclerView.OnScrollListener {
    int scrollDist = 0;
    boolean isVisible = true;

    static final float MINIMUM = 25;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (isVisible && scrollDist > MINIMUM) {
            hide();
            scrollDist = 0;
            isVisible = false;
        }
        else if (!isVisible && scrollDist < -MINIMUM) {
            show();
            scrollDist = 0;
            isVisible = true;
        }
        if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
            scrollDist += dy;
        }
    }

    public abstract void show();
    public abstract void hide();
}
