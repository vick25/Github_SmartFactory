package viewData;

import com.jidesoft.grid.RowStripeTableStyleProvider;
import com.jidesoft.grid.SortableTable;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableModel;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import smartfactoryV2.ConnectDB;

public class ViewData extends javax.swing.JDialog {

    public ViewData(java.awt.Frame parent, boolean modal, TableModel tableModel) {
        super(parent, modal);
        initComponents();
        if (tableModel != null) {
            this.table = new SortableTable(tableModel);
            this.table.getColumnModel().getColumn(0).setMinWidth(35);
            this.table.getColumnModel().getColumn(0).setMaxWidth(35);
            this.table.getColumnModel().getColumn(0).setResizable(false);
            this.table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
            this.table.getTableHeader().setBackground(Color.BLUE);
            this.table.setTableStyleProvider(new RowStripeTableStyleProvider(ConnectDB.colors3()));
//            Timer timer = new Timer(200, new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    if (this.table.getRowCount() <= 0) {
//                        BExcel.setEnabled(false);
//                    } else {
//                        BExcel.setEnabled(true);
//                    }
//                }
//            });
//            timer.start();
            Scrll.getViewport().setBackground(Color.WHITE);
            Scrll.setViewportView(this.table);
        }
        btnExcel.setVisible(false);
        this.setIconImage(new ImageIcon(
                getClass().getResource("/images/icons/view_multicolumn(10).png")).getImage());
        this.setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Scrll = new javax.swing.JScrollPane();
        btnExcel = new com.jidesoft.swing.JideButton();
        btnClose = new com.jidesoft.swing.JideButton();

        setTitle("View Data");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/office/insert-excel.gif"))); // NOI18N
        btnExcel.setText("Export to excel");
        btnExcel.setEnabled(false);
        btnExcel.setFocusable(false);
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/exit16x16.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.setFocusable(false);
        btnClose.setOpaque(true);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Scrll, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Scrll, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        getAccessibleContext().setAccessibleParent(this);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        FileSystemView fsv = FileSystemView.getFileSystemView();
        JFileChooser fc = new JFileChooser(fsv.getRoots()[0]);
        fc.setFileFilter(new FileNameExtensionFilter("Excel file", "xls"));
        File fichier = new File(fsv.getRoots()[0] + File.separator + "data.xls");
        fc.setSelectedFile(fichier);
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (!fc.getSelectedFile().exists()) {
                try {
                    if (!fc.getSelectedFile().getAbsolutePath().contains(".")) {
                        CreerExcel(new File(fc.getSelectedFile().getAbsolutePath() + ".xls"));
                    } else {
                        CreerExcel(fc.getSelectedFile());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, fc.getSelectedFile().getName() + " already exists ..."
                        + "", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void CreerExcel(File file) {
        try {
            WritableWorkbook wbb;
            WritableSheet sheet;
            Label label;
            wbb = Workbook.createWorkbook(file);
            String feuille = "Sheet1";
            sheet = wbb.createSheet(feuille, 0);
            for (int gh = 0; gh < table.getColumnCount(); gh++) {
                int p = gh;
                if (p < table.getColumnCount()) {
                    label = new Label(gh, 0, table.getColumnName(p));
                    sheet.addCell(label);
                    p++;
                }
            }
            int a = 0;
            for (int i = 0; i < table.getColumnCount(); i++) {
                int b = 0;
                for (int j = 1; j < table.getRowCount(); j++) {
                    if (a < table.getColumnCount()) {
                        label = new Label(i, j, table.getValueAt(b, a).toString());
                        sheet.addCell(label);
                        b++;
                    }
                }
                a++;
            }
            wbb.write();
            wbb.close();
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Scrll;
    private com.jidesoft.swing.JideButton btnClose;
    private com.jidesoft.swing.JideButton btnExcel;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    private SortableTable table;
}
