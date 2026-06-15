import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class VehicleEntry extends JFrame {

    JTextField vehicleNo;

    VehicleEntry() {
        setTitle("Park Vehicle");
        setSize(350,200);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel l = new JLabel("Vehicle No:");
        l.setBounds(30,40,100,25);
        add(l);

        vehicleNo = new JTextField();
        vehicleNo.setBounds(130,40,150,25);
        add(vehicleNo);

        JButton park = new JButton("Park");
        park.setBounds(100,100,120,30);
        add(park);

        park.addActionListener(e -> parkVehicle());

        setVisible(true);
    }

    void parkVehicle() {
        String vNo = vehicleNo.getText();

        for(int i=0;i<Dashboard.totalSlots;i++) {
            if(!Dashboard.slots[i]) {
                try {
                    Connection con = DBConnection.getConnection();

                    PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO vehicles(vehicle_no, slot, entry_time) VALUES(?,?,?)"
                    );

                    ps.setString(1, vNo);
                    ps.setInt(2, i);
                    ps.setLong(3, System.currentTimeMillis());

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Parked at P"+(i+1));
                    dispose();

                } catch(Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Parking Full");
    }
}