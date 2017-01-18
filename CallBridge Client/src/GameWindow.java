import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class GameWindow {

	private JFrame frame;
	
	private JLabel currentHandCards[];
	private JLabel handsWon[];;
	private JLabel overallScore[];
	private JLabel infoMessage;	
	private JButton playerCards[];
	
	private int turn;
	
	private String card[];
	private String cardPlaced;
	
	private char currentType;
	
	private final int DOWN = 0;
	private final int RIGHT = 1;
	private final int UP = 2;
	private final int LEFT = 3;
	
	private Map map;
	
	private int playerPosition[];	
	private int typeCount[];
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow(1);
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
	
	private void setPositions(int playerID)
	{
		if(playerID == 1)
		{
			playerPosition[1] = DOWN;
			playerPosition[2] = RIGHT;
			playerPosition[3] = UP;
			playerPosition[4] = LEFT;
			
		}
		else if(playerID == 2)
		{
			playerPosition[1] = LEFT;
			playerPosition[2] = DOWN;
			playerPosition[3] = RIGHT;
			playerPosition[4] = UP;		
		}
		else if(playerID == 3)
		{
			playerPosition[1] = UP;
			playerPosition[2] = LEFT;
			playerPosition[3] = DOWN;
			playerPosition[4] = RIGHT;
		}
		else
		{
			playerPosition[1] = RIGHT;
			playerPosition[2] = UP;
			playerPosition[3] = LEFT;
			playerPosition[4] = DOWN;
		}
	}
	
	public GameWindow(int playerID) {
		initialize(playerID);
	}
	
	public void makeVisible()
	{
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void setInfoMessage(String value)
	{
		infoMessage.setText(value);
	}
	
	public void setHandsInfo(String value)
	{
		int i;
		int id = Integer.parseInt(value.substring(0, 1));
		for(i=0; ; i++) 
			if(value.charAt(i) == '-') break;
		int handswon = Integer.parseInt(value.substring(1, i));
		int call = Integer.parseInt(value.substring(i+1));
		
		handsWon[id].setText("Player " + id + ": " + handswon + " (Call: " + call + ")");
	}
	
	public void setOverallScore(String value)
	{
		int id = Integer.parseInt(value.substring(0, 1));
		int score = Integer.parseInt(value.substring(1));
		
		if(score > 0) overallScore[id].setText("Player " + id + ": +" + score);
		else overallScore[id].setText("Player " + id + ": " + score);
		//else overallScore[id].setText("Player " + id + ": " + score);
	}
	

	public void getShuffledCards(String value)
	{		
		Arrays.fill(typeCount, 0);
		for(int i=0; i<26; i+=2) card[i/2] = value.substring(i, i+2);		
		
		Arrays.sort(card);
		
		for(int i=0; i<13; i++)
		{
			typeCount[(int)map.get(card[i].charAt(0))]++;
			playerCards[i].setIcon(new ImageIcon("img\\" + card[i] + ".png"));
		}
	}
	
	public String getMove(char currType)
	{
		//GlobalStaticVariables.isCardPlaced = false;	
		currentType = currType;
		while(GlobalStaticVariables.isCardPlaced == false) System.out.println(GlobalStaticVariables.isCardPlaced);
			
		return cardPlaced;	
	}
	
	public void setPlayedCardPicture(int playerID, String playedCard)
	{
		currentHandCards[playerPosition[playerID]].setIcon(new ImageIcon("img\\" + playedCard + ".png"));
	}
	
	public void setTurn(int value)
	{
		turn = value;
	}
	
	public void gameOver(String value)
	{
		if(value.length() == 1) JOptionPane.showMessageDialog(frame, "Game Over! Player " + value.charAt(0) + " is the winner.");
		else if(value.length() == 2) JOptionPane.showMessageDialog(frame, "Game Over! There is a tie between Player " + value.charAt(0) + " & Player " + value.charAt(1) + ".");
		else if(value.length() == 3) JOptionPane.showMessageDialog(frame, "Game Over! There is a tie among Player " + value.charAt(0) + ", Player " + value.charAt(1) + " & Player " + value.charAt(2) + ".");
		else JOptionPane.showMessageDialog(frame, "Game Over! There is a tie among Player " + value.charAt(0) + ", Player " + value.charAt(1) + ", Player " + value.charAt(2)  + " & Player " + value.charAt(3) + ".");		
		
	}

	private void initialize(final int playerID) {
		
		currentHandCards = new JLabel[4];
		handsWon = new JLabel[5];
		overallScore = new JLabel[5];
		playerCards = new JButton[13];	
		
		card = new String[13];	
		
		map = new HashMap();
		
		playerPosition = new int[5];		
		typeCount = new int[4];
		
		map.put('s', 0);
		map.put('h', 1);
		map.put('c', 2);
		map.put('d', 3);
		
		turn = 0;
		setPositions(playerID);
		frame = new JFrame();
		frame.setBounds(100, 100, 1124, 831);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		int tempPlayerID = playerID - 1;		
			
		JLabel lblPlayer1 = new JLabel("Player " + (tempPlayerID + 1));
		lblPlayer1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPlayer1.setBounds(490, 739, 55, 22);
		frame.getContentPane().add(lblPlayer1);		
		
		
		JLabel lblPlayer2 = new JLabel("Player " + (((tempPlayerID + 1) % 4) + 1));
		lblPlayer2.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPlayer2.setBounds(885, 376, 55, 29);
		frame.getContentPane().add(lblPlayer2);		
		
		
		JLabel lblPlayer3 = new JLabel("Player " + (((tempPlayerID + 2) % 4) + 1));
		lblPlayer3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPlayer3.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer3.setBounds(481, 94, 55, 22);
		frame.getContentPane().add(lblPlayer3);	
		
		
		JLabel lblPlayer4 = new JLabel("Player " + (((tempPlayerID + 3) % 4) + 1));
		lblPlayer4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPlayer4.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer4.setBounds(77, 376, 55, 29);
		frame.getContentPane().add(lblPlayer4);
		
		
		
		JLabel lblHandsWon = new JLabel("Hands Won");
		lblHandsWon.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblHandsWon.setBounds(839, 23, 141, 22);
		frame.getContentPane().add(lblHandsWon);
		
		handsWon[1] = new JLabel("Player 1: 0 (Call: 0)");
		handsWon[1].setFont(new Font("Tahoma", Font.BOLD, 12));
		handsWon[1].setBounds(849, 52, 160, 29);
		frame.getContentPane().add(handsWon[1]);
		
		handsWon[2] = new JLabel("Player 2: 0 (Call: 0)");
		handsWon[2].setFont(new Font("Tahoma", Font.BOLD, 12));
		handsWon[2].setBounds(850, 81, 159, 29);
		frame.getContentPane().add(handsWon[2]);
		
		handsWon[3] = new JLabel("Player 3: 0 (Call: 0)");
		handsWon[3].setFont(new Font("Tahoma", Font.BOLD, 12));
		handsWon[3].setBounds(849, 109, 160, 29);
		frame.getContentPane().add(handsWon[3]);
		
		handsWon[4] = new JLabel("Player 4: 0 (Call: 0)");
		handsWon[4].setFont(new Font("Tahoma", Font.BOLD, 12));
		handsWon[4].setBounds(849, 136, 160, 29);
		frame.getContentPane().add(handsWon[4]);		

		
		JLabel lblOverallScore = new JLabel("Overall Score");
		lblOverallScore.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblOverallScore.setBounds(42, 23, 141, 22);
		frame.getContentPane().add(lblOverallScore);
		
		overallScore[1] = new JLabel("Player 1: 0");
		overallScore[1].setFont(new Font("Tahoma", Font.BOLD, 12));
		overallScore[1].setBounds(52, 52, 97, 29);
		frame.getContentPane().add(overallScore[1]);
		
		overallScore[2] = new JLabel("Player 2: 0");
		overallScore[2].setFont(new Font("Tahoma", Font.BOLD, 12));
		overallScore[2].setBounds(52, 81, 97, 29);
		frame.getContentPane().add(overallScore[2]);
		
		overallScore[3] = new JLabel("Player 3: 0");
		overallScore[3].setFont(new Font("Tahoma", Font.BOLD, 12));
		overallScore[3].setBounds(52, 109, 97, 29);
		frame.getContentPane().add(overallScore[3]);
		
		overallScore[4] = new JLabel("Player 4: 0");
		overallScore[4].setFont(new Font("Tahoma", Font.BOLD, 12));
		overallScore[4].setBounds(52, 136, 97, 29);
		frame.getContentPane().add(overallScore[4]);
		
		infoMessage = new JLabel("The Game Has Not Yet Started!");
		infoMessage.setFont(new Font("Tahoma", Font.BOLD, 14));
		infoMessage.setHorizontalAlignment(SwingConstants.CENTER);
		infoMessage.setBounds(386, 18, 246, 36);
		frame.getContentPane().add(infoMessage);
		
		
		
		playerCards[0] = new JButton("");
		playerCards[0].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[0]);
		playerCards[0].setBounds(22, 609, 71, 96);
		playerCards[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[0].equals("nothing")) return;
				
				if(card[0].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}	
				
				cardPlaced = card[0];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[0] = "nothing";		
				playerCards[0].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;					
			}
		});
		
		
		playerCards[1] = new JButton("");
		playerCards[1].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[1]);
		playerCards[1].setBounds(103, 609, 71, 96);	
		playerCards[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[1].equals("nothing")) return;
				
				if(card[1].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}
				
				cardPlaced = card[1];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[1] = "nothing";
				playerCards[1].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;	
				
			}
		});
		
		playerCards[2] = new JButton("");
		playerCards[2].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[2]);
		playerCards[2].setBounds(189, 609, 71, 96);
		playerCards[2].addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[2].equals("nothing")) return;
				
				if(card[2].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}
				
				cardPlaced = card[2];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[2] = "nothing";
				playerCards[2].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;
				
			}
		});
		
		
		playerCards[3] = new JButton("");
		playerCards[3].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[3]);
		playerCards[3].setBounds(270, 609, 71, 96);
		playerCards[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[3].equals("nothing")) return;
				
				if(card[3].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}	
				
				cardPlaced = card[3];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[3] = "nothing";
				playerCards[3].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;
				
			}
		});
		
		playerCards[4] = new JButton("");
		playerCards[4].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[4]);
		playerCards[4].setBounds(351, 609, 71, 96);
		playerCards[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[4].equals("nothing")) return;
				
				if(card[4].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}
				
				
				cardPlaced = card[4];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[4] = "nothing";
				playerCards[4].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;				
			}
		});
		
		playerCards[5] = new JButton("");
		playerCards[5].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[5]);
		playerCards[5].setBounds(440, 609, 71, 96);
		playerCards[5].addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[5].equals("nothing")) return;
				
				if(card[5].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}
				
				cardPlaced = card[5];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[5] = "nothing";
				playerCards[5].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;				
			}
		});
		
		playerCards[6] = new JButton("");
		playerCards[6].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[6]);
		playerCards[6].setBounds(521, 609, 71, 96);
		playerCards[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[6].equals("nothing")) return;
				
				if(card[6].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}
				
				cardPlaced = card[6];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[6] = "nothing";
				playerCards[6].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;
				
			}
		});
		
		playerCards[7] = new JButton("");
		playerCards[7].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[7]);
		playerCards[7].setBounds(602, 609, 71, 96);
		playerCards[7].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[7].equals("nothing")) return;
				
				if(card[7].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}
				
				cardPlaced = card[7];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[7] = "nothing";
				playerCards[7].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;
				
			}
		});
		
		playerCards[8] = new JButton("");
		playerCards[8].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[8]);
		playerCards[8].setBounds(683, 609, 71, 96);
		playerCards[8].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[8].equals("nothing")) return;
				
				if(card[8].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}				
				
				cardPlaced = card[8];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[8] = "nothing";
				playerCards[8].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;}
		});
		
		playerCards[9] = new JButton("");
		playerCards[9].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[9]);
		playerCards[9].setBounds(764, 609, 71, 96);
		playerCards[9].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[9].equals("nothing")) return;
				
				if(card[9].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}
				
				
				cardPlaced = card[9];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[9] = "nothing";
				playerCards[9].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;
				
			}
		});
		
		playerCards[10] = new JButton("");
		playerCards[10].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[10]);
		playerCards[10].setBounds(849, 609, 71, 96);
		playerCards[10].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[10].equals("nothing")) return;
				
				if(card[10].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}				
				
				cardPlaced = card[10];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[10] = "nothing";
				playerCards[10].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;
				
			}
		});
		
		playerCards[11] = new JButton("");
		playerCards[11].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[11]);
		playerCards[11].setBounds(930, 609, 71, 96);
		playerCards[11].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[11].equals("nothing")) return;
				
				if(card[11].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}				
				
				cardPlaced = card[11];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[11] = "nothing";
				playerCards[11].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;
				
			}
		});
		
		playerCards[12] = new JButton("");
		playerCards[12].setIcon(new ImageIcon("img\\b2fv.png"));
		frame.getContentPane().add(playerCards[12]);
		playerCards[12].setBounds(1011, 609, 71, 96);
		playerCards[12].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(turn != playerID) 
				{
					JOptionPane.showMessageDialog(frame, "It's not your turn!");
					return;
				}
				
				if(card[12].equals("nothing")) return;
				
				if(card[12].charAt(0) != currentType && currentType != 'a')
				{
					if(typeCount[(int)map.get(currentType)] != 0)
					{
						JOptionPane.showMessageDialog(frame, "This is not a valid move!");
						return;
					}
				}				
				
				cardPlaced = card[12];
				typeCount[(int)map.get(cardPlaced.charAt(0))]--;
				card[12] = "nothing";				
				playerCards[12].setIcon(new ImageIcon("img\\b2fv.png"));
				turn = 0;
				GlobalStaticVariables.isCardPlaced = true;				
			}
		});
		
		
		
		currentHandCards[0] = new JLabel("");
		currentHandCards[0].setIcon(new ImageIcon("img\\b2fv.png"));
		currentHandCards[0].setBounds(475, 474, 71, 96);
		frame.getContentPane().add(currentHandCards[0]);
		
		currentHandCards[1] = new JLabel("");
		currentHandCards[1].setIcon(new ImageIcon("img\\b2fv.png"));
		currentHandCards[1].setBounds(792, 343, 71, 96);
		frame.getContentPane().add(currentHandCards[1]);
		
		currentHandCards[2] = new JLabel("");
		currentHandCards[2].setIcon(new ImageIcon("img\\b2fv.png"));
		currentHandCards[2].setBounds(475, 169, 71, 96);
		frame.getContentPane().add(currentHandCards[2]);
		
		currentHandCards[3] = new JLabel("");
		currentHandCards[3].setIcon(new ImageIcon("img\\b2fv.png"));
		currentHandCards[3].setBounds(189, 343, 71, 96);
		frame.getContentPane().add(currentHandCards[3]);
		
		
		
		
		
	}
}
