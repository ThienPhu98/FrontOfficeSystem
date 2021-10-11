package FrontOfficeSystem.Service.Tools;

import FrontOfficeSystem.Model.Staff;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteStaffInformationFile {
    static String dataFolder = "H:\\Study & Working\\module2\\CaseStudy\\src\\FrontOfficeSystem\\Data\\";
    static String fileStaffList = "StaffList.json";

    public static void main(String[] args) {
        Staff staff1 = new Staff("Nguyen Phan Thien Phu", "sparta1510", "", "manager", null);
        Staff staff2 = new Staff("David Villa", "albatrap231", "", "manager", null);
        Staff staff3 = new Staff("Kaka", "bestmen2021", "", "staff", null);
        Staff staff4 = new Staff("Ederson Arsnol", "slovakiaNokia22", "", "staff", null);
        Staff staff5 = new Staff("Luka Jame", "Jamedeptrai2021", "", "staff", null);
        Staff staff6 = new Staff("Pillip Lamp", "Lampart81", "", "staff", null);
        Staff staff7 = new Staff("Nguyen Van Hau", "Hauhuhong2021", "", "staff", null);

        ArrayList<Staff> staffList = new ArrayList<>();
        staffList.add(staff1);
        staffList.add(staff2);
        staffList.add(staff3);
        staffList.add(staff4);
        staffList.add(staff5);
        staffList.add(staff6);
        staffList.add(staff7);

        JSONArray staffJSONList = new JSONArray();
        for (Staff staff : staffList) {
            int randomNumber = (int) (Math.random() * 99999) + 10000;
            JSONObject staffDetail = new JSONObject();
            staffDetail.put("name", staff.getName());
            staffDetail.put("password", staff.getPassword());
            staffDetail.put("history", staff.getHistory());
            staffDetail.put("level", staff.getLevel());
            staffDetail.put("ID", randomNumber);

            JSONObject staffJSON = new JSONObject();
            staffJSON.put("staff", staffDetail);

            staffJSONList.add(staffJSON);
        }

        try (FileWriter file = new FileWriter(dataFolder + fileStaffList)) {
            file.write(staffJSONList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
