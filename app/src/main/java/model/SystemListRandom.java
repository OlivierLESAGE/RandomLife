package model;

import android.app.Activity;
import android.util.Log;
import android.widget.BaseAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe servant a faire le liens entre toutes les listes.
 *
 * Les listes sont contenu de manière static pour faciliter l'utilisation.
 */
public class SystemListRandom implements Observer {
    /**
     * Instance static de la classe SystemListRandom. Cela permet de la rendre accessible à partir de n'importe quelle classe.
     */
	public static SystemListRandom LIST;

    /**
     * Arraylist contenant toutes les listes d'elèments.
     */
	private ArrayList<ListeElement>	list;

    /**
     * Adapter du "systèm" de liste.
     */
	private ListElementAdapter adapter;

    /**
     *  Ctor.
     * @param app Activité dans laquelle le "système" est construit.
     */
	public SystemListRandom(Activity app) {
		this.list = new ArrayList<ListeElement>(0);
		this.adapter = new ListElementAdapter(app.getLayoutInflater());
	}

    /**
     * Méthode static permettant d'initialiser l'instance static.
     * @param app
     */
	public static void init(Activity app) {
		LIST = new SystemListRandom(app);
	}

    /**
     * Méthode permettant d'ajouter une liste.
     * @param list Liste à ajouter.
     * @return L'indice de la liste, -1 si elle n'à pas été ajouté.
     */
	public int addList(ListeElement list) {
		list.addObserver(this);
		if (this.list.add(list)) {
			this.setChanged();
			return this.list.indexOf(list);
		} else {
			return  -1;
		}
	}

    /**
     * Méthode permettant de supprimer une liste.
     * @param list Liste a supprimer.
     */
	public void removeList(ListeElement list) {
		this.list.remove(list);
		this.setChanged();
	}

    /**
     * Méthode permettant de récuperer une liste.
     * @param index
     * @return
     */
	public ListeElement getList(int index) {
		return this.list.get(index);
	}

    /**
     * Méthode permettant de récupérer un Iterator sur toutes les listes.
     * @return Un Iterator.
     */
	public Iterator<ListeElement> getIterator() {
		return this.list.iterator();
	}

    /**
     * Méthode permettant de récupérer l'Adapter de la liste.
     * @return L'Adapter de la liste.
     */
	public ListElementAdapter getAdapter() {
		return adapter;
	}

    /**
     * Méthode permettant de mettre a jour l'affichage lors de changment ainsi que "activé la sauvegarde".
     */
	public void setChanged() {
		this.adapter.notifyDataSetChanged();
		Sauvegarde.haveToBeSaved();
	}

    /**
     * Méthode permettant de récupérer le nombre de liste.
     * @return
     */
	public int length() {
		return this.list.size();
	}
	
	@Override
	public String toString() {
		/*boolean oneElementAtLeast = false;

		String str = "[";

		for(Iterator<ListeElement> it = this.getIterator(); it.hasNext();) {
			str += it.next().toString() + ", ";
			oneElementAtLeast = true;
		}

		if (oneElementAtLeast) {
			str = str.substring(0, str.length() - 2);
			str += "]";
		} else {
			str = "";
		}

		return str;*/
		return this.toJSONArray().toString();
	}

    /**
     * Méthode permettant de retourner le JSONObject qui correspond à toutes les listes.
     * @return Un JSONObject qui correspond à toutes listes.
     */
	public JSONArray toJSONArray() {
		JSONArray json = new JSONArray();

		for(Iterator<ListeElement> it = this.getIterator(); it.hasNext();) {
			json.put(it.next().toJSONObject());
		}

		return json;
	}

    /**
     * Méthode permettant de supprimer toutes les listes.
     */
	public void clear() {
		this.list.clear();
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
	}
}
