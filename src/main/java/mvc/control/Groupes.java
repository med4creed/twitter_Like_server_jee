package mvc.control;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import mvc.model.Dao;
import mvc.model.Groupe;
import mvc.model.Utilisateur;

@Path("groupes")
public class Groupes {

	@POST
	@Path("create/{idUtil}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response createGroupe(@PathParam("idUtil") long id,
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
			Groupe grp = new Groupe();
			grp.setNomGroupe(params.getFirst("nomGroupe"));
			grp.setDescription(params.getFirst("description"));
			grp.setDateCreation(new Date());
			Utilisateur admin = dao.getUserById(id);
			grp.setUserAdmin(admin);
			dao.createGrp(grp);
			utx.commit();

			// Renvoi des données
			return Response.ok(grp, MediaType.APPLICATION_JSON).build();

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
	@Path("jsonCreate/{idUtil}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response jsonCreateGroupe(@PathParam("idUtil") long id,
			String jsonGrp) throws Exception {
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
			Groupe grp = new Groupe();
			ObjectMapper mapper = new ObjectMapper();
			try {
				grp = mapper.readValue(jsonGrp, Groupe.class);
			} catch (JsonParseException e1) {
				e1.printStackTrace();
			} catch (JsonMappingException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			dao.createGrp(grp);
			utx.commit();

			// Renvoi des données
			return Response.ok(grp, MediaType.APPLICATION_JSON).build();

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
	public List<Groupe> getAllGroupes() throws Exception {
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
			List<Groupe> grps = dao.getAllGrps();
			utx.commit();

			// Renvoi des données
			return grps;

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
	@Path("{idGrpe}")
	@Produces(MediaType.APPLICATION_JSON)
	public Groupe getGroupe(@PathParam("idGrpe") long id) throws Exception {
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
			Groupe grp = dao.getGrpById(id);
			utx.commit();

			// Renvoi des données
			return grp;

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
	@Path("utilisateur/{idMember}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Groupe> getAllGroupesOfUtilisateur(
			@PathParam("idMember") long idMember) throws Exception {
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
			List<Groupe> grps = dao.getAllGrpsByMemberId(idMember);
			utx.commit();

			// Renvoi des données
			return grps;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				// Impossible d'annuler les changements, vous devriez logguer
				// une erreur,
				// voir envoyer un email à l'exploitant de l'application.
			}
			throw new Exception(ex);
		}

	}

	@GET
	@Path("admin/{idAdmin}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Groupe> getAllGroupesOfAdmin(@PathParam("idAdmin") long idAdmin)
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
			List<Groupe> grps = dao.getAllGrpsByAdminId(idAdmin);
			utx.commit();

			// Renvoi des données
			return grps;

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

	@PUT
	@Path("update/{idGrpe}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateGroupe(@PathParam("idGrpe") long id,
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
			Groupe grp = dao.getGrpById(id);
			if (params.getFirst("nomGroupe") != null)
				grp.setNomGroupe(params.getFirst("nomGroupe"));
			if (params.getFirst("description") != null)
				grp.setDescription(params.getFirst("description"));
			dao.updateGrp(grp);
			utx.commit();

			// Renvoi des données
			return Response.ok(grp, MediaType.APPLICATION_JSON).build();

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

	@DELETE
	@Path("delete/{idGrpe}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response deleteGroupe(@PathParam("idGrpe") long id) throws Exception {
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
			dao.deleteGrpById(id);
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
