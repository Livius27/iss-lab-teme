package model.validators;

import model.Loc;
import model.StareLoc;

public class LocValidator implements Validator<Loc> {
    @Override
    public void validate(Loc loc) throws ValidationException {
        String errors = "";
        long numar = loc.getNumar();
        int rand = loc.getRand();
        int loja = loc.getLoja();
        float pret = loc.getPret();
        StareLoc stareLoc = loc.getStareLoc();

        if (numar <= 0)
            errors += "Seat number cannot be less or equal to 0!\n";
        if (rand <= 0 || rand > 6)
            errors += "Seat row must be a number between 1 and 6\n";
        if (loja != 1 && loja != 2)
            errors += "Seat box must be 1 or 2!\n";
        if (pret <= 0)
            errors += "Price cannot be less or equal to 0!\n";
        if (stareLoc != StareLoc.LIBER && stareLoc != StareLoc.REZERVAT && stareLoc != StareLoc.INDISPONIBIL)
            errors += "Seat status can be LIBER, REZERVAT or INDISPONIBIL only!\n";
        if (!errors.equals(""))
            throw new ValidationException(errors);
    }
}
