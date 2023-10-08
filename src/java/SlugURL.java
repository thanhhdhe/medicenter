import java.util.Random;

public class SlugURL {
    private static final String CHARACTERS = "0123456789";

    public static String generateSlug(String input) {
        String slug = input.toLowerCase().replaceAll("[^a-z0-9]+", "-");
        slug = slug.replaceAll("-+", "-");

        // Generate a random number with 6 digits
        String randomNumber = generateRandomNumber(6);

        // Append the random number to the slug
        slug += "-" + randomNumber;

        return slug;
    }

    private static String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String input = "Example Product";
        String slug = generateSlug(input);
        System.out.println(slug);
    }
}
