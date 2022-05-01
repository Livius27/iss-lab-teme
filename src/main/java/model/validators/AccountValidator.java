package model.validators;

import model.Manager;

public class AccountValidator implements Validator<Manager> {
    @Override
    public void validate(Manager account) throws ValidationException {
        String errors = "";
        String username = account.getUsername();
        int passwordHash = account.getPasswordHash();

        if (username == null || username.equals(""))
            errors += "Account username cannot be null or empty!\n";
        if (passwordHash == 0)
            errors += "Account password cannot be null or empty!\n";
        if (!errors.equals(""))
            throw new ValidationException(errors);
    }
}
