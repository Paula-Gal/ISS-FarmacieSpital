package app.validators;

import app.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User user) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if(user.getFirstName().trim().equals("")) {
            errors.add("Invalid firstName!\n");
        }

        if(user.getLastName().trim().equals("")) {
            errors.add("Invalid lastName!\n");
        }

        if(user.getEmail().trim().equals("")) {
            errors.add("Invalid email!\n");
        }

        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getEmail());
        StringBuffer err = new StringBuffer("");
        if (!matcher.matches()) err.append("Invalid Email" + "\n");
        if(err.length() > 0) errors.add(err.toString());

        if(user.getPassword().trim().length() < 3) {
            errors.add("The password must have at least 6 characters! Whitespaces are not allowed!\n");
        }

        String errorMessage = errors.stream().reduce("", String::concat);
        if(errorMessage.length() > 0) {
            throw new ValidationException(errorMessage);
        }
    }
}