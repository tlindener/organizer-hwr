import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 */

/**
 * @author Steffen Baumann
 * @version 1.0
 * 
 */
public class HashMapToDataModel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Object[][] list = new Object[][] { { "1", "a" }, { "2", "b" },
				{ "3", "c" } };

		HashMap<String, String> map = convertModel(list, new String(),
				new String());
		ausgabe(map);
		Object[][] list2 = convertHashMap(map);
		ausgabe(list2);

	}

	private static void ausgabe(Object[][] model) {
		for(int i = 0; i < model.length; i++){
			System.out.println(model[i][0] + " " + model[i][1]);
		}
	}

	private static <K, V> void ausgabe(HashMap<K, V> map) {
		Set<Entry<K, V>> list = map.entrySet();
		Iterator<Entry<K, V>> it = list.iterator();
		while (it.hasNext()) {
			Entry<K, V> element = it.next();
			System.out.println(element.getKey() + " " + element.getValue());
		}
	}

	public static <K, V> Object[][] convertHashMap(HashMap<K, V> map) {

		Set<Entry<K, V>> list = map.entrySet();
		Object[][] model = new Object[list.size()][2];

		int i = 0;
		Iterator<Entry<K, V>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Entry<K, V> element = iterator.next();
			model[i][0] = element.getKey();
			model[i][1] = element.getValue();
			i++;
		}

		return model;
	}

	/**
	 * 
	 * @param model
	 *            to be converted
	 * @param key
	 *            Type of the HashMap
	 * @param value
	 *            Type of the HashMap
	 * @return new {@link HashMap} containing all elements of the given model
	 * @throws IllegalArgumentException
	 *             if
	 *             <ul>
	 *             <li>second dimension of the <b>model is not 2</b>.
	 *             <li>a key is <b>null</b>.
	 *             <li>the key was already to the link HashMap (<b>avoid
	 *             Duplicates</b>).
	 *             </ul>
	 * @throws ClassCastException
	 *             if either the key or the value could not be cast.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> HashMap<K, V> convertModel(Object[][] model, K key,
			V value) throws IllegalArgumentException, ClassCastException {

		if (model[0].length != 2) {
			throw new IllegalArgumentException(
					"Second arraydimension must be 2: " + model[0].length);
		}
		HashMap<K, V> map = new HashMap<>();

		for (int i = 0; i < model.length; i++) {
			if (model[i][0] == null) {
				throw new IllegalArgumentException(
						"Key value must not be null. Index: " + i);
			}
			boolean keyCastable = checkSuperClass(model[i][0].getClass(), key);
			boolean valueCastable = checkSuperClass(model[i][1].getClass(),
					value);
			if (keyCastable && valueCastable) {

				if (map.containsKey((K) model[i][0])
						&& map.get((K) model[i][0]) != null) {
					throw new IllegalArgumentException(
							"Key already exists in the HashMap: "
									+ (K) model[i][0]);
				}

				map.put((K) model[i][0], (V) model[i][1]);

			} else {
				throw new ClassCastException(
						"Unable to cast the key or value or both. Indey: " + i
								+ " - Key: " + keyCastable + " | Value: "
								+ valueCastable);
			}
		}
		return map;
	}

	/**
	 * Calls the generic method
	 * {@link #convertModel(Object[][], Object, Object)} by using Obejects
	 * 
	 * @param model
	 *            to be converted
	 * @return new {@link HashMap} containing all elements of the given model
	 * @throws IllegalArgumentException
	 *             if
	 *             <ul>
	 *             <li>second dimension of the <b>model is not 2</b>.
	 *             <li>a key is <b>null</b>.
	 *             <li>the key was already to the link HashMap (<b>avoid
	 *             Duplicates</b>).
	 *             </ul>
	 * @throws ClassCastException
	 *             if either the key or the value could not be cast.
	 * 
	 */
	public static HashMap<Object, Object> convertModel(final Object[][] model) {
		return convertModel(model, new Object(), new Object());
	}

	/**
	 * Checks if a cast is possible
	 * 
	 * @param clazz
	 *            of to be checked Object
	 * @param value
	 *            Object to cast to
	 * @return
	 */
	private static <V> boolean checkSuperClass(Class<?> clazz, V value) {
		return clazz.isInstance(value);
	}
}
