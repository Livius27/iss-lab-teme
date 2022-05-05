package model.validators;

import model.Rezervare;

public class RezervareValidator implements Validator<Rezervare> {
    @Override
    public void validate(Rezervare rezervare) {
        String errors = "";
        String titluSpectacol = rezervare.getTitluSpectacol();

        if (titluSpectacol == null || titluSpectacol.equals("")) {
            errors += "The title of the show cannot be null or empty!\n";
        }
        if (!errors.equals(""))
            throw new ValidationException(errors);
    }
}
