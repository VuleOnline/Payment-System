package common;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;

public class SessionManager {
	
	private static Map<String, Integer> session  = new ConcurrentHashMap<>();
	private static ThreadLocal<String> currSession = new ThreadLocal<>();
	
	public static String startSession(int id) 
	{
		String sessionId =  UUID.randomUUID().toString();
		session.put(sessionId, id);
		return sessionId;
	}
	public static void setCurrSession(String sessionId) 
	{
		currSession.set(sessionId);
	}
	public static String getCurrSess() 
	{
		return currSession.get();
	}
	
	public static int getEmpIdBySession(String currSess) 
	{
		return session.getOrDefault(currSess, -1);
		
	}
	public static boolean removeCurrSession() 
	{
		boolean removed =false;
		try {
		currSession.remove();
		removed=true;
		}catch(Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Logout failed. Try again.");
			e.printStackTrace();
		}
		return removed;
		
	}

}
