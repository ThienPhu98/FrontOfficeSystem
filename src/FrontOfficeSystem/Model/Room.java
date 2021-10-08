package FrontOfficeSystem.Model;

public class Room {
    String number;
    boolean isVacant;
    Guest guest;

    public Room() {
    }

    public Room(String number, boolean isVacant, Guest guest) {
        this.number = number;
        this.isVacant = isVacant;
        this.guest = guest;
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

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    @Override
    public String toString() {
        if (this.guest == null) {
            return "RoomNumber= '" + number +"', isVacant= '" + isVacant + "'" +
                    ", doesn't have guest";
        } else {
            return "RoomNumber= '" + number +"', isVacant= '" + isVacant + "'" +
                    ", guest= {name: " + guest.getName() + ", day arrival = "+ guest.getDayArrival() +"}";
        }
    }
}
