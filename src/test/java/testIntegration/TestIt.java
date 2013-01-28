package testIntegration;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class TestIt {

	private String baseUrl;
	private WebResource wr;

	@Before
	public void setUp() throws Exception {
		this.baseUrl = "http://127.0.0.1:8080/twitterLikeTest-1.1-SNAPSHOT/resources";
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		Client client = Client.create(clientConfig);
		URL url = new URL(baseUrl);
		wr = client.resource(url.toURI());
	}

	// ////////////////// tests utilisateurs

	@Test
	public void testGetAllUsers() throws Exception {
		ClientResponse resp = wr.path("utilisateurs")
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		String str = resp.getEntity(String.class);
		assertEquals(200, resp.getStatus());
		assertEquals(true, str.contains("nom\":\"Timeridjine"));
		assertEquals(true, str.contains("nom\":\"Atailia"));
		assertEquals(true, str.contains("nom\":\"Benlloch"));
	}

	@Test
	public void testGetUtilisateur() throws Exception {
		ClientResponse resp = wr.path("utilisateurs/10")
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		String str = resp.getEntity(String.class);

		assertEquals(200, resp.getStatus());
		assertEquals(true, str.contains("nom\":\"Timeridjine"));
	}

	@Test
	public void testAuthentification() throws Exception {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("mail", "farestimeridjine@gmail.com");
		params.add("mdp", "fares");
		ClientResponse resp = wr.path("utilisateurs/authentification")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, params);
		String str = resp.getEntity(String.class);

		assertEquals(200, resp.getStatus());
		assertEquals(true, str.contains("nom\":\"Timeridjine"));
	}

	@Test
	public void testUserToFollow() throws Exception {
		String str = wr.path("utilisateurs/" + 13 + "/utilisateurASuivre")
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		
		assertEquals(true, str.contains("nom\":\"Timeridjine"));
	}

	@Test
	public void testGetUsersOfGrp() throws Exception {
		String str = wr.path("utilisateurs/groupe/" + 12)
				.accept(MediaType.APPLICATION_JSON).get(String.class);

		assertEquals(true, str.contains("nom\":\"Benlloch"));
		assertEquals(true, str.contains("nom\":\"Atailia"));

	}

	// /////////////////// tests groupes

	@Test
	public void testGetAllGrps() throws Exception {
		String str = wr.path("groupes").accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		assertEquals(true, str.contains("nomGroupe\":\"groupe fares"));
		assertEquals(true, str.contains("nomGroupe\":\"groupe yann"));
		assertEquals(true, str.contains("nomGroupe\":\"groupe mohammed"));
	}

	@Test
	public void testGetGrp() throws Exception {
		String str = wr.path("groupes/" + 12)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		assertEquals(true, str.contains("nomGroupe\":\"groupe fares"));
	}

	@Test
	public void testGetGrpsOfUser() throws Exception {
		String str = wr.path("groupes/utilisateur/" + 13)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		assertEquals(true, str.contains("nomGroupe\":\"groupe fares"));
	}

	@Test
	public void testGetGrpsOfAdmin() throws Exception {
		String str = wr.path("groupes/admin/" + 13)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		assertEquals(true, str.contains("nomGroupe\":\"groupe yann"));
	}

	// /////////////////// tests messages

	@Test
	public void testGetAllMsgs() throws Exception {
		String str = wr.path("messages").accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		assertEquals(true, str.contains("msg\":\"message fares"));
		assertEquals(true, str.contains("msg\":\"message yann"));
		assertEquals(true, str.contains("msg\":\"message mohammed"));
	}

	@Test
	public void testGetAllMsgsOfUser() throws Exception {
		String str = wr.path("messages/utilisateur/" + 10)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		assertEquals(true, str.contains("msg\":\"message fares"));
	}

	@Test
	public void testGetAllMsgsOfUserToFollow() throws Exception {
		String str = wr.path("messages/aSuivre/utilisateur/" + 13)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		assertEquals(true, str.contains("msg\":\"message fares"));
	}

	@Test
	public void testGetAllMsgsOfGrp() throws Exception {
		String str = wr.path("messages/groupe/" + 12)
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		assertEquals(true,
				str.contains("msg\":\"message de yann dans le groupe fares"));
	}


}
