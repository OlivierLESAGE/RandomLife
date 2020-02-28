package opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.text.TextPaint;

/**
 * Classe représentant une texture.
 */
public class OpenGLTexture {
    /**
     * Handler de la texture.
     */
    private int textureHandle[];

    /**
     * Ctor.
     * @param context Context dans lequel est crée la texture.
     * @param resID Id de la texture.
     */
    public OpenGLTexture(Context context, int resID) {
        textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID, options);

            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }
    }

    /**
     * Ctor permettant d'écrire au cnetre d'un carré sur la texture.
     * @param context Context dans lequel est crée la texture.
     * @param resID Id de la texture.
     * @param text Texte à écrire.
     * @param x Position X du carré.
     * @param y Position Y du carré.
     * @param w Largeur du carré.
     * @param h Hauteur du carré.
     */
    public OpenGLTexture(Context context, int resID, String text, int x, int y, int w, int h) {
        textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID, options);
            Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            Canvas canvas = new Canvas(mutableBitmap);
            Paint paint = new Paint();
            TextPaint textPaint = new TextPaint();

            /*paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.MAGENTA);
            canvas.drawRect(x, y, x+w, y+h, paint);*/

            int xPos = (x + w/2);
            int yPos = (y + h/2) - ((int)((textPaint.descent() + textPaint.ascent()) / 2));
            //((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.
            textPaint.setAntiAlias(true);
            textPaint.setTextSize(textSize(text));
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(text, xPos, yPos, textPaint);

            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mutableBitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }
    }

    /**
     * Méthode permettant de connaitre la taille du texte en fonction de sa longueur.
     * @param text Texte.
     * @return Taille de la police à utilisé.
     */
    private static final int textSize(String text) {
        int textLength = text.length();
        int surplus = textLength - 18;
        if(textLength >= 25) return 32;
        else if (textLength > 18) return 42 - (surplus*10)/(25-18);
        else return 42;
    }

    /**
     * Méthode permettant de récupérer la texture.
     * @return
     */
    public int getHandle() {
        return this.textureHandle[0];
    }
}
