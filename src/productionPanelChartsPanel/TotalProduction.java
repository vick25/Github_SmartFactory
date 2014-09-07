package productionPanelChartsPanel;

import com.jidesoft.chart.Chart;
import java.awt.BorderLayout;

/**
 *
 * @author Victor Kadiata
 */
public class TotalProduction extends javax.swing.JPanel {

    public TotalProduction(Chart chart) {
        this.chartPanel = chart;
        initComponents();
        this.setLayout(new BorderLayout());
        if (this.chartPanel != null) {
            this.add(this.chartPanel, BorderLayout.CENTER);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    Chart chartPanel;
}
