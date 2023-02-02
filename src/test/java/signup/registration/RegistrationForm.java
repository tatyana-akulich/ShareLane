package signup.registration;

public class RegistrationForm {
    private String firstName;
    private String email;
    private String password;
    private String passwordConfirmation;

    public RegistrationForm(String firstName, String email, String password, String passwordConfirmation) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public RegistrationForm(String firstName, String email, String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
}
