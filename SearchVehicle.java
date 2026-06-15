import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SearchVehicle extends JFrame {

    JTextField vehicleNo;

    SearchVehicle() {
        setTitle("Search Vehicle");
        setSize(350,200);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel l = new JLabel("Vehicle No:");
        l.setBounds(30,40,100,25);
        add(l);

        vehicleNo = new JTextField();
        vehicleNo.setBounds(130,40,150,25);
        add(vehicleNo);

        JButton search = new JButton("Search");
        search.setBounds(100,100,120,30);
        add(search);

        search.addActionListener(e -> searchVehicle());

        setVisible(true);
    }

    void searchVehicle() {
        String vNo = vehicleNo.getText();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM vehicles WHERE vehicle_no=?"
            );
            ps.setString(1, vNo);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                JOptionPane.showMessageDialog(this,
                    "Found!\nSlot: P" + (rs.getInt("slot")+1));
            } else {
                JOptionPane.showMessageDialog(this, "Not Found");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}