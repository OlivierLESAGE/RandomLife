package fr.lesage.olivier.info503;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Observable;
import java.util.Observer;

import listener.TutorialPageChangeListener;
import listener.TutorialPasserListener;
import listener.TutorialSuivantListener;
import model.TutorialSlideAdapter;

/**
 * Activité permettant d'afficher un tutoriel.
 */
public class TutorialActivity extends AppCompatActivity implements Observer {
    /**
     * Indice du tutoriel du démarrage.
     */
    public static final int TUTORIAL_START = 1;

    /**
     * Indice du tutoriel d'édition de liste.
     */
    public static final int TUTORIAL_ELEMENT = 2;

    /**
     * Nombre de slide du tutoriel.
     */
    private int nbSlide;

    /**
     * widget dans lequel est affiché le tutoriel.
     */
    private ViewPager viewPager;

    /**
     * Layout contenant les points qui permettent de savoir a quel slide on en est.
     */
    private LinearLayout dotLayout;

    /**
     * Bouton permettant de passer le tutoriel.
     */
    private Button passer;

    /**
     * Bouton permettant de passer une slide.
     */
    private Button suivant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        int subject = getIntent().getExtras().getInt("subject");

        int titleID, textID, imageID;

        // On récupère les text et les images en fonction de l'indice du tutoriel.
        switch (subject) {
            case TUTORIAL_START:
                titleID = R.array.tutorial_start_title_array;
                textID = R.array.tutorial_start_text_array;
                imageID = R.array.tutorial_start_image_array;
                this.nbSlide = getResources().getInteger(R.integer.tutorial_start_nb_slide);
                break;
            case TUTORIAL_ELEMENT:
                titleID = R.array.tutorial_element_title_array;
                textID = R.array.tutorial_element_text_array;
                imageID = R.array.tutorial_element_image_array;
                this.nbSlide = getResources().getInteger(R.integer.tutorial_element_nb_slide);
                break;
                default:
                    titleID = R.array.tutorial_start_title_array;
                    textID = R.array.tutorial_start_text_array;
                    imageID = R.array.tutorial_start_image_array;
                    this.nbSlide = getResources().getInteger(R.integer.tutorial_start_nb_slide);
                    break;
        }

        // Gestion de l'interface.
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);

        this.passer = findViewById(R.id.buttonPasser);
        this.passer.setOnClickListener(new TutorialPasserListener(this));

        this.suivant = findViewById(R.id.buttonSuivant);
        this.suivant.setOnClickListener(new TutorialSuivantListener(this, this.viewPager, this.nbSlide));

        this.viewPager.setAdapter(new TutorialSlideAdapter(this, getLayoutInflater(), this.nbSlide, titleID, textID, imageID));
        this.viewPager.addOnPageChangeListener(new TutorialPageChangeListener(this));

        this.dotLayout = findViewById(R.id.dotslayout);
        setDot(0);
    }

    /**
     * Méthode permettant d'afficher les points.
     * @param position Numéros de la slide courante.
     */
    public void setDot(int position) {
        ImageView image;
        if (this.dotLayout != null) {
            // On retire tous les points
            this.dotLayout.removeAllViews();

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);

            // Pour chaque slide ...
            for(int i = 0; i < this.nbSlide; i++) {
                image = new ImageView(this);
                // Si c'est la slide courante ...
                if(i != position)
                    // On met un point blanc
                    image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_inactive));
                else
                    // Sinon un point gris.
                    image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_active));
                this.dotLayout.addView(image, params);
            }
        }
    }

    /**
     * Méthode perttant de récuperer le nombre de slides.
     * @return Le nombre de slides.
     */
    public int getNbSlide() {
        return this.nbSlide;
    }

    @Override
    public void update(Observable o, Object arg) {
        int position = this.viewPager.getCurrentItem();
        setDot(position);

        if(position < this.nbSlide-1) {
            suivant.setText(R.string.suivant);
            passer.setVisibility(View.VISIBLE);
        } else {
            suivant.setText(R.string.commencer);
            passer.setVisibility(View.INVISIBLE);
        }
    }
}
