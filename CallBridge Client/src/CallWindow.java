import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CallWindow {

	private JFrame frame;
	
	private static int call;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CallWindow window = new CallWindow();
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
	
	public void makeVisible()
	{
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	}
	
	public void makeInvisible()
	{
		frame.setVisible(false);
	}
	
	public int getCall()
	{
		//while(call == 0);		
		return call;
	}
	
	public CallWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		call = 0;
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		
		frame.getContentPane().setLayout(null);
		
		JButton callButton = new JButton("Call!");		
		callButton.setBounds(173, 178, 89, 23);
		frame.getContentPane().add(callButton);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4", "5", "6", "7"}));
		comboBox.setBounds(173, 86, 73, 23);
		frame.getContentPane().add(comboBox);
		
		JLabel lblMakeYourCall = new JLabel("Make Your Call");
		lblMakeYourCall.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMakeYourCall.setHorizontalAlignment(SwingConstants.CENTER);
		lblMakeYourCall.setBounds(156, 36, 117, 33);
		frame.getContentPane().add(lblMakeYourCall);
		
		callButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String c = comboBox.getSelectedItem().toString();
				call = Integer.parseInt(c);
				GlobalStaticVariables.isCallPlaced = true;
				frame.setVisible(false);
			}
		});
	}
}
