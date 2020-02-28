package model;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fr.lesage.olivier.info503.R;
import listener.ConfirmationCloseAnimationListner;
import listener.ConfirmationOpenAnimationListerner;

/**
 * Classe représentant le bandeau qui permet d'annuler une suppression.
 */
public class ConfirmationPopUp implements Observer {
    /**
     * Layout qui contient le bouton "annuler".
     */
    private LinearLayout layout;

    /**
     * Le bouton "annuler".
     */
    private Button button;

    /**
     * La TextView qui est à gauche du bouton "annuler".
     */
    private TextView text;

    /**
     * Activité dans laquel est créer le bandeau.
     */
    private Activity app;

    /**
     * Ctor.
     * @param layout Layout qui contient le bouton "annuler".
     * @param button Le bouton "annuler".
     * @param app Activité dans laquel est créer le bandeau.
     */
    public ConfirmationPopUp(LinearLayout layout, Button button, Activity app) {
        this.layout = layout;
        this.button = button;
        this.app = app;
        this.layout.setVisibility(View.GONE);
        this.text = this.layout.findViewById(R.id.confirmation_text);

        ElementDeleteSystem.addObserverStatic(this);
    }

    /**
     * Méthode permettant d'afficher le bandeau.
     */
    public void showLayout() {
        this.button.setEnabled(true);
        Animation anim = AnimationUtils.loadAnimation(this.app.getApplicationContext(), R.anim.confirmation_open_anim);
        this.layout.setVisibility(View.VISIBLE);
        this.layout.clearAnimation();
        this.layout.setAnimation(anim);
        this.layout.getAnimation().setAnimationListener(new ConfirmationOpenAnimationListerner(this.layout));
        this.layout.startAnimation(anim);

        /*this.button.clearAnimation();
        this.button.setAnimation(anim);
        this.button.startAnimation(anim);*/
    }

    /**
     * Méthode permettant de cacher le bandeau.
     */
    public void hideLayout() {
        Animation anim = AnimationUtils.loadAnimation(this.app.getApplicationContext(), R.anim.confirmation_close_anim);
        this.layout.clearAnimation();
        this.layout.setAnimation(anim);
        this.layout.getAnimation().setAnimationListener(new ConfirmationCloseAnimationListner(this.layout));
        this.layout.startAnimation(anim);

        /*this.button.clearAnimation();
        this.button.setAnimation(anim);
        this.button.startAnimation(anim);*/
    }

    @Override
    public void update(Observable o, Object arg) {
        ArrayList<ElementASupprimer> list =  ElementDeleteSystem.getLastDeletedGroup();
        int nbEle = (list != null) ? list.size() : 0;

        if (nbEle > 0) {
            this.text.setText(this.app.getResources().getQuantityString(R.plurals.confirmation_suppression, nbEle, nbEle));
            this.button.setEnabled(true);
        }
        else {
            this.button.setEnabled(false);
        }
    }
}
