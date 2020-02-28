package model;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Classe représentant une liste d'élèments.
 */
public class ListeElement extends Observable implements Observer {
    /**
     * Longueur en caractère du résumé.
     */
	public static final int SUMMARY_LENGTH = 35;

    /**
     * String qui sépare chaque élément dans le résumé.
     */
	public static final String SUMMARY_SEPARATOR = ", ";

    /**
     * String de fin du résumé lorsque la liste est trop longue.
     */
	public static final String SUMMARY_END = "...";

    /**
     * Arraylist contenant tous les élèments.
     */
	private ArrayList<Element> listElement;

    /**
     * Nom de la liste.
     */
	private String nom;

    /**
     * Adapter de la liste.
     */
	private ElementAdapter adapter;

    /**
     * Ctor.
     * @param nom Nom de la liste.
     * @param app Activité dans laquelle la liste est crée.
     */
	public ListeElement(String nom, Activity app) {
		this.listElement = new ArrayList<Element>(0);
		this.adapter = new ElementAdapter(this, app);
		this.setNom(nom);
	}

    /**
     * Méthide permettant d'ajouter un élèment à la liste.
     * @param element Element à ajouter.
     * @return L'indice auquel l'élèment a été aujouté, -1 si il n'a pas été ajouté.
     */
	public int addElement(Element element) {
		element.addObserver(this);
		if (this.listElement.add(element)) {
			isChanged();
			return this.listElement.indexOf(element);
		}
		else
			return -1;
	}

    /**
     * Méthode permettant d'ajouter un élèment à un indice.
     * @param element Element à ajouter.
     * @param index indice auquel l'élèment doit être ajouté.
     */
	public void addElement(Element element, int index) {
		element.addObserver(this);
		this.listElement.add(index, element);
		isChanged();
	}

    /**
     * Méthode permettant de récupèrer l'élèment à un indice donné.
     * @param position Indice de l'élèment.
     * @return L'élèment à l'indice donné.
     */
	public Element getElement(int position) {
		return this.listElement.get(position);
	}

    /**
     * Méthode permettant de récuperer un élèment au hasard.
     * @return
     */
	public Element getRandomElement() {
		Random rand = new Random();
		/*int randomNumber = rand.nextInt(listeEleemnt.size() + 1);
        return listeEleemnt.get(randomNumber);*/
		ArrayList<Integer> indexElement = new ArrayList<>(0);
		int indexEelementCourant = 0;
		if (this.listElement.size() == 0) {
			return null;
		} else {
			Iterator<Element> it = this.listElement.iterator();
			Element e;
			while(it.hasNext()) {
				e = it.next();
				for(int i = 0; i < e.getCoeff(); i++) {
					indexElement.add(indexEelementCourant);
				}
				++indexEelementCourant;
			}
			return this.listElement.get(indexElement.get(rand.nextInt(indexElement.size())));
		}
	}

    /**
     * Méthode permettant de supprimer un élèment donné de la liste.
     * @param element Element à supprimer.
     * @return True si l'élèment a été supprimé, sinon False.
     */
	public boolean deleteElement(Element element) {
		boolean res = this.listElement.remove(element);
		isChanged();
		return res;
	}

    /**
     * Méthode permettant de récupérer un Iterator sur la liste.
     * @return Un Iterator.
     */
	public Iterator<Element> getIterator() {
		return this.listElement.iterator();
	}

    /**
     * Méthode permettant de récupérer le nom de la liste.
     * @return Le nom de la liste.
     */
	public String getNom() {
		return this.nom;
	}

    /**
     * Méthode permettant de modifié le nom de la liste.
     * @param nom Nouveau nom de la liste.
     */
	public void setNom(String nom) {
		this.nom = nom;
		isChanged();
	}

	@Override
	public String toString() {
		/*boolean oneElementAtLeast = false;

		String str = "{\"nom\" : \"" + this.getNom() + "\", \"liste\" : [" ;

		for(Iterator<Element> it = this.getIterator(); it.hasNext();) {
			str += it.next().toString() + ", ";
			oneElementAtLeast = true;
		}

		str = str.substring(0, str.length() - ((oneElementAtLeast) ? 2 : 0));
		str += "]}";

		return str;*/
		return this.toJSONObject().toString();
	}

    /**
     * Méthode permettant de retourner le JSONObject qui correspond à la liste.
     * @return Un JSONObject qui correspond à la liste.
     */
	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
        JSONArray jsonTab = new JSONArray();

		try {
            json.put("nom", this.getNom());

            for(Iterator<Element> it = this.getIterator(); it.hasNext();) {
                jsonTab.put(it.next().toJSONObect());
            }

            json.put("liste", jsonTab);
        } catch (JSONException e) {
		    e.printStackTrace();
        }


		return json;
	}

    /**
     * Méthode permettant de récupérer le résumé de la liste.
     * @return Le résumé de la liste.
     */
	public String getSummary() {
        String str = "";
	    if(this.listElement.size() > 0) {
            Iterator<Element> it = this.getIterator();
            while (str.length() < (SUMMARY_LENGTH + SUMMARY_END.length()) && it.hasNext()) {
                str += it.next().getNom() + SUMMARY_SEPARATOR;
            }

            if (str.length() <= SUMMARY_LENGTH) {
                str = str.substring(0, str.length() - SUMMARY_SEPARATOR.length());
            } else {
                str = str.substring(0, SUMMARY_LENGTH - SUMMARY_SEPARATOR.length());
                str += SUMMARY_END;
            }
        }

		return str;
	}

	@Override
	public void update(Observable o, Object arg) {
		isChanged();
	}

    /**
     * Méthode permettant de notifier les Observateurs lors de modifications.
     */
	private void isChanged() {
		this.adapter.notifyDataSetChanged();
		setChanged();
		notifyObservers();
	}

    /**
     * Méthode permettant de savoir si la liste est vide.
     * @return True si la liste est vide, sinon False.
     */
	public boolean isEmpty(){
		return this.listElement.isEmpty();
	}

    /**
     * Méthode permettant de récupérer la taille de la lsite.
     * @return La taille de la liste.
     */
	public int size() {
		return this.listElement.size();
	}

    /**
     * Méthode permettant de récupérer l'Adapter de la liste.
     * @return L'Adapter de la liste.
     */
	public ElementAdapter getAdapter() {
		return adapter;
	}

    /**
     * Méthode permttant de désélectionné tous les élèments de la liste.
     */
	public void unselectAllElement() {
		Element ele;
		if (this.listElement.size() > 0) {
			Iterator<Element> it = this.listElement.iterator();
			for (; it.hasNext();) {
				ele = it.next();
				ele.unselect();
			}
		}
	}

    /**
     * Méthode permttant de récupérer l'indice d'un Element donné.
     * @param ele Element dont on veut connaitre l'indice.
     * @return L'indicde de l'Element.
     */
	public int getIndexOfElement(Element ele) {
		return this.listElement.indexOf(ele);
	}

    /**
     * Méthode permettant de supprimer tous les Element sélectionnés.
     */
	public void removeAllSelectedElement() {
		Element ele;
		if (this.listElement.size() > 0) {
			Iterator<Element> it = this.listElement.iterator();
			for (; it.hasNext();) {
				ele = it.next();
				if (ele.isSelected()) ElementDeleteSystem.addDeletedElement(ele, this);
			}
		}
	}

    /**
     * Méthode permettant de savoir si il y a au moins un Element avec un coefficient différent de 0.
     * @return True si il y a au moins un coefficient plus grand que 0, False sinon.
     */
	public boolean coefficientDifferentZero() {
		boolean res = false;
		if (!isEmpty()) {
			Iterator<Element> it = getIterator();

			while(it.hasNext() && !res) {
				if (it.next().getCoeff() != 0) res = true;
			}

			return res;
		} else return res;
	}
}
