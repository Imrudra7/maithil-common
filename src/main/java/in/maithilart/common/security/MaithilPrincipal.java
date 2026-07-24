package in.maithilart.common.security;

public class MaithilPrincipal {

    private final String userId;
    private final String email;
    private final String fullName;

    public MaithilPrincipal(
            String userId,
            String email,
            String fullName) {

        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }
}