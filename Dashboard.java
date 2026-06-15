import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Dashboard extends JFrame {

    static int totalSlots = 10;
    static boolean[] slots = new boolean[totalSlots];

    JPanel grid;

    Dashboard() {
        setTitle("Vehicle Parking Dashboard");
        setSize(800,500);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(40,40,40));

        // Load slots from DB
        loadSlotsFromDB();

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setBounds(0,0,200,500);
        sidebar.setBackground(new Color(20,20,20));
        sidebar.setLayout(null);

        Color btnColor = new Color(0,150,136);

        JButton park = new JButton("Park");
        park.setBounds(30,50,140,40);

        JButton exit = new JButton("Exit");
        exit.setBounds(30,110,140,40);

        JButton report = new JButton("Report");
        report.setBounds(30,170,140,40);

        JButton search = new JButton("Search");
        search.setBounds(30,230,140,40);

        JButton refresh = new JButton("Refresh");
        refresh.setBounds(30,290,140,40);

        // Button styling
        JButton[] buttons = {park, exit, report, search, refresh};
        for(JButton b : buttons) {
            b.setBackground(btnColor);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            sidebar.add(b);
        }

        add(sidebar);

        // ===== SLOT GRID =====
        grid = new JPanel(new GridLayout(2,5,10,10));
        grid.setBounds(230,50,520,300);

        drawSlots();
        add(grid);

        // ===== BUTTON ACTIONS =====
        park.addActionListener(e -> new VehicleEntry());

        exit.addActionListener(e -> new VehicleExit());

        report.addActionListener(e -> new ReportPage());

        search.addActionListener(e -> new SearchVehicle());

        refresh.addActionListener(e -> refreshSlots());

        setVisible(true);
    }

    // ===== DRAW SLOTS =====
    void drawSlots() {
        grid.removeAll();

        for(int i=0;i<totalSlots;i++) {
            JButton btn = new JButton("P"+(i+1));

            if(slots[i]) {
                btn.setBackground(Color.RED);
                btn.setForeground(Color.WHITE);
            } else {
                btn.setBackground(Color.GREEN);
            }

            int index = i;
            btn.addActionListener(e -> showDetails(index));

            grid.add(btn);
        }

        grid.revalidate();
        grid.repaint();
    }

    // ===== SHOW SLOT DETAILS =====
    void showDetails(int i) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM vehicles WHERE slot=?"
            );

            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                JOptionPane.showMessageDialog(this,
                    "Vehicle: " + rs.getString("vehicle_no"));
            } else {
                JOptionPane.showMessageDialog(this, "Empty Slot");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // ===== LOAD FROM DB =====
    void loadSlotsFromDB() {
        try {
            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT slot FROM vehicles");

            while(rs.next()) {
                slots[rs.getInt("slot")] = true;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // ===== REFRESH =====
    void refreshSlots() {
        for(int i=0;i<totalSlots;i++) {
            slots[i] = false;
        }

        loadSlotsFromDB();
        drawSlots();
    }
}