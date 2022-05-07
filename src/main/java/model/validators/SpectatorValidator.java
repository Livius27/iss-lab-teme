package model.validators;

import model.Spectator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpectatorValidator implements Validator<Spectator> {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public void validate(Spectator spectator) {
        String errors = "";
        String nume = spectator.getNume();
        String prenume = spectator.getPrenume();
        String email = spectator.getEmail();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

        if (nume == null || nume.equals("") || !nume.matches("^[A-Za-z]*$")) {
            errors += "Last name cannot be empty and must contain only letters!\n";
        }
        if (prenume == null || prenume.equals("") || !prenume.matches("^[A-Za-z]*$")) {
            errors += "First name cannot be empty and must contain only letters!\n";
        }
        if (!matcher.find()) {
            errors += "Invalid email!\n";
        }
        if (!errors.equals(""))
            throw new ValidationException(errors);
    }
}
