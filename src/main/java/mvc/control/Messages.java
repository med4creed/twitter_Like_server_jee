package mvc.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import mvc.model.Dao;
import mvc.model.Groupe;
import mvc.model.Message;
import mvc.model.Utilisateur;

@Path("messages")
public class Messages {

	@POST
	@Path("create/{idUtil}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response createMessage(@PathParam("idUtil") long id,
			MultivaluedMap<String, String> params) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			Message msg = new Message();
			msg.setMsg(params.getFirst("msg"));
			msg.setDateCreation(new Date());
			Utilisateur user = dao.getUserById(id);
			msg.setUser(user);
			dao.createMesage(msg);
			utx.commit();

			// Renvoi des données
			return Response.ok(msg, MediaType.APPLICATION_JSON).build();

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de persistence dans la base");
			}
			throw new Exception(ex);
		}

	}

	@POST
	@Path("create/{idUtil}/{idGrp}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response createMessage(@PathParam("idUtil") long idUtil,
			@PathParam("idGrp") long idGrp,
			MultivaluedMap<String, String> params) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			Message msg = new Message();
			msg.setMsg(params.getFirst("msg"));
			msg.setDateCreation(new Date());
			Utilisateur user = dao.getUserById(idUtil);
			Groupe grp = dao.getGrpById(idGrp);
			msg.setUser(user);
			msg.setGrp(grp);
			dao.createMesage(msg);
			utx.commit();

			// Renvoi des données
			return Response.ok(msg, MediaType.APPLICATION_JSON).build();

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de persistence dans la base");
			}
			throw new Exception(ex);
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllMessages() throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			List<Message> msgs = dao.getAllMesages();
			utx.commit();

			// Renvoi des données
			return msgs;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {

			}
			throw new Exception(ex);
		}

	}

	@GET
	@Path("aSuivre/utilisateur/{idUtil}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllMessagesOfUsersToFollow(
			@PathParam("idUtil") long idUtil) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			List<Message> msgs = new ArrayList<Message>();
			List<Utilisateur> users = dao.getAllUsersToFollow(idUtil);
			for (Utilisateur user : users) {
				msgs.addAll(dao.getAllMesagesByUserId(user.getId()));
			}
			utx.commit();

			// Renvoi des données
			return msgs;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {

			}
			throw new Exception(ex);
		}

	}

	@GET
	@Path("utilisateur/{idUtil}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllMessagesOfUtilisateur(
			@PathParam("idUtil") long idUtil) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			List<Message> msgs = dao.getAllMesagesByUserId(idUtil);
			utx.commit();

			// Renvoi des données
			return msgs;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {

			}
			throw new Exception(ex);
		}

	}

	@GET
	@Path("groupe/{idGrp}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getAllMessaagesOfGroupe(@PathParam("idGrp") long idGrp)
			throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			List<Message> msgs = dao.getAllMesagesByGrpId(idGrp);
			utx.commit();

			// Renvoi des données
			return msgs;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {

			}
			throw new Exception(ex);
		}

	}

	@DELETE
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response deleteMessaagee(@PathParam("id") long id) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			dao.deleteMesageById(id);
			utx.commit();

			// Renvoi des données
			return Response.ok().build();

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {

			}
			throw new Exception(ex);
		}

	}
}