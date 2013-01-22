package mvc.model;

import java.util.List;

public interface DaoInterface {

	// DAO Utilisateurs
	public void createUser(Utilisateur user);
	
	public Utilisateur getUserByEmail(String mail);

	public Utilisateur getUserById(long idUser);

	public Utilisateur getUserAdminByGrpId(long idGrp);

	public List<Utilisateur> getAllUsers();

	public List<Utilisateur> getAllMembersByGrpId(long idGrp);
	
	public List<Utilisateur> getAllUsersToFollow(long idUser);

	public void updateUser(Utilisateur user);

	public void deleteUser(Utilisateur user);

	public void deleteUserById(long idUser);

	// DAO Messages
	public void createMesage(Message msg);

	public Message getMesageById(long idMsg);

	public List<Message> getAllMesages();

	public List<Message> getAllMesagesByUserId(long idUser);

	public List<Message> getAllMesagesByGrpId(long idGrp);

	public void deleteMesage(Message msg);

	public void deleteMesageById(long idMsg);

	// DAO Groupes
	public void createGrp(Groupe grp);

	public Groupe getGrpById(long idGrp);

	public List<Groupe> getAllGrps();

	public List<Groupe> getAllGrpsByAdminId(long idAdmin);

	public List<Groupe> getAllGrpsByMemberId(long idMember);

	public void updateGrp(Groupe grp);

	public void deleteGrp(Groupe grp);

	public void deleteGrpById(long idGrp);

}
