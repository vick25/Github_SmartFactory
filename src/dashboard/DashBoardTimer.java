package dashboard;

import collapsibleDashboard.CollapsiblePaneDashboard;
import collapsibleDashboard.CollapsiblePaneGadget;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class DashBoardTimer extends TimerTask {

    public static boolean isShowChartDataMessage() {
        return showChartDataMessage;
    }

    public DashBoardTimer(CollapsiblePaneDashboard myColDashBoard, Map<String, CollapsiblePaneGadget> myMapMach,
            boolean myMachineTimeReset) {
        this._colDashBoard = myColDashBoard;
        this.machineTimeReset = myMachineTimeReset;
        this.mapMach = myMapMach;
    }

    @Override
    public void run() {
        if (mapMach.size() > 0 && machineTimeReset) {
            machineTimeReset = false;
            Set<String> keySet = mapMach.keySet();
            Iterator<String> keySetIterator = keySet.iterator();
            showChartDataMessage = false;
            while (keySetIterator.hasNext()) {
                String key = keySetIterator.next();
                try {
                    int[] configNo = _colDashBoard.getMachineConfigNo(key);
                    CollapsiblePaneGadget value = mapMach.get(key);
                    DashBoard.getBslTime().setText(new StringBuilder("Updating ").append(key).
                            append(" in a while.").toString());
                    _colDashBoard.setGadgetComponentPane(configNo, key, value);//set the gadget component pane with a chart
                } catch (SQLException ex) {
                    ConnectDB.catchSQLException(ex);
                } catch (ParseException | NullPointerException ex) {
                    ConnectDB.appendToFileException(ex);
                }
            }
            showChartDataMessage = true;
        }
        machineTimeReset = true;
    }
    private CollapsiblePaneDashboard _colDashBoard = null;
    private static boolean showChartDataMessage = true;
    private boolean machineTimeReset = false;
    private Map<String, CollapsiblePaneGadget> mapMach = new HashMap<>();
}
