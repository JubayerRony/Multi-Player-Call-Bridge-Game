import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CreditsWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreditsWindow window = new CreditsWindow();
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
	public CreditsWindow() {
		initialize();
	}
	
	public void makeVisible()
	{
	    frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 694, 539);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBackground.setForeground(Color.RED);
		lblBackground.setBackground(Color.WHITE);
		lblBackground.setIcon(new ImageIcon("img\\credits.jpg"));
		lblBackground.setBounds(0, 0, 686, 506);
		frame.getContentPane().add(lblBackground);
	}

}
