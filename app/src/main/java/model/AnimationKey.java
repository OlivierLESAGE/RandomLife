package model;

/**
 * Classe représentant une clé d'animation. Cela représente une position à un temps donné.
 */
public class AnimationKey {
    /**
     * Position de la clé.
     */
    private Transformable trans;

    /**
     * Temps de la clé. Moment auquel la clé doit être atteinte.
     */
    private int time;

    /**
     * Ctor.
     * @param trans Transformable de la clé.
     * @param time Temps de la clé.
     */
    public AnimationKey(Transformable trans, int time) {
        this.trans = trans;
        this.time = time;
    }

    /**
     * Méthode permettant de récupérer le Transformable de la clé.
     * @return Le Transformable de la clé.
     */
    public Transformable getTransformable() {
        return this.trans;
    }

    /**
     * Méthode permettant de récupérer le temps de la clé.
     * @return Le temps de la clé.
     */
    public int getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return this.trans.toString() + "\n" + this.time + "ms\n";
    }
}
