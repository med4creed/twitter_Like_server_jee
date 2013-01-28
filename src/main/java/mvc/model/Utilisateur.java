package mvc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonBackReference;

@Entity
@Table(name = "UTILISATEUR")
@SequenceGenerator(name = "seq", initialValue = 10, allocationSize = 100)
public class Utilisateur implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	private long id;
	@Column(length = 30, nullable = false)
	private String nom;
	@Column(length = 30, nullable = false)
	private String pnom;
	@Column(length = 30, nullable = false, unique = true)
	private String pseudo;
	@Column(nullable = false, unique = true)
	private String mail;
	@Column(length = 30)
	private String mdp;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateInscription = new Date();

	@JsonBackReference("user_grp")
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private List<Groupe> grpsAbonnement = new ArrayList<Groupe>();

	@JsonBackReference("userAdmin_grp")
	@OneToMany(mappedBy = "userAdmin", cascade = CascadeType.ALL)
	private List<Groupe> grpsAdmin = new ArrayList<Groupe>();

	@JsonBackReference("user_msg")
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Message> msgs = new ArrayList<Message>();

	@JsonBackReference
	private List<Utilisateur> userToFollow = new ArrayList<Utilisateur>();

	public Utilisateur() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPnom() {
		return pnom;
	}

	public void setPnom(String pnom) {
		this.pnom = pnom;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public Date getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(Date dateInscription) {
		this.dateInscription = dateInscription;
	}

	public List<Groupe> getGrpsAbonnement() {
		return grpsAbonnement;
	}

	public void setGrpsAbonnement(List<Groupe> grpsAbonnement) {
		this.grpsAbonnement = grpsAbonnement;
	}

	public List<Groupe> getGrpsAdmin() {
		return grpsAdmin;
	}

	public void setGrpsAdmin(List<Groupe> grpsAdmin) {
		this.grpsAdmin = grpsAdmin;
	}

	public List<Message> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<Message> msgs) {
		this.msgs = msgs;
	}

	public List<Utilisateur> getUserToFollow() {
		return userToFollow;
	}

	public void setUserToFollow(List<Utilisateur> userToFollow) {
		this.userToFollow = userToFollow;
	}

	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", nom=" + nom + ", pnom=" + pnom
				+ ", pseudo=" + pseudo + ", mail=" + mail + ", mdp=" + mdp
				+ ", dateInscription=" + dateInscription + ", grpsAbonnement="
				+ grpsAbonnement + ", grpsAdmin=" + grpsAdmin + ", msgs="
				+ msgs + "]";
	}

}
