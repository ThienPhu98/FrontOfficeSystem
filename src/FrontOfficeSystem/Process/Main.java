package FrontOfficeSystem.Process;

import FrontOfficeSystem.Service.*;

public class Main {
    public static void main(String[] args) {
        ISystemProcess process = new SystemProcess();
        process.mainMenu();
    }
}
