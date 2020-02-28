package model;

import android.os.SystemClock;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Classe permettant d'animer un Transformable.
 */
public class AnimationInterpoler {
    /**
     * AnimationKey courante. (point de départ)
     */
    private AnimationKey keyPrec;

    /**
     * AnimationKeySuivante. (point d'arrivé)
     */
    private AnimationKey keySuiv;

    /**
     * Liste des AnimationKey.
     */
    private LinkedList<AnimationKey> keyList;

    /**
     * Tranformable qui va être animé.
     */
    private Transformable trans;

    /**
     * Durée des AnimationKey précéfente.
     */
    private long duration;

    /**
     * Temps auquel l'animation commence.
     */
    private long offsetTime;

    /**
     * Ctor.
     */
    public AnimationInterpoler() {
        this.keyPrec = null;
        this.keySuiv = null;
        this.keyList = new LinkedList<AnimationKey>();

        this.trans = null;
        this.duration = 0;
        this.offsetTime = 0;
    }

    /**
     * Méthode permettant de mettre un tranformable en temps que cible de l'animation..
     * @param trans Transformable qui doit être animé.
     */
    public void setTargetTransformable(Transformable trans) {
        this.trans = trans;
    }

    /**
     * Méthode permettant de réinitialiser une animation. Cela supprimer tous les AnimationKey.
     */
    public void reset() {
        this.keyPrec = null;
        this.keySuiv = null;
        this.keyList.clear();

        this.duration = 0;
    }

    /**
     * Méthode permettant d'ajouter une AnimationKey.
     * @param key
     */
    public void addKey(AnimationKey key) {
        this.keyList.addLast(key);
    }

    /**
     * Méthode permettant de commencer une animation.
     * doit être appelée avant toutes interpollation.
     */
    public void start() {
        this.nextKey();
        this.nextKey();
        this.offsetTime = SystemClock.currentThreadTimeMillis();
    }

    /**
     * Méthode permettant de passer à l'animationKey suivante.
     */
    public void nextKey() {
        if (this.keySuiv != null) {
            this.duration = this.keySuiv.getTime();
            this.keyPrec = this.keySuiv;
        }

        if (this.keyList.size() > 0) this.keySuiv = this.keyList.pop();
        else this.keySuiv = null;
    }

    /**
     * Méthode permettant d'interpoller le Transformable entre les deux AnimationKey.
     */
    public void interpole() {
        long time = SystemClock.currentThreadTimeMillis();
        if(this.trans != null && !this.isFinish()) {
            long timeKey = (time-this.offsetTime) - this.duration;

            if (this.keyPrec != null) {
                if (this.keySuiv != null) {
                    if (this.keySuiv.getTime() - this.duration < timeKey) {
                        this.nextKey();
                        interpole();
                    } else {
                        Transformable keyPrecTrans = this.keyPrec.getTransformable();
                        Transformable keySuivTrans = this.keySuiv.getTransformable();
                        int keySuivTime = this.keySuiv.getTime();

                        float from, to;
                        long keyduraction = keySuivTime - this.duration;

                        from = keyPrecTrans.getPositionX(); to = keySuivTrans.getPositionX();
                        this.trans.setPositionX(AnimationInterpoler.interpolationFunction(timeKey, from, to - from, keyduraction));
                        from = keyPrecTrans.getPositionY(); to = keySuivTrans.getPositionY();
                        this.trans.setPositionY(AnimationInterpoler.interpolationFunction(timeKey, from, to - from, keyduraction));
                        from = keyPrecTrans.getPositionZ(); to = keySuivTrans.getPositionZ();
                        this.trans.setPositionZ(AnimationInterpoler.interpolationFunction(timeKey, from, to - from, keyduraction));

                        from = keyPrecTrans.getRotationX(); to = keySuivTrans.getRotationX();
                        this.trans.setRotationX(AnimationInterpoler.interpolationFunction(timeKey, from, to - from, keyduraction));
                        from = keyPrecTrans.getRotationY(); to = keySuivTrans.getRotationY();
                        this.trans.setRotationY(AnimationInterpoler.interpolationFunction(timeKey, from, to - from, keyduraction));
                        from = keyPrecTrans.getRotationZ(); to = keySuivTrans.getRotationZ();
                        this.trans.setRotationZ(AnimationInterpoler.interpolationFunction(timeKey, from, to - from, keyduraction));

                        from = keyPrecTrans.getScaleX(); to = keySuivTrans.getScaleX();
                        this.trans.setScaleX(AnimationInterpoler.interpolationFunction(timeKey, from, to - from, keyduraction));
                        from = keyPrecTrans.getScaleY(); to = keySuivTrans.getScaleY();
                        this.trans.setScaleY(AnimationInterpoler.interpolationFunction(timeKey, from, to - from, keyduraction));
                        from = keyPrecTrans.getScaleZ(); to = keySuivTrans.getScaleZ();
                        this.trans.setScaleZ(AnimationInterpoler.interpolationFunction(timeKey, from, to - from, keyduraction));
                    }
                } else {
                    trans.set(this.keyPrec.getTransformable());
                }
            }
        }
    }

    /**
     * Méthode peremettant de récupérer la durée des AnimationKey précédentes.
     * @return
     */
    public long getDuration() {
        return this.duration;
    }

    @Override
    public String toString() {
        String res = "";

		/*if (this.keyPrec != null) {
			res += "keyPrec = \n" + this.keyPrec.toString();
		} else {
			res += "keyPrec = null\n";
		}
		if (this.keySuiv != null) {
			res += "keySuiv = \n" + this.keySuiv.toString();
		} else {
			res += "keySuiv = null\n";
		}
		res += this.duration;*/


        if (this.keyPrec != null) res += this.keyPrec.toString();
        if (this.keySuiv != null) res += this.keySuiv.toString();

        Iterator<AnimationKey> it = this.keyList.iterator();
        while(it.hasNext()) {
            res += it.next().toString();
        }

        return res;
    }

    /**
     * Méthode permettant d'interpoller un float entre deux float en fonction du temps.
     * @param time Temps écoulé.
     * @param from Valeur de départ.
     * @param to Valeur d'arrivé.
     * @param duration Durée pour arrivé à la valeur d'arrivé.
     * @return La valeur interpollé.
     */
    public static final float interpolationFunction(long time, float from, float to, long duration) {
        return -to/2.0f * (float)(Math.cos(Math.PI*time/duration) - 1.0f) + from;
        //Log.d("TEST", to + " * " + time + " / " + duration + " + " + from);
        //return to * time / duration + from;
    }

    /**
     * Méthode permettant de savoir si l'animation est finis.
     * @return True si l'animation est finis, False sinon.
     */
    public boolean isFinish() {
        if(this.keySuiv == null) return true;
        else return false;
    }
}
