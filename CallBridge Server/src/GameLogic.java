import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameLogic {
	private String cards[] = {"sa", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "st", "sk", "sq", "sj",
			  "da", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "dt", "dk", "dq", "dj",
			  "ca", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "ct", "ck", "cq", "cj",
		      "ha", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9", "ht", "hk", "hq", "hj"};
	private boolean check[];
	private Random rand;
	
	private int call[];
	private int handswon[];
	private int overallScore[];
	private String playedCard[];
	private int points[][];
	private Map map;
	//int call[] = {0, 1, 2, 3, 4};
	
	public GameLogic()
	{
		initialize();
	}
	
	public void resetCall()
	{
		Arrays.fill(call, 0);
	}
	
	public void resetCheck()
	{
		Arrays.fill(check, false);
	}
	
	public void resetPlayedCard()
	{
		Arrays.fill(playedCard, "nothing");
	}
	
	public void resetHandsWon()
	{
		Arrays.fill(handswon, 0);
	}
	
	public void resetOverallScore()
	{
		Arrays.fill(overallScore, 0);
	}
	
	public void setCallValue(int id, int value)
	{
		call[id] = value;
	}
	
	public int getCallValue(int id)
	{
		return call[id];
	}
	
	public void setPlayedCard(int id, String value)
	{
		playedCard[id] = value;
	}
	
	public String getPlayedCard(int id)
	{
		return playedCard[id];
	}
	
	public int getHandsWon(int id)
	{
		return handswon[id];
	}
	
	public int getOverallScore(int id)
	{
		return overallScore[id];
	}
	
	
	private void initialize()
	{
		check = new boolean[52];
		rand = new Random();
		
		call = new int[5];
		handswon = new int[5];
		overallScore = new int[5];
		playedCard = new String [5];
		points = new int [4][15];
		map = new HashMap();
		
		map.put('s', 0);
		map.put('h', 1);
		map.put('c', 2);
		map.put('d', 3);
		
		map.put('2', 2);
		map.put('3', 3);
		map.put('4', 4);
		map.put('5', 5);
		map.put('6', 6);
		map.put('7', 7);
		map.put('8', 8);
		map.put('9', 9);
		map.put('t', 10);
		map.put('j', 11);
		map.put('q', 12);
		map.put('k', 13);		
		map.put('a', 14);

		for(int i=2; i<=14; i++) points[0][i] = i + 50;
	
		
	}
	
	public String shuffleCards(boolean isLast)
	{
		int cnt = 0;
		int idx;
		String ret = new String("");
		if(isLast)
		{
			for(idx=0; idx<52; idx++) 
			{
				if(check[idx] == false) ret += cards[idx];
			}
			
			return ret;
		}		
		
		while(cnt < 13)
		{
			idx = rand.nextInt(52);
			if(check[idx] == false)
			{
				check[idx] = true;
				cnt++;
				ret += cards[idx];
			}
		}		
		
		return ret;
		
	}
	
	public int getWinner(char currentType)
	{
		Arrays.fill(points[1], 0);
		Arrays.fill(points[2], 0);
		Arrays.fill(points[3], 0);
		
		int x = (int) map.get(currentType);
		
		if(x != 0) for(int i=2; i<=14; i++) points[x][i] = i;
		
		int p1, p2, p3, p4;
		
		p1 = points[(int)map.get(playedCard[1].charAt(0))][(int)map.get(playedCard[1].charAt(1))];
		p2 = points[(int)map.get(playedCard[2].charAt(0))][(int)map.get(playedCard[2].charAt(1))];
		p3 = points[(int)map.get(playedCard[3].charAt(0))][(int)map.get(playedCard[3].charAt(1))];
		p4 = points[(int)map.get(playedCard[4].charAt(0))][(int)map.get(playedCard[4].charAt(1))];
		
		//System.out.println(p1 + " " + p2 + " " + p3 + " "  + p4);
		
		int mx = Math.max(p1, Math.max(p2, Math.max(p3, p4)));
		int ret = 0;
		
		if(mx == p1) ret = 1;		
		else if(mx == p2) ret = 2;
		else if(mx == p3) ret = 3;
		else if(mx == p4) ret = 4;
		
		handswon[ret]++;
		
		return ret;
	}
	
	public void updateOverallScore()
	{
		for(int i=1; i<=4; i++)
		{
			if(handswon[i] == call[i] || handswon[i]-1 == call[i]) overallScore[i] += call[i];
			else overallScore[i] -= call[i];
		}
	}
	
}
