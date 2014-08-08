package dashboard;

/**
 *
 * @author Victor Kadiata
 */
public class Machine {

    private int machineID;
    private String machineName;

    public Machine(int machName, String machDomain) {
        machineID = machName;
        machineName = machDomain;
    }

    @Override
    public String toString() {
        return machineID + "";
    }

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
}
