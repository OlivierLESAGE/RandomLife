package fr.lesage.olivier.info503;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import opengl.OpenGLMeshFromFile;
import opengl.OpenGLSurfaceView;

/**
 * Activité permettant d'affiché le résultat d'un tirage.
 *
 * Cette activité utilise OpenGL ES 2.0
 */
public class TirageActivity extends AppCompatActivity {
    /**
     * Widget dans lequel est affiché l'animation.
     */
    private OpenGLSurfaceView mGLView;

    /**
     * Résultat du tirage.
     */
    private String element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.element = getIntent().getStringExtra("nom_element");
        this.mGLView = new OpenGLSurfaceView(this);

        setContentView(this.mGLView);

        /*findViewById(R.id.tirage_view).setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        ((TextView) findViewById(R.id.resultat_textView)).setText();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mGLView.onResume();
    }

    /**
     *
     * @return Le résultat du tirage.
     */
    public String getElement() {
        return element;
    }

    /*@Override
    public boolean onTouchEvent (MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN) {
            finish();
        }
        return true;
    }*/
}
