package listener;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import java.util.Observable;

import fr.lesage.olivier.info503.R;
import fr.lesage.olivier.info503.TutorialActivity;

public class TutorialPageChangeListener extends Observable implements ViewPager.OnPageChangeListener {

    public TutorialPageChangeListener(TutorialActivity app) {
        this.addObserver(app);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        setChanged();
        notifyObservers();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
