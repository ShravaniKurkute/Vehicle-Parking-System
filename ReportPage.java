import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ReportPage extends JFrame {

    JTable table;
    DefaultTableModel model;

    ReportPage() {
        setTitle("Parking Report");
        setSize(600,400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("Vehicle No");
        model.addColumn("Slot");
        model.addColumn("Entry Time");

        loadData();

        add(new JScrollPane(table));
        setVisible(true);
    }

    void loadData() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM vehicles");

            while(rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("vehicle_no"),
                    "P" + (rs.getInt("slot")+1),
                    new java.util.Date(rs.getLong("entry_time"))
                });
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}