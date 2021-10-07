package FrontOfficeSystem.Model;

public class Guest {
    String name;
    String phoneNumber;
    String dayArrival;
    String guaranteeFee;
    String methodPayment;
    String bookingCode;

    public Guest() {
    }

    public Guest(String name, String phoneNumber, String dayArrival,
                 String guaranteeFee, String methodPayment, String bookingCode) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dayArrival = dayArrival;
        this.guaranteeFee = guaranteeFee;
        this.methodPayment = methodPayment;
        this.bookingCode = bookingCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDayArrival() {
        return dayArrival;
    }

    public void setDayArrival(String dayArrival) {
        this.dayArrival = dayArrival;
    }

    public String getGuaranteeFee() {
        return guaranteeFee;
    }

    public void setGuaranteeFee(String guaranteeFee) {
        this.guaranteeFee = guaranteeFee;
    }

    public String getMethodPayment() {
        return methodPayment;
    }

    public void setMethodPayment(String methodPayment) {
        this.methodPayment = methodPayment;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    @Override
    public String toString() {
        return "\nname='" + name + "'" +
                "\nphoneNumber = '" + phoneNumber + "'" +
                "\ndayArrival = '" + dayArrival + "'" +
                "\nguaranteeFee = '" + guaranteeFee + "'" +
                "\nmethodPayment = '" + methodPayment + "'" +
                "\nbookingCode = '" + bookingCode + "'";
    }
}
