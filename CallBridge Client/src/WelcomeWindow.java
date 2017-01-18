import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class WelcomeWindow {

	private JFrame frame;
	private LoginWindow loginWindow;
	private CreditsWindow creditsWindow;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeWindow window = new WelcomeWindow();
				    window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WelcomeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 694, 539);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnJoinGame = new JButton("Join Game");
		btnJoinGame.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				loginWindow = new LoginWindow();
				loginWindow.makeVisible();
			}
		});
		btnJoinGame.setBackground(Color.WHITE);
		btnJoinGame.setBounds(496, 206, 112, 28);
		frame.getContentPane().add(btnJoinGame);
		
		JButton btnNewButton_1 = new JButton("Credits");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creditsWindow = new CreditsWindow();
				creditsWindow.makeVisible();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.setBounds(496, 269, 112, 28);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBackground.setForeground(Color.RED);
		lblBackground.setBackground(Color.WHITE);
		lblBackground.setIcon(new ImageIcon("img\\home.jpg"));
		lblBackground.setBounds(0, 0, 686, 506);
		frame.getContentPane().add(lblBackground);
	}
}
