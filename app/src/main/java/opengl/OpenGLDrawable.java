package opengl;

import android.opengl.Matrix;

import model.Transformable;

/**
 * Classe représentant un objet 3D affichable avec OpenGL.
 */
public class OpenGLDrawable {
    /**
     * Matrice permettant de dessiner l'objet.
     */
    private float mvpMatrix[];

    /**
     * Maillage de l'objet.
     */
    private OpenGLMesh mesh;

    /**
     * Shader permettant de dessiner l'objet.
     */
    private OpenGLShader shader;

    /**
     * Texture de l'objet.
     */
    private OpenGLTexture texture;

    /**
     * Position, rotation et grossissement de l'objet.
     */
    protected Transformable trans;

    /**
     * Ctor.
     * @param mesh Maillage de l'objet.
     * @param shader Shader avec lequel l'objet seras dessiner.
     * @param texture Texture de l'objet.
     * @param px Position sur l'axe X.
     * @param py Position sur l'axe Y.
     * @param pz Position sur l'axe Z.
     * @param rx Rotation sur l'axe X.
     * @param ry Rotation sur l'axe Y.
     * @param rz Rotation sur l'axe Z.
     * @param sx Grosssissement sur l'axe X.
     * @param sy Grosssissement sur l'axe Y.
     * @param sz Grosssissement sur l'axe Z.
     */
    public OpenGLDrawable(OpenGLMesh mesh, OpenGLShader shader, OpenGLTexture texture, float px, float py, float pz, float rx, float ry, float rz, float sx, float sy, float sz) {
        this.mesh = mesh;
        this.shader = shader;
        this.texture = texture;

        this.trans = new Transformable(px, py, pz, rx, ry, rz, sx, sy, sz);

        this.mvpMatrix = new float[16];
    }

    /**
     * Méthode permettant de dessiner l'objet.
     * @param vpMatrix Matrice View Projection. (résultat de la multiplication de la view et la projection)
     */
    public void draw(float vpMatrix[]) {
        // Réinitialisation de la mtrice.
        Matrix.setIdentityM(this.mvpMatrix, 0);

        // Grossissement de la matrice.
        Matrix.scaleM(this.mvpMatrix, 0, this.trans.getScaleX(), this.trans.getScaleY(), this.trans.getScaleZ());

        // Rotation de la matrice
        Matrix.rotateM(this.mvpMatrix, 0, this.trans.getRotationX(), 1, 0, 0);
        Matrix.rotateM(this.mvpMatrix, 0, this.trans.getRotationY(), 0, 1, 0);
        Matrix.rotateM(this.mvpMatrix, 0, this.trans.getRotationZ(), 0, 0, 1);

        // Translation de la matrice
        Matrix.translateM(this.mvpMatrix, 0, this.trans.getPositionX(), this.trans.getPositionY(), this.trans.getPositionZ());

        // Multiplication de la matrice avec la view et la projection
        Matrix.multiplyMM(this.mvpMatrix, 0,  vpMatrix, 0,this.mvpMatrix, 0);

        // Affichage du maillage à l'écran avec le shader et la texture de l'objet.
        this.mesh.draw(this.shader, this.texture, this.mvpMatrix);
    }

    /**
     * Méthode permettant de mettre à jour l'objet.
     */
    public void update() {
    }
}
