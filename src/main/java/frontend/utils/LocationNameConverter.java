package frontend.utils;

public class LocationNameConverter {

    public static String capitalizeLocationName(String input) {
        final String[] words = input.toLowerCase().split(" ");
        final StringBuilder properSpelling = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                properSpelling.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return properSpelling.toString().trim();
        }
}
