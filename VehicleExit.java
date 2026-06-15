import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class VehicleExit extends JFrame {

    JTextField vehicleNo;

    VehicleExit() {
        setTitle("Exit Vehicle");
        setSize(350,200);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel l = new JLabel("Vehicle No:");
        l.setBounds(30,40,100,25);
        add(l);

        vehicleNo = new JTextField();
        vehicleNo.setBounds(130,40,150,25);
        add(vehicleNo);

        JButton exit = new JButton("Exit");
        exit.setBounds(100,100,120,30);
        add(exit);

        exit.addActionListener(e -> exitVehicle());

        setVisible(true);
    }

    void exitVehicle() {
        String vNo = vehicleNo.getText();

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM vehicles WHERE vehicle_no=?"
            );

            ps.setString(1, vNo);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                long entry = rs.getLong("entry_time");
                int slot = rs.getInt("slot");

                long exit = System.currentTimeMillis();
                long hours = (exit - entry) / (1000*60*60);
                if(hours == 0) hours = 1;

                int bill = (int)hours * 20;

                PreparedStatement del = con.prepareStatement(
                    "DELETE FROM vehicles WHERE vehicle_no=?"
                );

                del.setString(1, vNo);
                del.executeUpdate();

                JOptionPane.showMessageDialog(this,
                    "Bill: ₹"+bill+"\nHours: "+hours);

                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Vehicle Not Found");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}