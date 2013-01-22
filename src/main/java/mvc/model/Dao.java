package mvc.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class Dao implements DaoInterface {

	EntityManager em;

	public Dao() {

	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public void closeEm() {
		this.getEm().close();
	}

	// Utilisateur
	@Override
	public void createUser(Utilisateur user) {
		this.getEm().persist(user);
	}

	@Override
	public Utilisateur getUserByEmail(String mail) {
		Query req = this.getEm().createQuery(
				"select user from Utilisateur user where user.mail = :mail");
		req.setParameter("mail", mail);
		Utilisateur user = (Utilisateur) req.getSingleResult();
		return user;
	}

	@Override
	public Utilisateur getUserById(long idUser) {
		return this.getEm().find(Utilisateur.class, idUser);
	}

	@Override
	public Utilisateur getUserAdminByGrpId(long idGrp) {
		Query req = this.getEm().createQuery(
				"select grp.userAdmin from Groupe grp where grp.id = :idGrp");
		req.setParameter("idGrp", idGrp);
		Utilisateur admin = (Utilisateur) req.getSingleResult();
		return admin;
	}

	@Override
	public List<Utilisateur> getAllUsers() {
		Query req = this.getEm().createQuery(
				"select user from Utilisateur user");
		List<Utilisateur> users = (List<Utilisateur>) req.getResultList();
		return users;
	}

	@Override
	public List<Utilisateur> getAllMembersByGrpId(long idGrp) {
		Query req = this.getEm().createQuery(
				"select grp.members from Groupe grp where grp.id = :idGrp");
		req.setParameter("idGrp", idGrp);
		List<Utilisateur> users = (List<Utilisateur>) req.getResultList();
		return users;
	}

	@Override
	public List<Utilisateur> getAllUsersToFollow(long idUser) {
		Query req = this
				.getEm()
				.createQuery(
						"select user.userToFollow from Utilisateur user where user.id = :idUser");
		req.setParameter("idUser", idUser);
		List<Utilisateur> users = (List<Utilisateur>) req.getResultList();
		return users;
	}

	@Override
	public void updateUser(Utilisateur user) {
		this.getEm().merge(user);
	}

	@Override
	public void deleteUser(Utilisateur user) {
		this.getEm().remove(user);
	}

	@Override
	public void deleteUserById(long idUser) {
		this.getEm().remove(this.getEm().find(Utilisateur.class, idUser));
	}

	// Message
	@Override
	public void createMesage(Message msg) {
		this.getEm().persist(msg);
	}

	@Override
	public Message getMesageById(long idMsg) {
		return this.getEm().find(Message.class, idMsg);
	}

	@Override
	public List<Message> getAllMesages() {
		Query req = this.getEm().createQuery("select msg from Message msg");
		List<Message> msg = (List<Message>) req.getResultList();
		return msg;
	}

	@Override
	public List<Message> getAllMesagesByUserId(long idUser) {
		Query req = this.getEm().createQuery(
				"select msg from Message msg where msg.user.id = :idUser");
		req.setParameter("idUser", idUser);
		List<Message> msgs = (List<Message>) req.getResultList();
		return msgs;
		// return this.getEm().find(Utilisateur.class, idUser).getMsgs();
	}

	@Override
	public List<Message> getAllMesagesByGrpId(long idGrp) {
		Query req = this.getEm().createQuery(
				"select msg from Message msg where msg.grp.id = :idGrp");
		req.setParameter("idGrp", idGrp);
		List<Message> msgs = (List<Message>) req.getResultList();
		return msgs;
//		return this.getEm().find(Groupe.class, idGrp).getGrpMsgs();
	}

	@Override
	public void deleteMesage(Message msg) {
		this.getEm().remove(msg);
	}

	@Override
	public void deleteMesageById(long idMsg) {
		this.getEm().remove(this.getEm().find(Message.class, idMsg));
	}

	// Groupe
	@Override
	public void createGrp(Groupe grp) {
		this.getEm().persist(grp);
	}

	@Override
	public Groupe getGrpById(long idGrp) {
		return this.getEm().find(Groupe.class, idGrp);
	}

	@Override
	public List<Groupe> getAllGrps() {
		Query req = this.getEm().createQuery("select grp from Groupe grp");
		List<Groupe> grps = (List<Groupe>) req.getResultList();
		return grps;
	}

	@Override
	public List<Groupe> getAllGrpsByAdminId(long idAdmin) {
		Query req = this
				.getEm()
				.createQuery(
						"select admin.grpsAdmin from Utilisateur admin where admin.id = :idAdmin");
		req.setParameter("idAdmin", idAdmin);
		List<Groupe> grpsAdmin = (List<Groupe>) req.getResultList();
		return grpsAdmin;
	}

	@Override
	public List<Groupe> getAllGrpsByMemberId(long idMember) {
		Query req = this
				.getEm()
				.createQuery(
						"select user.grpsAbonnement from Utilisateur user where user.id = :idMember");
		req.setParameter("idMember", idMember);
		List<Groupe> grpsAbonnement = (List<Groupe>) req.getResultList();
		return grpsAbonnement;
	}

	@Override
	public void updateGrp(Groupe grp) {
		this.getEm().merge(grp);
	}

	@Override
	public void deleteGrp(Groupe grp) {
		this.getEm().remove(grp);
	}

	@Override
	public void deleteGrpById(long idGrp) {
		this.getEm().remove(this.getEm().find(Groupe.class, idGrp));
	}

}
