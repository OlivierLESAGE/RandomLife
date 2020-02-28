package opengl;

import android.content.Context;
import android.opengl.GLES20;

import model.FileIOHelper;

/**
 * Classe représentant un Shader.
 */
public class OpenGLShader {
    /**
     * Entier permettant d'identifier le shader une fois charger par OpenGL.
     */
    private int mProgram;

    /**
     * Ctor.
     * @param context Context dans lequel est crée le shader.
     * @param vertexShaderID ID du fichier contenant le vertex shader.
     * @param fragmentShaderID ID du fichier contenant le fragment shader.
     */
    public OpenGLShader(Context context, int vertexShaderID, int fragmentShaderID) {
        int vertexShader = loadShader(context, GLES20.GL_VERTEX_SHADER, vertexShaderID);
        int fragmentShader = loadShader(context, GLES20.GL_FRAGMENT_SHADER, fragmentShaderID);

        this.mProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(this.mProgram, vertexShader);
        GLES20.glAttachShader(this.mProgram, fragmentShader);

        GLES20.glLinkProgram(this.mProgram);
    }

    /**
     * Méthode permettant de récuperer le program.
     * @return Le program.
     */
    public int getProgram() {
        return mProgram;
    }

    /**
     * Méthode permettant de charger un shader.
     * @param context Context dans lequel est chargé le shader.
     * @param type Type du shader.
     * @param shaderID Id du fichier.
     * @return Le shader compiler.
     */
    private static final int loadShader(Context context, int type, int shaderID) {
        String shaderCode = FileIOHelper.readFromRAW(context, shaderID);

        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
