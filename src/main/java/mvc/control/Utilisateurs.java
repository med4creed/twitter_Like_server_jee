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
import javax.ws.rs.core.Response.Status;

import mvc.model.Dao;
import mvc.model.Groupe;
import mvc.model.Utilisateur;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

@Path("/utilisateurs")
public class Utilisateurs {

	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUtilisateur(MultivaluedMap<String, String> params)
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
			Utilisateur user = new Utilisateur();
			user.setNom(params.getFirst("nom"));
			user.setPnom(params.getFirst("pnom"));
			user.setPseudo(params.getFirst("pseudo"));
			user.setMail(params.getFirst("mail"));
			user.setMdp(params.getFirst("mdp"));
			user.setDateInscription(new Date());
			dao.createUser(user);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

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
	@Path("jsonCreate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response jsonCreateUtilisateur(String jsonUser) throws Exception {
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
			Utilisateur user = new Utilisateur();
			ObjectMapper mapper = new ObjectMapper();
			try {
				user = mapper.readValue(jsonUser, Utilisateur.class);
			} catch (JsonParseException e1) {
				e1.printStackTrace();
			} catch (JsonMappingException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			dao.createUser(user);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

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
	@Path("authentification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authentificationUtilisateur(
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
			Utilisateur user = new Utilisateur();
			user = dao.getUserByEmail(params.getFirst("mail"));
			utx.commit();

			// Renvoi des données
			if (user != null) {
				if (params.getFirst("mdp").equals(user.getMdp())) {
					return Response.ok(user, MediaType.APPLICATION_JSON)
							.build();
				} else {
					return Response.status(Status.BAD_REQUEST).build();
				}
			}else{
				return Response.status(Status.NOT_FOUND).build();
			}

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème d'authentification");
			}
			throw new Exception(ex);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Utilisateur> getAllUtilisateurs() throws Exception {
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
			List<Utilisateur> users = dao.getAllUsers();
			utx.commit();

			// Renvoi des données
			return users;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de recherche de tous les utilisateurs!!");
			}
			throw new Exception(ex);
		}

	}

	@GET
	@Path("{idUtil}/utilisateurASuivre")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Utilisateur> getAllUtilisateursASuivre(
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
			List<Utilisateur> users = dao.getAllUsersToFollow(idUtil);
			utx.commit();

			// Renvoi des données
			return users;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de recherche de tous les utilisateurs à suivre!!");
			}
			throw new Exception(ex);
		}

	}

	@GET
	@Path("{idUtil}")
	@Produces(MediaType.APPLICATION_JSON)
	public Utilisateur getUtilisateur(@PathParam("idUtil") long id)
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
			Utilisateur user = dao.getUserById(id);
			utx.commit();

			// Renvoi des données
			return user;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de recherche d'utilisateur!!");
			}
			throw new Exception(ex);
		}

	}

	@GET
	@Path("groupe/{idgrp}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Utilisateur> getAllUtilisateursOfGroupe(
			@PathParam("idgrp") long idgrp) throws Exception {
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
			List<Utilisateur> members = dao.getAllMembersByGrpId(idgrp);
			utx.commit();

			// Renvoi des données
			return members;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de recherche de tous les utilisateurs d'un groupe!!");
			}
			throw new Exception(ex);
		}

	}

	@PUT
	@Path("{idUtil}/rejoindre/{idGrpe}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response rejoindreGroupe(@PathParam("idUtil") long idUtil,
			@PathParam("idGrpe") long idGrpe) throws Exception {
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
			Groupe grp = dao.getGrpById(idGrpe);
			Utilisateur user = dao.getUserById(idUtil);
			user.getGrpsAbonnement().add(grp);
			grp.getMembers().add(user);
			dao.updateGrp(grp);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

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

	@PUT
	@Path("{idUtil}/quitter/{idGrpe}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response quitterGroupe(@PathParam("idUtil") long idUtil,
			@PathParam("idGrpe") long idGrpe) throws Exception {
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
			Groupe grp = dao.getGrpById(idGrpe);
			Utilisateur user = dao.getUserById(idUtil);
			user.getGrpsAbonnement().remove(grp);
			grp.getMembers().remove(user);
			dao.updateGrp(grp);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

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

	@PUT
	@Path("{idUtil}/suivre/{idUtilAsuivre}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response suivreUtilisateur(@PathParam("idUtil") long idUtil,
			@PathParam("idUtilAsuivre") long idUtilAsuivre) throws Exception {
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
			Utilisateur user = dao.getUserById(idUtil);
			Utilisateur userToFollow = dao.getUserById(idUtilAsuivre);
			user.getUserToFollow().add(userToFollow);
			dao.updateUser(user);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

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

	@PUT
	@Path("{idUtil}/nePlusSuivre/{idUtilAsuivre}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response nePlusSuivreUtilisateur(@PathParam("idUtil") long idUtil,
			@PathParam("idUtilAsuivre") long idUtilAsuivre) throws Exception {
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
			Utilisateur user = dao.getUserById(idUtil);
			Utilisateur userToFollow = dao.getUserById(idUtilAsuivre);
			user.getUserToFollow().remove(userToFollow);
			dao.updateUser(user);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

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

	@PUT
	@Path("update/{idUtil}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateUtilisateur(@PathParam("idUtil") long id,
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
			Utilisateur user = dao.getUserById(id);
			if (params.getFirst("nom") != null)
				user.setNom(params.getFirst("nom"));
			if (params.getFirst("pnom") != null)
				user.setPnom(params.getFirst("pnom"));
			if (params.getFirst("pseudo") != null)
				user.setPseudo(params.getFirst("pseudo"));
			if (params.getFirst("mail") != null)
				user.setMail(params.getFirst("mail"));
			if (params.getFirst("mdp") != null)
				user.setMdp(params.getFirst("mdp"));
			dao.updateUser(user);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

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
	@Path("delete/{idUtil}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response deleteUtilisateur(@PathParam("idUtil") long id)
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
			dao.deleteUserById(id);
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
