package model;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import fr.lesage.olivier.info503.ListActivity;

/**
 * Classe permettant de sélectionner et supprimer des élèments.
 */
public class ElementDeleteSystem extends Observable implements Runnable {
    /**
     * Durée d'affichage du bandeau.
     */
    public static final int TEMPS_CORBEILLE_ACTIVE = 5000;

    /**
     * Durée pendant laquel est mis en pause le Thread.
     */
    public static final int PRECISION = 50;

    /**
     * Instance static du système de suppression.
     */
    private static ElementDeleteSystem deleteSystem = null;

    /**
     * Activité dans laquel est créer le système de suppression.
     */
    private ListActivity app;

    /**
     * Thread qui mesure le temps et qui gère l'affichage du bandeau.
     */
    private Thread waiter;

    /**
     * Nombre d'élèments sélectionner.
     */
    private int nbEleSelected;

    /**
     * Temps écoulé depuis que le bandeau est affiché.
     */
    private int corbeilleTime;

    /**
     * Liste des élèments à supprimer.
     */
    private ArrayList<ElementASupprimer> elementASupprimers;

    /**
     * Liste des élèments supprimer.
     */
    private ArrayList<ElementASupprimer> elementSupprimer;

    /**
     * Ctor.
     */
    private  ElementDeleteSystem() {
        this.waiter = new Thread();
        this.nbEleSelected = 0;
        this.corbeilleTime = 0;
        this.elementASupprimers = new ArrayList<ElementASupprimer>(0);
        this.elementSupprimer = null;
    }

    /**
     * Méthode static permettant d'initialiser l'instance static.
     */
    public static void init() {
        deleteSystem = new ElementDeleteSystem();
    }

    /**
     * Méthode static permettant de savoir si des élèments sont sélectionner.
     * @return True si aucun élèments sont sélectionnés.
     */
    public static boolean isiSelectMode() {
        return deleteSystem._isiSelectMode();
    }

    private boolean _isiSelectMode() {
        return this.nbEleSelected != 0;
    }

    /**
     * Méthode static permettant de comptabiliser un élèment à supprimer en plus.
     */
    public static void addElement() {
        deleteSystem._addElement();
    }

    private void _addElement() {
        this.nbEleSelected += 1;
        this.isChanged();
    }

    /**
     * Méthode static permettant de décomptabiliser un élèment à supprimer.
     */
    public static void removeElement() {
        deleteSystem._removeElement();
    }

    private void _removeElement() {
        this.nbEleSelected -= 1;
        this.isChanged();
    }

    /**
     * Méthode permettant de notifier les observateurs lors de changements.
     */
    private void isChanged() {
        setChanged();
        notifyObservers();
    }

    /**
     * Méthode static permettant d'ajouter un observateur.
     * @param o Nouvel Observateur.
     */
    public static void addObserverStatic(Observer o) {
        deleteSystem.addObserver(o);
    }

    /**
     * Méthode static permettant de retirer un observateur.
     * @param o Observateur à retirer.
     */
    public static void deleteObserverStatic(Observer o) {
        deleteSystem.deleteObserver(o);
    }

    /**
     * Méthode static peremttant d'ajouter un élèment à supprimer.
     * @param ele
     * @param list
     */
    public static void addDeletedElement(Element ele, ListeElement list) {
        deleteSystem._addDeletedElement(ele, list);
    }

    private void _addDeletedElement(Element ele, ListeElement list) {
        this.elementASupprimers.add(new ElementASupprimer(ele, list));
    }

    /**
     * Méthode permettant de supprimer tous les élèments à supprimer.
     */
    public static void deleteAllSelectedElement() {
        deleteSystem._deleteAllSelectedElement();
    }

    private void _deleteAllSelectedElement() {
        ElementASupprimer ele;
        if (this.elementASupprimers.size() > 0) {
            Iterator<ElementASupprimer> it = this.elementASupprimers.iterator();
            for (; it.hasNext(); ) {
                ele = it.next();
                ele.getElement().unselect();
                ele.delete();
            }
        }

        this.elementSupprimer = this.elementASupprimers;
        this.elementASupprimers = new ArrayList<ElementASupprimer>(0);

        this.corbeilleTime = TEMPS_CORBEILLE_ACTIVE;
        if(!this.waiter.isAlive()) {
            this.waiter = new Thread(this);
            this.waiter.start();
        }
        this.isChanged();
    }

    /**
     * Méthode permettant de supprimer les élèments supprimer "en attente".
     */
    public static void clearDeletedElement() {
        deleteSystem._clearDeletedElement();
    }

    private void _clearDeletedElement() {
        this.elementSupprimer = null;
        this.isChanged();
    }

    /**
     * Méthode permettant de récupérer tous les élèments qui ont étaient supprimés.
     * @return La liste des élèments supprimés.
     */
    public static ArrayList<ElementASupprimer> getLastDeletedGroup() {
        return deleteSystem._getLastDeletedGroup();
    }

    private ArrayList<ElementASupprimer> _getLastDeletedGroup() {
        if (this.elementSupprimer != null)
            return this.elementSupprimer;
        else return null;
    }

    /**
     * Méthode permettant de désupprimés les élèments supprimés.
     */
    public static void undeleteElement() {
        deleteSystem._undeleteElement();
    }

    private void _undeleteElement() {
        if(this.elementSupprimer != null) {
            ElementASupprimer ele;
            Iterator<ElementASupprimer> it = this.elementSupprimer.iterator();
            while(it.hasNext()) {
                ele = it.next();
                ele.unDelete();
            }
            this.elementSupprimer = null;
            this.corbeilleTime = 0;
            this.isChanged();
        }

    }

    /**
     * Méthode permettant de changer l'activité.
     * @param app
     */
    public static void setActivity(ListActivity app) {
        deleteSystem._setActivity(app);
    }

    private void _setActivity(ListActivity app) {
        this.app = app;
    }

    /**
     * Méthode permettant de retirer l'activité.
     */
    public static void removeActivity() {
        deleteSystem._removeActivity();
    }

    private void _removeActivity() {
        this.app = null;
    }

    @Override
    public void run() {
        this.app.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                deleteSystem.isChanged();
                app.showConfirmationLayout();
            }
        });
        while (corbeilleTime > 0) {
            try {
                corbeilleTime -= PRECISION;
                Thread.sleep(PRECISION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.elementSupprimer = null;
        if (this.app != null)
            this.app.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    deleteSystem.isChanged();
                    app.hideConfirmationLayout();
                }
            });
    }
}
