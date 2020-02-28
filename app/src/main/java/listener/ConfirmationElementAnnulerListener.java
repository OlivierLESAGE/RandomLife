package listener;

import android.view.View;

import model.ElementDeleteSystem;

public class ConfirmationElementAnnulerListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        ElementDeleteSystem.undeleteElement();
    }
}
