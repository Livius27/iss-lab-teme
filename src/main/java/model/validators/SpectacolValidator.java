package model.validators;

import model.Spectacol;

public class SpectacolValidator implements Validator<Spectacol> {
    @Override
    public void validate(Spectacol spectacol) {
        String errors = "";
        String titlu = spectacol.getTitlu();
        String data = spectacol.getData();
        int nrLocuriDisponibile = spectacol.getNrLocuriDisponibile();

        if (titlu == null || titlu.equals(""))
            errors += "Please enter the title of the show!\n";
        if (data == null || data.equals(""))
            errors += "Please enter the date of the show!\n";
        if (nrLocuriDisponibile <= 0 || nrLocuriDisponibile > 60)
            errors += "Show must have at least 1 available seat and a maximum of 60 available seats!\n";
        if (!errors.equals(""))
            throw new ValidationException(errors);
    }
}
