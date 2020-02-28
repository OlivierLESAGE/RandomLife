package model;

import model.Element;

/**
 * Classe représentant un Element à supprimer.
 */
public class ElementASupprimer {
    /**
     * Indice de l'élèment dans sa liste.
     */
    private int index;

    /**
     * Liste à laquelle appartient l'Element.
     */
    private ListeElement list;

    /**
     * Element à supprimer.
     */
    private Element ele;

    /**
     * Ctor.
     * @param ele Element à supprimer.
     * @param list Liste à laquelle appartient l'Element.
     */
    public ElementASupprimer(Element ele, ListeElement list) {
        this.ele = ele;
        this.list = list;
        this.index = this.list.getIndexOfElement(this.ele);
    }

    /**
     * Méthode permettant de retirer l'élèment de sa liste.
     */
    public void delete() {this.getList().deleteElement(this.ele);}

    /**
     * Méthode permettant de remettre l'élèment dans sa liste.
     */
    public void unDelete() {
        this.getList().addElement(this.ele, getIndex());
    }

    /**
     * Méthode permettant de récupérer l'indice de l'élèment.
     * @return L'indice de l'élèment.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Méthode permettant de récupérer la liste de l'élèment.
     * @return La liste de l'élèment.
     */
    public ListeElement getList() {
        return this.list;
    }

    /**
     *     /**
     * Méthode permettant de récupérer l'élèment.
     * @return L'élèment.
     */
    public Element getElement() {return this.ele;}
}
