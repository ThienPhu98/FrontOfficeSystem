package FrontOfficeSystem.Service.Tools;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import FrontOfficeSystem.Model.Room;
import org.json.simple.*;

public class WriteRoomFile {
    static String dataFolder = "H:\\Study & Working\\module2\\CaseStudy\\src\\FrontOfficeSystem\\Data\\";
    static String fileRoomList = "RoomList.json";

    public static void main(String[] args) {
        Room room1 = new Room("101", true, null);
        Room room2 = new Room("102", true, null);
        Room room3 = new Room("201", true, null);
        Room room4 = new Room("202", true, null);
        Room room5 = new Room("301", true, null);
        Room room6 = new Room("302", true, null);
        Room room7 = new Room("303", true, null);

        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);
        roomList.add(room4);
        roomList.add(room5);
        roomList.add(room6);
        roomList.add(room7);

        JSONArray RoomJSONList = new JSONArray();
        for (Room room : roomList) {
            JSONObject roomDetail = new JSONObject();
            roomDetail.put("number", room.getNumber());
            roomDetail.put("vacant", room.isVacant());
            roomDetail.put("guestID", room.getGuestID());

            JSONObject guestJSON = new JSONObject();
            guestJSON.put("room", roomDetail);

            RoomJSONList.add(guestJSON);
        }

        try (FileWriter file = new FileWriter(dataFolder + fileRoomList)) {
            file.write(RoomJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
