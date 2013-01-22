package mvc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name = "MESSAGE")
@SequenceGenerator(name = "seq", initialValue = 10, allocationSize = 100)
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	private long id;
	@Column
	private String msg;
	@Temporal(TemporalType.DATE)
	private Date dateCreation = new Date();

	@JsonManagedReference("user_msg")
	@ManyToOne
	private Utilisateur user;

	@JsonManagedReference("grp_msg")
	@ManyToOne
	private Groupe grp;

	public Message() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public Groupe getGrp() {
		return grp;
	}

	public void setGrp(Groupe grp) {
		this.grp = grp;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", msg=" + msg + ", dateCreation="
				+ dateCreation + ", user=" + user + ", grp=" + grp + "]";
	}

}
