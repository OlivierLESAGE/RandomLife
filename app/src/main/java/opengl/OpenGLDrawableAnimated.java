package opengl;

import android.os.SystemClock;
import android.util.Log;

import model.AnimationInterpoler;

/**
 * Classe représentant un objet 3D animé affichable avec OpenGL.
 */
public class OpenGLDrawableAnimated extends OpenGLDrawable {
    /**
     * Animation de l'objet.
     */
    private AnimationInterpoler anim;

    /**
     * Ctor.
     * @param mesh Maillage de l'objet.
     * @param shader Shader de l'objet.
     * @param texture Texture de l'objet.
     * @param anim ANimation de l'objet.
     */
    public OpenGLDrawableAnimated(OpenGLMesh mesh, OpenGLShader shader, OpenGLTexture texture, AnimationInterpoler anim) {
        super(mesh, shader, texture, 0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f);
        this.setAnimation(anim);
    }

    /**
     * Méthode permettant de savoir si l'animation est finis.
     * @return True si l'animation est finis, sinon False.
     */
    public boolean animationIsFinish() {
        return this.anim.isFinish();
    }

    /**
     * Méthode permettant de changer l'animation de l'objet.
     * @param anim
     */
    public void setAnimation(AnimationInterpoler anim) {
        this.anim = anim;
        this.anim.setTargetTransformable(this.trans);
        this.anim.start();
    }

    @Override
    public void update() {
        super.update();
        this.anim.interpole();
    }
}
