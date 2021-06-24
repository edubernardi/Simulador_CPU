package trabgraub;

public class Usuario {
    private String username;
    private String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public boolean isAdm() {
        return username.equals("admin") && password.equals("admin");
    }

    public String getUsername() {
        return username;
    }

    public String toString() {
        return "Nome de usuario: " + username + " Senha: " + password;
    }
}
