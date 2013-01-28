package mvc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name = "GROUPE")
@SequenceGenerator(name = "seq", initialValue = 10, allocationSize = 100)
public class Groupe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	private long id;
	@Column
	private String nomGroupe;
	@Column
	private String description;
	@Temporal(TemporalType.DATE)
	private Date dateCreation = new Date();

	@JsonManagedReference("user_grp")
	@ManyToMany(mappedBy = "grpsAbonnement", cascade = { CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private List<Utilisateur> members = new ArrayList<Utilisateur>();

	@JsonManagedReference("userAdmin_grp")
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private Utilisateur userAdmin;

	@JsonBackReference("grp_msg")
	@OneToMany(mappedBy = "grp", cascade = CascadeType.ALL)
	private List<Message> grpMsgs = new ArrayList<Message>();

	public Groupe() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNomGroupe() {
		return nomGroupe;
	}

	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public List<Utilisateur> getMembers() {
		return members;
	}

	public void setMembers(List<Utilisateur> members) {
		this.members = members;
	}

	public Utilisateur getUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(Utilisateur userAdmin) {
		this.userAdmin = userAdmin;
	}

	public List<Message> getGrpMsgs() {
		return grpMsgs;
	}

	public void setGrpMsgs(List<Message> grpMsgs) {
		this.grpMsgs = grpMsgs;
	}

	@Override
	public String toString() {
		return "Groupe [id=" + id + ", nomGroupe=" + nomGroupe
				+ ", dateCreation=" + dateCreation + ", members=" + members
				+ ", userAdmin=" + userAdmin + ", grpMsgs=" + grpMsgs + "]";
	}

}
