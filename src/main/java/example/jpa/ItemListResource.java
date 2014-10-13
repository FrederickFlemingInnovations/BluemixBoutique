package example.jpa;

import java.util.List;

import javax.inject.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


@Singleton
@Path("/itemlist")
//@Scope(ScopeType.SINGLETON)



/**
 * CRUD service of todo list table. It uses REST style.
 *
 */
public class ItemListResource {
	UserTransaction userTran;
	static {
		getEm();
	}

	public ItemListResource(){
		InitialContext ic;
		try {
			ic = new InitialContext();
			userTran=(UserTransaction)ic.lookup("java:comp/UserTransaction");
		} catch (NamingException e) {
			System.err.println("ERROR obtaining UserTransaction" );
		}
	}

	// There are two ways of obtaining the connection information for some services in Java

	// Method 1: Auto-configuration and JNDI
	// The Liberty buildpack automatically generates server.xml configuration
	// stanzas for the SQL Database service which contain the credentials needed to
	// connect to the service. The buildpack generates a JNDI name following
	// the convention of "jdbc/<service_name>" where the <service_name> is the
	// name of the bound service.
	// Below we'll do a JNDI lookup for the EntityManager whose persistence
	// context is defined in web.xml. It references a persistence unit defined
	// in persistence.xml. In these XML files you'll see the "jdbc/<service name>"
	// JNDI name used.

	// Method 2: Parsing VCAP_SERVICES environment variable (Not used)
    // The VCAP_SERVICES environment variable contains all the credentials of
	// services bound to this application. You can parse it to obtain the information
	// needed to connect to the SQL Database service. SQL Database is a service
	// that the Liberty buildpack auto-configures as described above, so parsing
	// VCAP_SERVICES is not a best practice.

	private static EntityManager getEm() {
		InitialContext ic;
		try {
			ic = new InitialContext();
			return (EntityManager) ic.lookup("java:comp/env/bluemixboutiquepu/entitymanager");
		} catch (NamingException e) {
			System.out.println("ERROR obtaining EntityManager" );
			e.printStackTrace();
		}
		return null;
	}

	@POST
	public Response create(@FormParam("name") String name, @FormParam("imgsrc") String imgsrc, @FormParam("itemNumber") String itemNumber, @FormParam("description") String description, @FormParam("price") String price){
		ITEM item = new ITEM();
		item.setName(name);
		item.setImgSrc(imgsrc);
		item.setItemNumber(itemNumber);
		item.setDescription(description);
		item.setPrice(price);
		EntityManager em = getEm();
		try{
			userTran.begin();
			em.persist(item);
			userTran.commit();
			return Response.ok(item.toString()).build();
		} catch (Exception e){
			System.err.println("ERROR creating record" );
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}


	}

	@DELETE
	public Response delete(@QueryParam("id") long id){
		EntityManager em = getEm();

			try {
				userTran.begin();
				ITEM item = em.find(ITEM.class, id);
				if(item != null){
					em.remove(item);
					userTran.commit();
				} else {
					return Response.status(Status.NOT_FOUND).build();
				}
			} catch (Exception e) {
				System.err.println("ERROR deleting record" );
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
			return Response.ok().build();

	}

	@PUT
	public Response update(@FormParam("id") long id, @FormParam("name") String name, @FormParam("age") int age){
		EntityManager em = getEm();
		ITEM item = em.find(ITEM.class, id);
		if(item != null){
			item.setName(name);
			try {
				userTran.begin();
				em.refresh(item);
				userTran.commit();
			} catch (Exception e) {
				System.err.println("ERROR updating record" );
			} finally {
				em.close();
			}
			return Response.ok().build();
		}  else
			return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@QueryParam("id") long id) throws Exception {
		EntityManager em = getEm();
		if( id == 0 ){
			List<ITEM> list = em.createQuery("SELECT t FROM ITEM t", ITEM.class).getResultList();
			String json = "{\"id\":\"all\", \"body\":" + list.toString() + "}";
			return Response.ok(json).build();
		}
		ITEM item = null;
		try{
			userTran.begin();
			item = em.find(ITEM.class, id);
			userTran.commit();
		} catch(Exception e){
			em.close();
		}
		if(item != null)
			return Response.ok(item.toString()).build();
		else
			return Response.status(Status.NOT_FOUND).build();

	}







}
