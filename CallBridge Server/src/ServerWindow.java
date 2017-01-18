import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ServerWindow {

	private JFrame frame;
	
	private static JLabel lblPlayer1Status;
	private static JLabel lblPlayer2Status;
	private static JLabel lblPlayer3Status;
	private static JLabel lblPlayer4Status;
	
	private JButton btnStartGame;
	

	private ServerSocket serverSocket;
	private static GameLogic gameLogic;	
	
	private static boolean checkLogin[];
	
	
	private static DataInputStream in[];
	private static DataOutputStream out[];
	public static DataInputStream tempIn;
	public static DataOutputStream tempOut;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow window = new ServerWindow();
					window.frame.setVisible(true);
					window.frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public static void setClientDataInputStream(int id, DataInputStream inn)
	{
		in[id] = inn;
	}
	
	public static void setClientDataOutputStream(int id, DataOutputStream outt)
	{
		out[id] = outt;
	}
	
	public static DataInputStream getClientDataInputStream(int id)
	{
		return in[id];
	}
	
	public static DataOutputStream getClientDataOutputStream(int id)
	{
		return out[id];
	}	
	
	private boolean isAllLoggedIn()
	{
		for(int i=1; i<=4; i++) if(checkLogin[i] == false) return false;
		return true;
	}
	
	public static void sendToClient(int id, String command, String value) throws Exception
	{
		out[id].writeUTF(command);
		out[id].writeUTF(value);
	}

	public static void authenticate(String value) throws Exception
	{
		String id = value.substring(0, 8);
		String pass = value.substring(8);
		
		if(id.equals("Player 1") && pass.equals("")) 
		{
			if(checkLogin[Integer.parseInt(id.substring(7))] == false)
			{
				sendToClient(Integer.parseInt(id.substring(id.length()-1)), "auth", "Accepted1");				
				ServerWindow.lblPlayer1Status.setText("Player 1 Connected");
				checkLogin[Integer.parseInt(id.substring(7))] = true;
			}
			else 
			{
				sendToClient(Integer.parseInt(id.substring(id.length()-1)), "auth", "Redundant");
				in[Integer.parseInt(id.substring(id.length()-1))] = tempIn;
				out[Integer.parseInt(id.substring(id.length()-1))] = tempOut;
				//ServerWindow.tempOut = ServerWindow.out[Integer.parseInt(id.substring(id.length()-1))];				
			}
			
		}
		
		else if(id.equals("Player 2") && pass.equals("")) 
		{
			if(checkLogin[Integer.parseInt(id.substring(7))] == false)
			{
				sendToClient(Integer.parseInt(id.substring(id.length()-1)), "auth", "Accepted2");				
				ServerWindow.lblPlayer2Status.setText("Player 2 Connected");
				checkLogin[Integer.parseInt(id.substring(7))] = true;
			}
			else 
			{
				sendToClient(Integer.parseInt(id.substring(id.length()-1)), "auth", "Redundant");
				in[Integer.parseInt(id.substring(id.length()-1))] = tempIn;
				out[Integer.parseInt(id.substring(id.length()-1))] = tempOut;
				//ServerWindow.tempOut = ServerWindow.out[Integer.parseInt(id.substring(id.length()-1))];				
			}
		}
		
		else if(id.equals("Player 3") && pass.equals("")) 
		{
			if(checkLogin[Integer.parseInt(id.substring(7))] == false)
			{
				sendToClient(Integer.parseInt(id.substring(id.length()-1)), "auth", "Accepted3");				
				ServerWindow.lblPlayer3Status.setText("Player 3 Connected");
				checkLogin[Integer.parseInt(id.substring(7))] = true;
			}
			else 
			{
				sendToClient(Integer.parseInt(id.substring(id.length()-1)), "auth", "Redundant");
				in[Integer.parseInt(id.substring(id.length()-1))] = tempIn;
				out[Integer.parseInt(id.substring(id.length()-1))] = tempOut;
				//ServerWindow.tempOut = ServerWindow.out[Integer.parseInt(id.substring(id.length()-1))];				
			}
		}
		
		else if(id.equals("Player 4") && pass.equals("")) 
		{
			if(checkLogin[Integer.parseInt(id.substring(7))] == false)
			{
				sendToClient(Integer.parseInt(id.substring(id.length()-1)), "auth", "Accepted4");				
				ServerWindow.lblPlayer4Status.setText("Player 4 Connected");
				checkLogin[Integer.parseInt(id.substring(7))] = true;
			}			
			else 
			{
				sendToClient(Integer.parseInt(id.substring(id.length()-1)), "auth", "Redundant");
				in[Integer.parseInt(id.substring(id.length()-1))] = tempIn;
				out[Integer.parseInt(id.substring(id.length()-1))] = tempOut;
				//ServerWindow.tempOut = ServerWindow.out[Integer.parseInt(id.substring(id.length()-1))];				
			}
		}
		
		else
		{
			sendToClient(Integer.parseInt(id.substring(id.length()-1)), "auth", "Ignored");
			in[Integer.parseInt(id.substring(id.length()-1))] = tempIn;
			out[Integer.parseInt(id.substring(id.length()-1))] = tempOut;
		}
	}
	
	public static void setCall(String value)
	{
		
		int id = Integer.parseInt(value.substring(0, 1));
		int call = Integer.parseInt(value.substring(1));
		gameLogic.setCallValue(id, call);
		//gameLogic._call[id] = call;
	}
	
	public static void setMove(String value)
	{
		int id = Integer.parseInt(value.substring(0, 1));
		String cardPlaced = value.substring(1);
		gameLogic.setPlayedCard(id, cardPlaced);
	}


	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	
	public void startServer() throws Exception
	{
		serverSocket = new ServerSocket(2300);	
		Thread t = new connector(serverSocket);
		t.start();
	}
	
	public void startGame() throws Exception
	{
		int currentPlayer;
		int startingPlayer = 1;
		String currentType;
		int cnt;
		int loop;
		//int i;
		while(true)
		{
			if(startingPlayer > 4) startingPlayer = 1;		
			gameLogic.resetCheck();
			for(currentPlayer=startingPlayer, cnt=0; cnt<4; currentPlayer++, cnt++)
			{
				if(currentPlayer > 4) currentPlayer = 1;
				if(cnt != 3) sendToClient(currentPlayer, "shuffle", gameLogic.shuffleCards(false));
				else sendToClient(currentPlayer, "shuffle", gameLogic.shuffleCards(true));				
			}
			
			gameLogic.resetCall();			
			for(currentPlayer=startingPlayer, cnt=0; cnt<4; currentPlayer++, cnt++)
			{
				if(currentPlayer > 4) currentPlayer = 1;
				for(int id = 1; id<=4; id++) sendToClient(id, "changeInfoMessage", "Player " + currentPlayer + "'s Turn To Call");
				
				sendToClient(currentPlayer, "makeCall", Integer.toString(currentPlayer));
				
				int c = 0;
				while(gameLogic.getCallValue(currentPlayer) == 0){System.out.println("looping");}	
				
				for(int id = 1; id<=4; id++) sendToClient(id, "changeHandsInfo", currentPlayer + 
						Integer.toString(gameLogic.getHandsWon(currentPlayer))+ "-" + Integer.toString(gameLogic.getCallValue(currentPlayer)));				
			}	
			
			for(loop=1, currentPlayer=startingPlayer; loop<=13; loop++)
			{
				gameLogic.resetPlayedCard();
				
				for(int id = 1; id<=4; id++) 
				{
					sendToClient(id, "changeInfoMessage", "Player " + currentPlayer + "'s Turn");
					sendToClient(id, "changeTurn", Integer.toString(currentPlayer));
				}
				sendToClient(currentPlayer, "makeMove", Integer.toString(currentPlayer) + "a");
				while(gameLogic.getPlayedCard(currentPlayer).equals("nothing")) System.out.println(gameLogic.getPlayedCard(currentPlayer));
				for(int id = 1; id<=4; id++) sendToClient(id, "setPlayedCardPicture", Integer.toString(currentPlayer) + gameLogic.getPlayedCard(currentPlayer));
				currentType = gameLogic.getPlayedCard(currentPlayer).substring(0, 1);
								
				
				currentPlayer++;
				if(currentPlayer > 4) currentPlayer = 1;
				for(int id = 1; id<=4; id++) 
				{
					sendToClient(id, "changeInfoMessage", "Player " + currentPlayer + "'s Turn");
					sendToClient(id, "changeTurn", Integer.toString(currentPlayer));
				}
				sendToClient(currentPlayer, "makeMove", Integer.toString(currentPlayer) + currentType);
				while(gameLogic.getPlayedCard(currentPlayer).equals("nothing")) System.out.println(gameLogic.getPlayedCard(currentPlayer));
				for(int id = 1; id<=4; id++) sendToClient(id, "setPlayedCardPicture", Integer.toString(currentPlayer) + gameLogic.getPlayedCard(currentPlayer));
				
				currentPlayer++;
				if(currentPlayer > 4) currentPlayer = 1;
				for(int id = 1; id<=4; id++) 
				{
					sendToClient(id, "changeInfoMessage", "Player " + currentPlayer + "'s Turn");
					sendToClient(id, "changeTurn", Integer.toString(currentPlayer));
				}
				sendToClient(currentPlayer, "makeMove", Integer.toString(currentPlayer) + currentType);
				while(gameLogic.getPlayedCard(currentPlayer).equals("nothing")) System.out.println(gameLogic.getPlayedCard(currentPlayer));
				for(int id = 1; id<=4; id++) sendToClient(id, "setPlayedCardPicture", Integer.toString(currentPlayer) + gameLogic.getPlayedCard(currentPlayer));
				
				currentPlayer++;
				if(currentPlayer > 4) currentPlayer = 1;
				for(int id = 1; id<=4; id++) 
				{
					sendToClient(id, "changeInfoMessage", "Player " + currentPlayer + "'s Turn");
					sendToClient(id, "changeTurn", Integer.toString(currentPlayer));
				}
				sendToClient(currentPlayer, "makeMove", Integer.toString(currentPlayer) + currentType);
				while(gameLogic.getPlayedCard(currentPlayer).equals("nothing")) System.out.println(gameLogic.getPlayedCard(currentPlayer));
				for(int id = 1; id<=4; id++) sendToClient(id, "setPlayedCardPicture", Integer.toString(currentPlayer) + gameLogic.getPlayedCard(currentPlayer));
				
				currentPlayer = gameLogic.getWinner(currentType.charAt(0));
				for(int id = 1; id<=4; id++) sendToClient(id, "changeInfoMessage", "Player " + currentPlayer + " won the hand!");
				
				TimeUnit.SECONDS.sleep(4);
				
				for(int id = 1; id<=4; id++)
				{
					for(int j=1; j<=4; j++) 
					{	
						sendToClient(id, "changeHandsInfo", j + 
									Integer.toString(gameLogic.getHandsWon(j))+ "-" + Integer.toString(gameLogic.getCallValue(j)));
						
						sendToClient(id, "setPlayedCardPicture", Integer.toString(j) + "b2fv");
					}
				}
				
				
			}
			
			gameLogic.updateOverallScore();
			
			gameLogic.resetCall();
			gameLogic.resetHandsWon();
			for(int id = 1; id<=4; id++)
			{
				for(int j=1; j<=4; j++) 
				{	
					sendToClient(id, "changeHandsInfo", j + 
								Integer.toString(gameLogic.getHandsWon(j))+ "-" + Integer.toString(gameLogic.getCallValue(j)));	
					
					sendToClient(id, "changeOverallScore", j + Integer.toString(gameLogic.getOverallScore(j)));		
					
				}
			}
			
			
			boolean isOver = false;
			String winner = "";
			for(int id=1; id<=4; id++)
			{
				if(gameLogic.getOverallScore(id) >= 3)
				{
					winner += Integer.toString(id);
					isOver = true;
				}
			}
			
			if(isOver == true)
			{
				for(int id = 1; id<=4; id++) sendToClient(id, "gameOver", winner);	
				for(int id = 1; id<=4; id++) sendToClient(id, "changeInfoMessage", "The game has not yet started!");
				break;
			}
			startingPlayer++;
			
		}
	}
	
	
	public ServerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		checkLogin = new boolean [5];
		
		
		in = new DataInputStream[5];
		out = new DataOutputStream[5];
		
		gameLogic = new GameLogic();
		frame = new JFrame();
		frame.setBounds(100, 100, 604, 463);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JButton btnStartServer = new JButton("Start Server");
		btnStartServer.setBounds(243, 100, 136, 32);
		frame.getContentPane().add(btnStartServer);
		
		lblPlayer1Status = new JLabel("Player 1 Disconnected");
		lblPlayer1Status.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPlayer1Status.setBounds(58, 126, 200, 78);
		frame.getContentPane().add(lblPlayer1Status);
		
		lblPlayer2Status = new JLabel("Player 2 Disconnected");
		lblPlayer2Status.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPlayer2Status.setBounds(407, 132, 173, 72);
		frame.getContentPane().add(lblPlayer2Status);
		
		lblPlayer3Status = new JLabel("Player 3 Disconnected");
		lblPlayer3Status.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPlayer3Status.setBounds(58, 253, 173, 96);
		frame.getContentPane().add(lblPlayer3Status);
		
		lblPlayer4Status = new JLabel("Player 4 Disconnected");
		lblPlayer4Status.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPlayer4Status.setBounds(407, 253, 158, 96);
		frame.getContentPane().add(lblPlayer4Status);
		
		final JLabel lblServeInfo = new JLabel("Server is OFF");
		lblServeInfo.setFont(new Font("Dialog", Font.BOLD, 18));
		lblServeInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblServeInfo.setBounds(229, 24, 150, 47);
		frame.getContentPane().add(lblServeInfo);
		
		btnStartGame = new JButton("Start Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(isAllLoggedIn() == false)
				{
					JOptionPane.showMessageDialog(frame, "All the players have not logged in yet!");
					return;
				}
				try {
					startGame();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnStartGame.setBounds(243, 356, 136, 32);
		frame.getContentPane().add(btnStartGame);
		
		
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					startServer();
					lblServeInfo.setText("Server is ON");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}

class listenFromClient extends Thread
{
	//private DataOutputStream out;
	private DataInputStream in;
	
	public listenFromClient(DataInputStream i)
	{
		in = i;
		//out = o;
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				String command = in.readUTF();
				String value = in.readUTF();
				
				if(command.equals("auth")) ServerWindow.authenticate(value);
				if(command.equals("setCall")) ServerWindow.setCall(value);
				if(command.equals("setMove")) ServerWindow.setMove(value);
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
	}
}

class connector extends Thread
{
	private ServerSocket serverSocket;
	
	public connector(ServerSocket serversocket)
	{
		serverSocket = serversocket;
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				Socket sc = serverSocket.accept();
				System.out.println("Just connected to "+ sc.getRemoteSocketAddress());
				DataInputStream inn = new DataInputStream(sc.getInputStream());
				DataOutputStream outt = new DataOutputStream(sc.getOutputStream());
				String id = inn.readUTF();
				ServerWindow.tempIn = ServerWindow.getClientDataInputStream(Integer.parseInt(id.substring(id.length()-1)));
				ServerWindow.tempOut = ServerWindow.getClientDataOutputStream(Integer.parseInt(id.substring(id.length()-1)));
				ServerWindow.setClientDataInputStream(Integer.parseInt(id.substring(id.length()-1)), inn);
				ServerWindow.setClientDataOutputStream(Integer.parseInt(id.substring(id.length()-1)), outt);
				Thread t = new listenFromClient(ServerWindow.getClientDataInputStream(Integer.parseInt(id.substring(id.length()-1))));
				t.start();
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	
}
