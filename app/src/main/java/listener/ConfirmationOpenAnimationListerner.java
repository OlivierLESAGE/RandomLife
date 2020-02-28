package listener;

import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

public class ConfirmationOpenAnimationListerner implements Animation.AnimationListener {
    LinearLayout layout;

    public ConfirmationOpenAnimationListerner(LinearLayout layout) {
        this.layout = layout;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        this.layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
