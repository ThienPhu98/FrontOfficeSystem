package FrontOfficeSystem.Service;

import FrontOfficeSystem.Model.Staff;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SignIn {
    static String dataFolder = "H:\\Study & Working\\module2\\CaseStudy\\src\\FrontOfficeSystem\\Data\\";
    static String staffFile = "StaffList.json";

    public static void signIn() {
        ArrayList<Staff> staffList = outPutStaffFileToList();
        Scanner scanner = new Scanner(System.in);

        System.out.print("\n====================================================");
        System.out.print("\n||____________WELCOME TO THIENPHU-HOTEL___________||");
        boolean isPassWordValid = false;
        while (!isPassWordValid) {
            System.out.print("\n||____________________SIGN-IN_____________________||");
            System.out.print("\n||Enter Your Password or press 'E' to exit: ");
            String password = scanner.nextLine();
            if (password.toUpperCase().equals("E")) {
                System.out.print("\n||_____________SYSTEM-PROCESS-ENDING______________||");
                System.exit(0);
            } else {
                for (Staff staffObj : staffList) {
                    if (staffObj.getPassword().equals(password)) {
                        if (staffObj.getLevel().equals("staff")) {
                            isPassWordValid = true;
                            SystemProcessStaff staff = new SystemProcessStaff(staffObj);
                            staff.mainMenu();
                        } else {
                            isPassWordValid = true;
                            SystemProcessManager manager = new SystemProcessManager(staffObj);
                            manager.mainMenu();
                        }
                    }
                }
            }
            if (!isPassWordValid) {
                System.out.print("\nPassword is invalid! Please try again!");
            }
        }
    }

    public static void writeStaffFileToList(JSONObject staff, ArrayList<Staff> staffList) {
        JSONObject staffFile = (JSONObject) staff.get("staff");

        if (staffFile != null) {
            Staff staffObj = new Staff();
            staffObj.setName((String) staffFile.get("name"));
            staffObj.setPassword((String) staffFile.get("password"));
            staffObj.setHistory((String) staffFile.get("history"));
            staffObj.setLevel((String) staffFile.get("level"));
            staffObj.setID((String) staffFile.get("ID"));

            staffList.add(staffObj);
        }
    }

    public static ArrayList<Staff> outPutStaffFileToList() {
        ArrayList<Staff> staffList = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(dataFolder + staffFile)) {
            Object obj = jsonParser.parse(reader);
            JSONArray staffFile = (JSONArray) obj;
            staffFile.forEach(staff -> writeStaffFileToList((JSONObject) staff, staffList));

        } catch (IOException | ParseException e) {
            return staffList;
        }
        return staffList;
    }

    public static void writeStaffListToFile(ArrayList<Staff> staffList) {
        JSONArray staffJSONList = new JSONArray();
        for (Staff staff : staffList) {
            JSONObject staffDetail = new JSONObject();
            staffDetail.put("name", staff.getName());
            staffDetail.put("password", staff.getPassword());
            staffDetail.put("history", staff.getHistory());
            staffDetail.put("level", staff.getLevel());
            staffDetail.put("ID", staff.getID());

            JSONObject staffJSON = new JSONObject();
            staffJSON.put("staff", staffDetail);

            staffJSONList.add(staffJSON);
        }

        try (FileWriter file = new FileWriter(dataFolder + staffFile)) {
            file.write(staffJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
