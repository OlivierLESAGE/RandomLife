package opengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Classe représentant un maillage.
 *
 * Un maillage est composé de Vertex (point). 3 Vertex forment une face.
 * Ainsi les vertex sont stockés dans un tableau, les trois premier forment la première face, les 3 suivants forment la deuxième face, ...
 *
 * Pour afficher une texture sur un maillage, chaque point qui compose le maillage est associé à un point en 2D (coordonnées UV).
 * Ainsi chaque point en 3D à une coordonnées UV permettant de savoir sa position sur l'image.
 */
public class OpenGLMesh {
    /**
     * Nombre de coordonnées par vertex.
     */
    public static final int COORDS_PER_VERTEX = 3;

    /**
     * Taille en octet d'un vertex.
     */
    public static final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4;

    /**
     * Taille en octet d'un vertex.
     */
    public static final int TXTURE_STRIDE = 2 * 4;

    /**
     * Buffer contenant les sertices.
     */
    protected FloatBuffer vertexBuffer;
    /**
     * Buffer contenant les coordonnées UV du maillage.
     */
    protected FloatBuffer textureBuffer;

    /**
     * Tableau contenant tous les vertices.
     */
    protected float vertexCoord[];

    /**
     * Tableau contenant toutes les coordonnées UV.
     */
    protected float textureCoord[];

    /**
     * Couleur de l'objet.
     */
    protected float color[];

    /**
     * Nombre de vertex.
     */
    private int vertexCount;

    /**
     * Ctor.
     */
    public OpenGLMesh() {

    }

    /**
     * Ctor.
     * @param vertexCoord Tableau contenant les points.
     * @param textureCoord Tableau contenant les coordonnées UV.
     * @param color Couleur de l'objet.
     */
    public OpenGLMesh(float vertexCoord[], float textureCoord[], float color[]) {
        init(vertexCoord, textureCoord, color);
    }

    /**
     * Méthode permettant d'initaliser le maillage. Charge dans des buffer les points.
     * @param vertexCoord
     * @param textureCoord
     * @param color
     */
    protected void init(float vertexCoord[], float textureCoord[], float color[]) {
        this.vertexCoord = vertexCoord;
        this.vertexCount = vertexCoord.length / COORDS_PER_VERTEX;

        this.textureCoord = textureCoord;

        this.color = color;

        // Création du buffer contenant les vertices.
        ByteBuffer bb = ByteBuffer.allocateDirect(this.vertexCoord.length * 4);
        bb.order(ByteOrder.nativeOrder());

        this.vertexBuffer = bb.asFloatBuffer();
        this.vertexBuffer.put(this.vertexCoord);
        this.vertexBuffer.position(0);

        // Création du buffer contenant les coordonnées UV.
        ByteBuffer tb = ByteBuffer.allocateDirect(this.textureCoord.length * 4);
        tb.order(ByteOrder.nativeOrder());

        this.textureBuffer = tb.asFloatBuffer();
        this.textureBuffer.put(this.textureCoord);
        this.textureBuffer.position(0);
    }

    /**
     * Méthode permettant de dessiner le maillage avec un shader, une texture et une position donnée.
     * @param shader Shader avec lequel le maillage va être dessiné.
     * @param texture Texture avec laquel le maillage ve être dessiner.
     * @param mvpMatrix Position de l'objet.
     */
    public void draw(OpenGLShader shader, OpenGLTexture texture, float mvpMatrix[]) {
        // Linkage du shader.
        int mProgram = shader.getProgram();
        GLES20.glUseProgram(mProgram);

        // Envoi des vertices.
        int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                VERTEX_STRIDE, this.vertexBuffer);

        // Envoi des coordonnées UV.
        int mTextureCoordHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");
        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);
        GLES20.glVertexAttribPointer(mTextureCoordHandle, 2,
                GLES20.GL_FLOAT, false,
                TXTURE_STRIDE, this.textureBuffer);

        // Envoi de la texture
        int mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.getHandle());
        GLES20.glUniform1i(mTextureUniformHandle, 0);

        // Envoi de la couleur
        int mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, this.color, 0);

        // Envoi de la matrice
        int mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Dessine le maillage
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, this.vertexCount);

        // Désactive les buffer.
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTextureUniformHandle);
    }
}
