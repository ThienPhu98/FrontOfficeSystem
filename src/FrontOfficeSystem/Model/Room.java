package FrontOfficeSystem.Model;

public class Room {
    String number;
    boolean isVacant = true;
    String guestID;

    public Room(String number, boolean isVacant, String guestID) {
        this.number = number;
        this.isVacant = isVacant;
        this.guestID = guestID;
    }

    public Room() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isVacant() {
        return isVacant;
    }

    public void setVacant(boolean vacant) {
        isVacant = vacant;
    }

    public String getGuestID() {
        return guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    @Override
    public String toString() {
        if (isVacant) {
            return "Room " + number + ": {Room is empty}";
        } else {
            return "Room " + number + ": {Room has guest, GuestID: " + guestID +"}";
        }
    }
}
