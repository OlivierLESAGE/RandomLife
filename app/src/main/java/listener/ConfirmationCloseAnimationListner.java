package listener;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

public class ConfirmationCloseAnimationListner implements Animation.AnimationListener {
    LinearLayout layout;

    public ConfirmationCloseAnimationListner(LinearLayout layout) {
        this.layout = layout;
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        this.layout.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
