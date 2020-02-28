package model;

/**
 * Classe représentant un point "objet" dans l'espace avec une position, une rotation, et un grossissement.
 */
public class Transformable {
    private float positionX;
    private float positionY;
    private float positionZ;

    private float rotationX;
    private float rotationY;
    private float rotationZ;

    private float scaleX;
    private float scaleY;
    private float scaleZ;

    public Transformable (float px, float py, float pz, float rx, float ry, float rz, float sx, float sy, float sz) {
        this.setPositionX(px);
        this.setPositionY(py);
        this.setPositionZ(pz);

        this.setRotationX(rx);
        this.setRotationY(ry);
        this.setRotationZ(rz);

        this.setScaleX(sx);
        this.setScaleY(sy);
        this.setScaleZ(sz);
    }

    public Transformable() {
        this(0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f);
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public float getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(float positionZ) {
        this.positionZ = positionZ;
    }

    public float getRotationX() {
        return rotationX;
    }

    public void setRotationX(float rotationX) {
        this.rotationX = rotationX;
    }

    public float getRotationY() {
        return rotationY;
    }

    public void setRotationY(float rotationY) {
        this.rotationY = rotationY;
    }

    public float getRotationZ() {
        return rotationZ;
    }

    public void setRotationZ(float rotationZ) {
        this.rotationZ = rotationZ;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getScaleZ() {
        return scaleZ;
    }

    public void setScaleZ(float scaleZ) {
        this.scaleZ = scaleZ;
    }

    public void setPosition(float px, float py, float pz) {
        this.setPositionX(px);
        this.setPositionY(py);
        this.setPositionZ(pz);
    }

    public void setRotation(float rx, float ry, float rz) {
        this.setRotationX(rx);
        this.setRotationY(ry);
        this.setRotationZ(rz);
    }
    public void setScale(float sx, float sy, float sz) {
        this.setScaleX(sx);
        this.setScaleY(sy);
        this.setScaleZ(sz);
    }

    public void set(Transformable trans) {
        this.setPosition(trans.getPositionX(), trans.getPositionY(), trans.getPositionZ());
        this.setRotation(trans.getRotationX(), trans.getRotationY(), trans.getRotationZ());
        this.setScale(trans.getScaleX(), trans.getScaleY(), trans.getScaleZ());
    }

    @Override
    public String toString() {
        return this.getPositionX() + " " + this.getPositionY() + " " + this.getPositionZ() + "\n" +
                this.getRotationX() + " " + this.getRotationY() + " " + this.getRotationZ() + "\n" +
                this.getScaleX() + " " + this.getScaleY() + " " + this.getScaleZ();
    }
}
