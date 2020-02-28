package listener;

import android.app.Activity;
import android.view.View;

import fr.lesage.olivier.info503.TutorialActivity;

public class TutorialPasserListener implements View.OnClickListener {
    private Activity activity;

    public TutorialPasserListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.finish();
    }
}
