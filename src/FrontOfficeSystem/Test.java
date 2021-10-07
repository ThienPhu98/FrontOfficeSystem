package FrontOfficeSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import FrontOfficeSystem.Model.Guest;
import FrontOfficeSystem.Model.Room;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {
static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        Room room1 = new Room("101", true, null);
        Room room2 = new Room("102", true, null);
        Room room3 = new Room("103", true, null);
        Room room4 = new Room("201", true, null);
        Room room5 = new Room("202", true, null);
        Room room6 = new Room("203", true, null);
        Room room7 = new Room("301", true, null);
        Room room8 = new Room("302", true, null);
        Room room9 = new Room("303", true, null);
        Room room10 = new Room("304", true, null);
        Room room11 = new Room("401", true, null);
        Room room12 = new Room("402", true, null);

        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);
        roomList.add(room4);
        roomList.add(room5);
        roomList.add(room6);
        roomList.add(room7);
        roomList.add(room8);
        roomList.add(room9);
        roomList.add(room10);
        roomList.add(room11);
        roomList.add(room12);

        JSONArray guestList = new JSONArray();
        for (Room room : roomList) {
            JSONObject roomDetail = new JSONObject();
            roomDetail.put("name", room.getNumber());
            roomDetail.put("status", room.isVacant());
            roomDetail.put("guest", room.getGuest());

            JSONObject roomJSON = new JSONObject();
            roomJSON.put ("room", roomDetail);

            guestList.add(roomJSON);
        }


        String dataFolder = "H:\\Study & Working\\module2\\CaseStudy\\src\\FrontOfficeSystem\\Data\\";
        String fileName = "roomList.json";

        try (FileWriter file = new FileWriter(dataFolder + fileName)){
            file.write(guestList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
//
////        ĐỌC FILE
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(dataFolder + fileName)) {
//            Object obj = jsonParser.parse(reader);
//
//
//            JSONArray guestListRead = (JSONArray) obj;
//            System.out.print(guestListRead);
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }







    }
}
