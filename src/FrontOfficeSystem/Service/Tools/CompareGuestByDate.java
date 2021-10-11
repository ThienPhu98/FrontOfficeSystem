package FrontOfficeSystem.Service.Tools;

import FrontOfficeSystem.Model.Guest;

import java.util.Comparator;

public class CompareGuestByDate implements Comparator<Guest> {
    public int compare(Guest guest1, Guest guest2){
        String[] arrGuest1 = guest1.getDayArrival().split("/");
        String[] arrGuest2 = guest2.getDayArrival().split("/");
        int dayOfGuest1 = Integer.parseInt(arrGuest1[0]);
        int monthOfGuest1 = Integer.parseInt(arrGuest1[1]);
        int yearOfGuest1 = Integer.parseInt(arrGuest1[2]);

        int dayOfGuest2 = Integer.parseInt(arrGuest2[0]);
        int monthOfGuest2 = Integer.parseInt(arrGuest2[1]);
        int yearOfGuest2 = Integer.parseInt(arrGuest2[2]);

        if (compareInt(yearOfGuest1,yearOfGuest2)!=0) return compareInt(yearOfGuest1,yearOfGuest2);
        else if(compareInt(monthOfGuest1,monthOfGuest2)!=0)return compareInt(monthOfGuest1,monthOfGuest2);
        return compareInt(dayOfGuest1,dayOfGuest2);

    }

    private  int compareInt(int num1, int num2){
        if (num1 > num2){
            return 1;
        }else if (num1 == num2){
            return 0;
        }
        return -1 ;
    }
}
