package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

/**
 * Classe représentant un élèment d'une liste.
 */
public class Element extends Observable {
    /**
     * Coefficient par défaut.
     */
	public static final int DEFAULT_COEFF = 1;

    /**
     * Nom de l'élèment.
     */
	private String nom;

    /**
     * Coefficient de l'élèment.
     */
	private int coeff;

    /**
     * Boolean permettant de savoir si l'élèment est sélectionné.
     */
	private boolean isSelected;

    /**
     * Ctor.
     * @param nom Nom de l'élèment.
     * @param coeff Coefficient de l'élèment.
     */
	public Element(String nom, int coeff) {
		this.nom = nom;
		this.coeff = coeff;
		this.isSelected = false;
	}

    /**
     * Ctor.
     * Ce Constructor petmet de dupliquer un Element.
     * @param ele Element à dupliquer.
     */
	public Element(Element ele) {
		this.nom = ele.getNom();
		this.coeff = ele.getCoeff();
		this.isSelected = false;
	}

    /**
     * Ctor.
     * @param nom Nom de l'élèment.
     */
	public Element(String nom) {
		this(nom, DEFAULT_COEFF);
	}

    /**
     * Méthode permettant de modifier le nom d'un élèment.
     * @param nom Nouveau nom de l'élèment.
     */
	public void setNom(String nom) {
		this.nom = nom;
		isChanged();
	}

    /**
     * Méthode permettant de récupérer le nom de l'élèment.
     * @return Le nom de l'élèment.
     */
	public String getNom() {
		return this.nom;
	}

    /**
     * Méthode permettant de modifier le coefficient d'un élèment.
     * @param coeff Nouveau coefficient de l'élèment.
     */
	public void setCoeff(int coeff) {
		this.coeff = coeff;
		isChanged();
	}

    /**
     * Méthode permettant de récupérer le coefficient de l'élèment.
     * @return Le coefficient de l'élèment.
     */
	public int getCoeff() {
		return this.coeff;
	}

    /**
     * Méthode permettant de savoir si un élèment est sélectionné.
     * @return True si l'élèment est sélctionné sinon False.
     */
	public boolean isSelected() {
		return this.isSelected;
	}

    /**
     * Méthode permettant de sélectionné un élèment.
     */
	public void select() {
	    // Si l'élèment n'est pas sélectionné alors on ajoute 1 au nombre d'élèment sélectionné.
		if (!this.isSelected) ElementDeleteSystem.addElement();
		this.isSelected = true;
		isChanged();
	}

    /**
     * Méthode permettant de déséletionné un élèment.
     */
	public void unselect() {
        // Si l'élèment est sélectionné alors on retire au nombre d'élèment sélectionné.
		if(this.isSelected) ElementDeleteSystem.removeElement();
		this.isSelected = false;
		isChanged();
	}

	@Override
	public String toString() {
		//return "{\"nom\" : \"" + this.getNom() + "\", \"coeff\" : " + this.getCoeff() + "}";
		return this.toJSONObect().toString();
	}

    /**
     * Méthode permettant de retourner le JSONObject qui correspond à l'élèment.
     * @return Un JSONObject qui correspond à l'élèment.
     */
	public JSONObject toJSONObect() {
	    JSONObject json = new JSONObject();

	    try {
            json.put("nom", this.getNom());
            json.put("coeff", this.getCoeff());
        } catch (JSONException e) {
	        e.printStackTrace();
        }

	    return json;
    }

    /**
     * Méthode permettant de notifier les Observateurs lors de modifications.
     */
	private void isChanged() {
		setChanged();
		notifyObservers();
	}
}
