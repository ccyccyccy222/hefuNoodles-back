public class User {
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String access;

    public User(String name, String avatar,String access) {
        this.name = name;
        this.avatar = avatar;
        this.access=access;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getavatar() {
        return avatar;
    }

    public void setavatar(String avatar) {
        this.avatar = avatar;
    }

    public String getaccess() {
        return access;
    }

    public void setaccess(String access) {
        this.access = access;
    }
}
