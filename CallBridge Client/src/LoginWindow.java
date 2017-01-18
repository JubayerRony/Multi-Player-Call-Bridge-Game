import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;

public class LoginWindow {

	private static JFrame frame;
	private JTextField password;
	
	
	private String serverName;
	private int port;
	private Socket client;
	private static DataOutputStream out;
	private DataInputStream in;
	
	private static GameWindow gameWindow;
	private static CallWindow callWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
					//window.connectToServer();
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
	
	void connectToServer(String id) throws Exception
	{
		serverName = "localhost";
		port = 2300;
		
		System.out.println("Connecting to " + serverName + " on port " + port);
		
		
		client = new Socket(serverName, port);
		out = new DataOutputStream(client.getOutputStream());
		in = new DataInputStream(client.getInputStream());
		
		System.out.println("Just connected to " + client.getRemoteSocketAddress());
		
		out.writeUTF(id);
		
		Thread t = new listenFromServer(in);
		t.start();
		
	}
	
	public static void sendToServer(String command, String value) throws Exception
	{
		out.writeUTF(command);
		out.writeUTF(value);
	}
	
	public LoginWindow() {
		initialize();
	}
	
	public static void authenticate(String value)
	{
		if(value.equals("Ignored")) JOptionPane.showMessageDialog(frame, "Player ID and/or Password do not match!");
		else if(value.equals("Redundant")) JOptionPane.showMessageDialog(frame, "A player with the same ID is already logged in!");
		else
		{
			frame.dispose();
			gameWindow = new GameWindow(Integer.parseInt(value.substring(value.length()-1)));
			gameWindow.makeVisible();
		}
		
	}
	
	public static void sendShuffledCards(String value)
	{
		gameWindow.getShuffledCards(value);
	}
	
	public static void changeInfoMessage(String value)
	{
		gameWindow.setInfoMessage(value);
	}
	
	public static void makeCall(String value) throws Exception
	{		
		callWindow.makeVisible();		
		GlobalStaticVariables.isCallPlaced = false;
		while(GlobalStaticVariables.isCallPlaced == false) System.out.println(GlobalStaticVariables.isCallPlaced);
		int call = callWindow.getCall();		
		sendToServer("setCall", value + Integer.toString(call));		
	}	
	
	public static void changeHandsInfo(String value)
	{
		gameWindow.setHandsInfo(value);
	}
	
	public static void changeOverallScore(String value)
	{
		gameWindow.setOverallScore(value);
	}
	
	public static void makeMove(String value) throws Exception
	{
		GlobalStaticVariables.isCardPlaced = false;
		char currentType = value.charAt(1);
		String cardPlaced = gameWindow.getMove(currentType);
		sendToServer("setMove", value.substring(0, 1) + cardPlaced);
	}
	
	public static void setPlayedCardPicture(String value)
	{
		String id = value.substring(0, 1);
		String playedCard = value.substring(1);
		gameWindow.setPlayedCardPicture(Integer.parseInt(id), playedCard);
	}
	
	public static void changeTurn(String value)
	{
		gameWindow.setTurn(Integer.parseInt(value));
	}
	
	public static void gameOver(String value)
	{
		gameWindow.gameOver(value);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		callWindow = new CallWindow();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Player 1", "Player 2", "Player 3", "Player 4"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(165, 63, 154, 31);
		frame.getContentPane().add(comboBox);
		
		
		
		JButton btnSubmit = new JButton("Login");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				

				String id = comboBox.getSelectedItem().toString();
				String pass = password.getText();					
				try
				{
					connectToServer(id);
					sendToServer("auth", id + pass);
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}				
				
			}
		});
		btnSubmit.setBounds(183, 197, 117, 25);
		frame.getContentPane().add(btnSubmit);
		
		JLabel lblPlayerNumber = new JLabel("Player Number");
		lblPlayerNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerNumber.setBounds(43, 66, 104, 25);
		frame.getContentPane().add(lblPlayerNumber);
		
		password = new JTextField();
		password.setBounds(165, 130, 151, 31);
		frame.getContentPane().add(password);
		password.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(43, 128, 97, 25);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblLoginWindow = new JLabel("Login Window");
		lblLoginWindow.setFont(new Font("Dialog", Font.BOLD, 21));
		lblLoginWindow.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginWindow.setBounds(139, 0, 176, 31);
		frame.getContentPane().add(lblLoginWindow);
		ButtonGroup group = new ButtonGroup();
		
		
	}
}

class listenFromServer extends Thread
{
	private DataInputStream in;
	
	public listenFromServer(DataInputStream i)
	{
		in = i;
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				String command = in.readUTF();
				String value = in.readUTF();
				if(command.equals("auth")) LoginWindow.authenticate(value);				
				if(command.equals("shuffle")) LoginWindow.sendShuffledCards(value);
				if(command.equals("changeInfoMessage")) LoginWindow.changeInfoMessage(value);
				if(command.equals("makeCall")) LoginWindow.makeCall(value);
				if(command.equals("changeHandsInfo")) LoginWindow.changeHandsInfo(value);
				if(command.equals("makeMove")) LoginWindow.makeMove(value);
				if(command.equals("setPlayedCardPicture")) LoginWindow.setPlayedCardPicture(value);
				if(command.equals("changeTurn")) LoginWindow.changeTurn(value);
				if(command.equals("changeOverallScore")) LoginWindow.changeOverallScore(value);
				if(command.equals("gameOver")) LoginWindow.gameOver(value);
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
}
