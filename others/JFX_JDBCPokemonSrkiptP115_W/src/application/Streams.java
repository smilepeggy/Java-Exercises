package application;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {
//      private static Predicate<String> startsWithPredicate = text -> text.startsWith("H");
	private static Predicate<String> startsWithPredicate = new Predicate<String>() {

		@Override
		public boolean test(String t) {
			return t.startsWith("H");
		}
	};

	public static void main(String[] args) {
		Stream<String> stringStream = Stream.of("Haus", "Garten", "Hund", "Katze", "Maus", "Hund");
		List<String> collect = stringStream.filter(startsWithPredicate) // Filtert alle Elemente heraus, welche als
																		// Ergebnis nicht true haben
				.distinct() // löscht alle doppelten Elemente raus

				.map(text -> text.toUpperCase()) // Bildet den Parameter auf die Funktion ab
				.collect(Collectors.toList()); // Sammelt alle Elemente in einem neuen Datentypen
		collect.forEach(System.out::println);

		// intList = Ursprungsquelle
		List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
				22, 23);
		int result = intList.stream().reduce(0, (numOne, numTwo) -> numOne + numTwo);
		System.out.println(result);

//              intList.forEach(System.out::print);
		// .stream() erzeugt eine Kopie der Ursprungsquelle in einem Stream!
		// Wenn die Zahl geteilt durch 2 als Ergebnis 0 hat, bleibt sie im Stream,
		// ansonsten wird sie rausgeschmissen
//              List<Integer> filteredList = intList.stream().filter(num -> num % 2 == 0).collect(Collectors.toList());
//              System.out.println("NACH FILTER:");
//              filteredList.forEach(System.out::print);
	}

}
