package com.matveyeu.shop.validator;

import com.matveyeu.shop.domain.User;
import com.matveyeu.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        //Username and password can't be empty or contain whitespace
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.not_empty");
        //Username can't be duplicated
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "register.error.duplicated.username");
        }
        //Email can't be duplicated
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "register.error.duplicated.email");
        }

        //Password must be the same as the confirmation password
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "register.error.diff_password");
        }

    }
}