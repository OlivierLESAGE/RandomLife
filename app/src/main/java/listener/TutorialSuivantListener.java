package listener;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;

import fr.lesage.olivier.info503.TutorialActivity;

public class TutorialSuivantListener implements View.OnClickListener {
    private Activity activity;
    private ViewPager view;
    private int nbSlide;

    public TutorialSuivantListener(Activity activity, ViewPager view, int nbSlide) {
        this.activity = activity;
        this.view = view;
        this.nbSlide = nbSlide;
    }

    @Override
    public void onClick(View v) {
        int nextSlide = this.view.getCurrentItem()+1;

        if(nextSlide < this.nbSlide) {
            view.setCurrentItem(nextSlide);
        } else {
            this.activity.finish();
        }
    }
}
