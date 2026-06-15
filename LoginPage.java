import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {

    JTextField username;
    JPasswordField password;

    LoginPage() {
        setTitle("Login");
        setSize(400,300);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(30,30,30));

        JLabel title = new JLabel("LOGIN");
        title.setBounds(150,30,100,30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title);

        JLabel u = new JLabel("Username:");
        u.setBounds(50,90,100,25);
        u.setForeground(Color.WHITE);
        add(u);

        username = new JTextField();
        username.setBounds(150,90,150,25);
        add(username);

        JLabel p = new JLabel("Password:");
        p.setBounds(50,130,100,25);
        p.setForeground(Color.WHITE);
        add(p);

        password = new JPasswordField();
        password.setBounds(150,130,150,25);
        add(password);

        JButton login = new JButton("Login");
        login.setBounds(130,180,120,30);
        login.setBackground(new Color(0,150,136));
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String user = username.getText().trim();
        String pass = new String(password.getPassword()).trim();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );

            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                new Dashboard();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}