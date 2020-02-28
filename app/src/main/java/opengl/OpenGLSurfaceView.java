package opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import fr.lesage.olivier.info503.TirageActivity;

/**
 * Classe représentant le contenu de l'activité tirage.
 */
public class OpenGLSurfaceView extends GLSurfaceView {
    /**
     * Renderer de la vue.
     */
    private final OpenGLRenderer mRenderer;

    /**
     * Ctor.
     * @param context Context dans lequel est créer la GLSurfaceView.
     */
    public OpenGLSurfaceView(TirageActivity context) {
        super(context);

        setEGLContextClientVersion(2);

        this.mRenderer = new OpenGLRenderer(context);

        setRenderer(this.mRenderer);

        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        //requestRender();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.mRenderer.skip();
                break;
                default:
                    break;
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mRenderer.onResume();
    }
}
