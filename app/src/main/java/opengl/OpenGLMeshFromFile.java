package opengl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import model.FileIOHelper;

/**
 * Classe permettant de charger un maillage depuis un fichier ressource RAW
 */
public class OpenGLMeshFromFile extends OpenGLMesh {
    /**
     * Ctor.
     * @param context Context dans lequel est crée le maillage.
     * @param file Id du fichier.
     */
    public OpenGLMeshFromFile(Context context, int file) {
        String obj = FileIOHelper.readFromRAW(context, file);

        String lines[] = obj.split("\n");

        int nbVertice = 0;
        float fileVertice[];
        int nbTexture = 0;
        float fileTexture[];
        int nbFace = 0;
        int fileFace[];

        String content[];

        if(lines.length > 0) {
            int nline = 0;

             do {
                content = lines[nline].split(" ");
                nline ++;
            } while (!content[0].equals("i"));

            fileVertice = new float[Integer.parseInt(content[1]) * 3];
            fileTexture = new float[Integer.parseInt(content[2]) * 2];
            fileFace = new int[Integer.parseInt(content[3]) * 6];

            for(; nline < lines.length; nline++) {
                content = lines[nline].split(" ");

                if (content.length > 0) {
                    switch (content[0]) {
                        case "v":
                            fileVertice[nbVertice] = Float.parseFloat(content[1]);
                            fileVertice[nbVertice+1] = Float.parseFloat(content[2]);
                            fileVertice[nbVertice+2] = Float.parseFloat(content[3]);
                            nbVertice += 3;
                            break;
                        case "vt":
                            fileTexture[nbTexture] = Float.parseFloat(content[1]);
                            fileTexture[nbTexture+1] = Float.parseFloat(content[2]);
                            nbTexture += 2;
                            break;
                        case "f":
                            String point1[] = content[1].split("/");
                            String point2[] = content[2].split("/");
                            String point3[] = content[3].split("/");

                            fileFace[nbFace] = Integer.parseInt(point1[0]);
                            fileFace[nbFace+1] = Integer.parseInt(point1[1]);
                            fileFace[nbFace+2] = Integer.parseInt(point2[0]);
                            fileFace[nbFace+3] = Integer.parseInt(point2[1]);
                            fileFace[nbFace+4] = Integer.parseInt(point3[0]);
                            fileFace[nbFace+5] = Integer.parseInt(point3[1]);
                            nbFace += 6;
                            break;
                        default:
                            break;
                    }
                }
            }

            int nbVerticeFinaux = 0;
            float verticeFinaux[] = new float[(fileFace.length/2)*3];
            int nbTextureFinaux = 0;
            float textureFinaux[] = new float[(fileFace.length/2)*2];

            for(int i = 0; i < fileFace.length; i+=2) {
                verticeFinaux[nbVerticeFinaux] = fileVertice[(fileFace[i]-1)*3];
                verticeFinaux[nbVerticeFinaux+1] = fileVertice[((fileFace[i]-1)*3)+1];
                verticeFinaux[nbVerticeFinaux+2] = fileVertice[((fileFace[i]-1)*3)+2];
                nbVerticeFinaux += 3;

                textureFinaux[nbTextureFinaux] = fileTexture[(fileFace[i+1]-1)*2];
                textureFinaux[nbTextureFinaux+1] = fileTexture[((fileFace[i+1]-1)*2)+1];
                nbTextureFinaux += 2;
            }
            //init(new float[]{-0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.0f, 0.5f, 0.0f}, new float[] {1.0f, 1.0f, 0.0f, 1.0f});
            init(verticeFinaux, textureFinaux, new float[] {1.0f, 1.0f, 1.0f, 1.0f});
        } else {
            init(new float[]{-0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.0f, 0.5f, 0.0f}, new float[]{-1f, -1f, 0.0f, 1f, -1f, 0.0f, 0.0f, 1f, 0.0f}, new float[] {1.0f, 1.0f, 0.0f, 1.0f});
        }

        /*for(int i = 0; i < lines.length; i++) {
            String content[] = lines[i].split(" ");

            if(content.length > 0) {
                switch (content[0]) {
                    case "v":
                        Log.d("TEST", "vertice");
                        break;
                    case "vt":
                        Log.d("TEST", "texture");
                        break;
                    case "f":
                        Log.d("TEST", "face");
                        break;
                        default:
                            Log.d("TEST", "autre");
                            break;
                }
            }
        }*/


    }
}
