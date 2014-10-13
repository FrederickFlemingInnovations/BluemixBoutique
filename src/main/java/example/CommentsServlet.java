package example;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * Servlet implementation class CommentsServlet
 */
@WebServlet("/CommentsServlet")
public class CommentsServlet extends HttpServlet {

	@Resource(name = "couchdb/bluemixboutiquecloudant")
	protected CouchDbInstance _db;
	String dbname = "blueplants_db";

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		JSONArray result = new JSONArray();

		switch (action) {
	        case "getComments":
	        	result = getComments(request.getParameter("itemNumber"));
	            break;
	         case "addComment":
	        	 addComment(request.getParameter("itemNumber"), request.getParameter("comment"));
	        	 break;
		}
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().print(result.serialize());
		return;
	}

	private void addComment(String itemNumber, String comment){

    	CouchDbConnector dbc = _db.createConnector(dbname, true);
    	Map<String, String> doc = new  HashMap<String, String>();
    	doc.put("_id",  UUID.randomUUID().toString());
    	doc.put("itemNumber", itemNumber);
    	doc.put("comment", comment);
    	dbc.create(doc);
	}

	private JSONArray getComments(String itemNumber) {
		JSONArray returnArray = new JSONArray();
		CouchDbConnector dbc = _db.createConnector(dbname, true);

		Map<String, String> doc = new HashMap<String, String>();

		ViewQuery query = new ViewQuery().allDocs().includeDocs(true);
		List<Map> result = dbc.queryView(query, Map.class);
		JSONArray jsonresult = new JSONArray();
		for (Map element : result) {
			JSONObject obj = new JSONObject();
			obj.putAll(element);
			if(itemNumber==null || obj.get("itemNumber").equals(itemNumber))
				jsonresult.add(obj);
		}

		System.out.println(jsonresult.toString());

		return jsonresult;

	}


}
