package FrontOfficeSystem.Model;

public class Staff {
    String name;
    String password;
    String history;

    public Staff() {
    }

    public Staff(String name, String password, String history) {
        this.name = name;
        this.password = password;
        this.history = history;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
