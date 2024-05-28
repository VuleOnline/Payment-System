package common;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

public class SessionManager {
	
	private static Map<String, SessionData> session  = new ConcurrentHashMap<>();
	private static ThreadLocal<String> currSession = new ThreadLocal<>();
	
	public static String startSession(int id) 
	{
		String sessionId =  UUID.randomUUID().toString();
		session.put(sessionId, new SessionData(id));
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
		SessionData sessionData = session.get(currSess);
        if (sessionData != null) {
            return sessionData.getId();
        } else {
            return -1;
        }
    }
	
	 public static List<String> getPanelHistoryBySession(String currSess) {
	        SessionData sessionData = session.get(currSess);
	        if (sessionData != null) {
	            return sessionData.getPanelHistory();
	        } else {
	            return new CopyOnWriteArrayList<>(); 
	        }
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
	
	 private static class SessionData {
	        private int id;
	        private List<String> panelHistory;

	        public SessionData(int id) {
	            this.id = id;
	            this.panelHistory = new CopyOnWriteArrayList<>();
	        }

	        public int getId() {
	            return id;
	        }

	        public List<String> getPanelHistory() {
	            return panelHistory;
	        }
	    }

}
