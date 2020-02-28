package opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import fr.lesage.olivier.info503.R;
import fr.lesage.olivier.info503.TirageActivity;
import model.AnimationInterpoler;
import model.AnimationKey;
import model.Transformable;

/**
 * Classe représentant le "canvas".
 */
class OpenGLRenderer implements GLSurfaceView.Renderer {
    /**
     * Nombre d'images par secondes.
     */
    public static final int FRAMERATE = 30;

    /**
     * Hauteur des cartes.
     */
    public static final float HAUTEUR_CARTE = 0.15f;

    /**
     * Vitesse de la carte.
     */
    public static final int VITESSE = 25;

    /**
     * Context.
     */
    private TirageActivity context;

    /**
     * Carte numéros 1.
     */
    private OpenGLDrawableAnimated carte1;

    /**
     * Carte numéros 1.
     */
    private OpenGLDrawableAnimated carte2;

    /**
     * Shader utilisé pour dessiner les cartes.
     */
    private OpenGLShader shader;

    /**
     * Texture des cartes.
     */
    private OpenGLTexture texture;

    /**
     * Maillage des cartes.
     */
    private OpenGLMeshFromFile mesh;

    /**
     * Matrice de projection.
     */
    private float projectionMatrix[];

    /**
     * Matrice représentant la caméra.
     */
    private float viewMatrix[];

    /**
     * Matrice Model View Projection.
     */
    private float mvpMatrix[];

    /**
     * Temps auquel la frame commence et termine..
     */
    private long start, end;

    /**
     * Les différentes position que vont prendre les cartes.
     */
    private Transformable pointGauche, pointMilieuHaut, pointMilieuBas, pointDroit, cameraFront;

    /**
     * Etat du canvas.
     */
    private Etat etat;

    /**
     * Méthode permettant de réinitialiser l"état du canvas lorsque l'activité et relancer au travers de la méthode onResume.
     */
    public void onResume() {
        this.etat = Etat.DEBUT;
    }

    /**
     * Enumération des etats du Renderer.
     */
    private enum Etat {DEBUT, DOIT_AVANCER, EST_A_LA_FIN, FINIS};

    /**
     * Ctor.
     * @param context Context dans lequel est créer le Renderer.
     */
    public OpenGLRenderer(TirageActivity context) {
        this.context = context;

        this.projectionMatrix = new float[16];
        this.viewMatrix = new float[16];
        this.mvpMatrix = new float[16];

        this.mesh = new OpenGLMeshFromFile(this.context, R.raw.carte);

        this.pointGauche = new Transformable(-0.62f, 0.0f, 0.0f,0.0f,180.0f,180.0f,1.0f,1.0f,1.0f);
        this.pointMilieuHaut = new Transformable(0.0f, 0.0f, HAUTEUR_CARTE,0.0f,180.0f,180.0f,1.0f,1.0f,1.0f);
        this.pointMilieuBas = new Transformable(0.0f, 0.0f, -HAUTEUR_CARTE,0.0f,180.0f,180.0f,1.0f,1.0f,1.0f);
        this.pointDroit = new Transformable(0.62f, 0.0f, 0.0f,0.0f,180.0f,180.0f,1.0f,1.0f,1.0f);
        this.cameraFront = new Transformable(0.0f, 0.0f, -0.9f,0.0f,0.0f,180.0f,1.0f,1.0f,1.0f);


        this.etat = Etat.DEBUT;
    }

    /**
     * Méthode permettant de passer l'animation.
     */
    public void skip() {
        if(this.etat == Etat.DEBUT) {
            this.etat = Etat.DOIT_AVANCER;
        } else if (this.etat == Etat.FINIS) {
            this.context.finish();
        }
    }

    /**
     * Méthode permettant de mettre les animaitons de fin au cartes.
     */
    public void setEndAnimation() {
        AnimationInterpoler anim1 = new AnimationInterpoler();
        anim1.addKey(new AnimationKey(pointMilieuHaut, 0));
        anim1.addKey(new AnimationKey(cameraFront, 100));

        this.carte1.setAnimation(anim1);

        AnimationInterpoler anim2 = new AnimationInterpoler();
        anim2.addKey(new AnimationKey(pointMilieuBas, 0));
        anim2.addKey(new AnimationKey(pointMilieuBas, 10 ));

        this.carte2.setAnimation(anim2);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClearColor(0.049707f, 0.082283f,0.462077f,1.0f);

        this.shader = new OpenGLShader(context, R.raw.vertex_shader, R.raw.fragment_shader);

        this.texture = new OpenGLTexture(context, R.drawable.card_texture, this.context.getElement(), 512, 122, 513, 804);

        int nbAlea = (int) (Math.random() * 5 +1);

        AnimationInterpoler animCarte1 = new AnimationInterpoler();
        animCarte1.addKey(new AnimationKey(this.pointMilieuHaut, 0));
        for(int i = 0; i < nbAlea*4; i+=4) {
            animCarte1.addKey(new AnimationKey(this.pointDroit, VITESSE/2 + VITESSE * i));
            animCarte1.addKey(new AnimationKey(this.pointMilieuBas, VITESSE/2 + VITESSE * (i+1)));
            animCarte1.addKey(new AnimationKey(this.pointGauche, VITESSE/2 + VITESSE * (i+2)));
            animCarte1.addKey(new AnimationKey(this.pointMilieuHaut, VITESSE/2 + VITESSE * (i+3)));
        }

        this.carte1 = new OpenGLDrawableAnimated(this.mesh, this.shader, this.texture, animCarte1);

        AnimationInterpoler animCarte2 = new AnimationInterpoler();
        animCarte2.addKey(new AnimationKey(this.pointMilieuBas, 0));
        for(int i = 0; i < nbAlea*4; i+=4) {
            animCarte2.addKey(new AnimationKey(this.pointGauche, VITESSE/2 + VITESSE * i));
            animCarte2.addKey(new AnimationKey(this.pointMilieuHaut, VITESSE/2 + VITESSE * (i+1)));
            animCarte2.addKey(new AnimationKey(this.pointDroit, VITESSE/2 + VITESSE * (i+2)));
            animCarte2.addKey(new AnimationKey(this.pointMilieuBas, VITESSE/2 + VITESSE * (i+3)));
        }

        this.carte2 = new OpenGLDrawableAnimated(this.mesh, this.shader, this.texture, animCarte2);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ration = (float) width/height;

        float zoom = 1.5f;
        Matrix.frustumM(projectionMatrix, 0, -ration*zoom, ration*zoom, -1*zoom, 1*zoom, 2.f, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        start = SystemClock.currentThreadTimeMillis();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        /*Set up de la camera*/
        Matrix.setLookAtM(this.viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(this.mvpMatrix, 0, this.projectionMatrix, 0, this.viewMatrix, 0);

        switch (this.etat) {
            case DEBUT:
                if (this.carte1.animationIsFinish() && this.carte2.animationIsFinish()) {
                    this.setEndAnimation();
                    this.etat = Etat.EST_A_LA_FIN;
                }
                break;
            case DOIT_AVANCER:
                this.setEndAnimation();
                this.etat = Etat.EST_A_LA_FIN;
                break;
            case EST_A_LA_FIN:
                if (this.carte1.animationIsFinish() && this.carte2.animationIsFinish()) {
                    this.etat = Etat.FINIS;
                }
        }

        this.carte1.update();
        this.carte1.draw(this.mvpMatrix);

        this.carte2.update();
        this.carte2.draw(this.mvpMatrix);

        end = SystemClock.currentThreadTimeMillis();

        if(end - start < 1000/60) SystemClock.sleep(1000/FRAMERATE - (end - start));
    }
}
