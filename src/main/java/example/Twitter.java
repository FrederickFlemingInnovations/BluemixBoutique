package example;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;



/**
 * Servlet implementation class Twitter
 */
@WebServlet("/Twitter")
public class Twitter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Twitter() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONArray tweetList = new JSONArray();
		try {
		  twitter4j.Twitter twitter = TwitterFactory.getSingleton();
		    Query query = new Query("\"" + request.getParameter("query") + "\"" );
		    query.setCount(5);
		    query.setLang("en");
		    QueryResult result = twitter.search(query);
		    for (Status status : result.getTweets()) {
		    	JSONObject obj = new JSONObject();
				obj.put("screenname", status.getUser().getScreenName());
				obj.put("picture", status.getUser().getProfileImageURL());
				obj.put("text", status.getText());
				tweetList.put(obj);
		    	System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
		    }
		
			response.setContentType("text/html; charset=UTF-8"); 
			response.getWriter().print(tweetList.toString(1)); 
		} catch (Exception e) {
			System.out.println("Unable to talk to Twitter. Is twitter4j.properties configured properly?");
			JSONObject obj = new JSONObject();
			try {
				obj.put("screenname", "Error");
				obj.put("picture", "");
				obj.put("text", "Is twitter4j.properties configured properly?");
				tweetList.put(obj);
				
				response.setContentType("text/html; charset=UTF-8"); 
				System.out.println(tweetList.toString(1));
				response.getWriter().print(tweetList.toString(1));
			} catch (JSONException e1) {
				System.err.println("JSONException");
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	

	
}
