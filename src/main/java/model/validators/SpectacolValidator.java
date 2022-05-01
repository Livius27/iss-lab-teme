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
            errors += "Introduceti titlul spectacolului!\n";
        if (data == null || data.equals(""))
            errors += "Introduceti data spectacolului!\n";
        if (nrLocuriDisponibile <= 0 || nrLocuriDisponibile > 16)
            errors += "Spectacolul trebuie sa aiba minim 1 loc disponibil si maxim 16!\n";
        if (!errors.equals(""))
            throw new ValidationException(errors);
    }
}
