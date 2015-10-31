package tirage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Tirage2 {
	private ArrayList<Integer> idCouples = new ArrayList<Integer>();
	private Map<Integer, String> mapNoms = new HashMap<Integer, String>();
	private Map<Integer, Integer> coupleCadeau = new HashMap<Integer, Integer>();

	public void tire() {
		initialize();

		Collections.shuffle(idCouples);
		int i = 1;
		int c1 = 0;
		int c2 = 0;

		for (Integer idCouple : idCouples) {
			if (i == 1) {
				c1 = idCouple;
				i++;
			} else if (i == 2) {
				c2 = idCouple;
				coupleCadeau.put(c1, c2);
				i = 1;
			}
		}

		for (Integer couple1 : coupleCadeau.keySet()) {
			int couple2 = coupleCadeau.get(couple1);
			System.out.println(mapNoms.get(couple1) + " offre un cadeau à " + mapNoms.get(couple2));
		}
	}

	private void initialize() {
		idCouples.add(1);
		idCouples.add(2);
		idCouples.add(3);
		idCouples.add(4);
		idCouples.add(5);
		idCouples.add(6);

		mapNoms.put(1, "Boulons");
		mapNoms.put(2, "Bassets");
		mapNoms.put(3, "Kottelat");
		mapNoms.put(4, "Flows");
		mapNoms.put(5, "Aline");
		mapNoms.put(6, "Barthe");
	}
}
