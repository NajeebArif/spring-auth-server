package narif.manslp.msoauth2.authserver;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.PublicKey;
import java.security.interfaces.RSAKey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("Auth Server Specs:")
class AuthserverApplicationTests {

	public static final String KEYSTORE_JKS = "narif-keystore.jks";
	public static final String STORE_PASS = "nArif@123";
	public static final String ALIAS = "narif-key-pair";
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	@DisplayName("Should issue tokens for client credentials flow, in the absence of user(backend-backend interaction).")
	public void testClientCredentials() throws Exception {
		mockMvc.perform(post("/oauth/token")
				.queryParam("grant_type","client_credentials")
				.queryParam("scope","read")
				.with(SecurityMockMvcRequestPostProcessors.httpBasic("machineClientId","machineClientSecret"))
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.jti",is(notNullValue())));
	}

	@Test
	@DisplayName("Issued JWTs must be valid JWT and must be signed with Private Key.")
	public void testJwtSignature() throws JSONException {
		final String uriString = getAuthTokenUrlWithQueryParams();
		final HttpHeaders httpHeaders = getHttpHeaders();
		final ResponseEntity<String> stringResponseEntity = getJwtResponseEntity(uriString, httpHeaders);

		assertThat(stringResponseEntity.getStatusCodeValue()).isEqualTo(200);

		final var jwt = stringResponseEntity.getBody();
		JSONObject obj = new JSONObject(jwt);
		final var tokenType = obj.getString("token_type");
		assertThat(tokenType).isNotNull().isNotEmpty().isNotBlank().isEqualTo("bearer");

		validateJwtSignatureWithPublicKey(obj);
	}


	@Test
	@DisplayName("The access token issues should have jti property.")
	public void testJtiPresence() throws Exception {
		mockMvc.perform(post("/oauth/token")
				.queryParam("grant_type","password")
				.queryParam("username","narif")
				.queryParam("password","strongPassword")
				.queryParam("scope","read")
				.with(SecurityMockMvcRequestPostProcessors.httpBasic("clientId","clientSecret"))
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.jti",is(notNullValue())));
	}

	@Test
	@DisplayName("If proper client details and user details are provided, then access token should be returned.")
	public void testValidTokenGen() throws Exception {
		mockMvc.perform(post("/oauth/token")
				.queryParam("grant_type","password")
				.queryParam("username","narif")
				.queryParam("password","strongPassword")
				.queryParam("scope","read")
				.with(SecurityMockMvcRequestPostProcessors.httpBasic("clientId","clientSecret"))
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token_type",is("bearer")));
	}

	@Test
	@DisplayName("Call to token url should return 401, if client info is missing.")
	void testAuthTokenUrlAccess() throws Exception{
		mockMvc.perform(get("/oauth/token"))
				.andExpect(status().isUnauthorized());
	}

	//<editor-fold desc="HELPER FOR JWT VALIDATION">
	private void validateJwtSignatureWithPublicKey(JSONObject obj) throws JSONException {
		final DecodedJWT decodedJWT = getDecodedJWT(obj);
		final Algorithm algorithm = getAlgorithmForPublicKey();
		assertThat(decodedJWT).isNotNull();
		algorithm.verify(decodedJWT);
	}

	private DecodedJWT getDecodedJWT(JSONObject obj) throws JSONException {
		final var accessToken = obj.getString("access_token");
		final var decodedJWT = JWT.decode(accessToken);
		return decodedJWT;
	}

	private Algorithm getAlgorithmForPublicKey() {
		final PublicKey aPublic = getPublicKey();
		final var algorithm = Algorithm.RSA256((RSAKey) aPublic);
		return algorithm;
	}

	private PublicKey getPublicKey() {
		final var keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(KEYSTORE_JKS), STORE_PASS.toCharArray());
		final var keyPair = keyStoreKeyFactory.getKeyPair(ALIAS);
		final var aPublic = keyPair.getPublic();
		return aPublic;
	}

	private ResponseEntity<String> getJwtResponseEntity(String uriString, HttpHeaders httpHeaders) {
		final var stringResponseEntity = testRestTemplate.withBasicAuth("clientId", "clientSecret")
				.postForEntity(uriString, new HttpEntity<>(httpHeaders), String.class);
		return stringResponseEntity;
	}

	private HttpHeaders getHttpHeaders() {
		final var httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		return httpHeaders;
	}

	private String getAuthTokenUrlWithQueryParams() {
		final var uriString = UriComponentsBuilder.fromUriString("/oauth/token")
				.queryParam("grant_type", "password")
				.queryParam("username", "narif")
				.queryParam("password", "strongPassword")
				.queryParam("scope", "read")
				.toUriString();
		return uriString;
	}
	//</editor-fold>
}
