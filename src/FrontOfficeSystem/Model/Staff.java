package FrontOfficeSystem.Model;

public class Staff {
    String name;
    String password;
    String history;
    String level;
    String ID;

    public Staff() {
    }

    public Staff(String name, String password, String history, String level, String ID) {
        this.name = name;
        this.password = password;
        this.history = history;
        this.level = level;
        this.ID = ID;
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
        this.history += history;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "{" + "name='" + name + ", level= " + level + ", ID= " + ID + "}";
    }
}
