package narif.manslp.msoauth2.authserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Auth Server Specs:")
class AuthserverApplicationTests {

	@Autowired
	private MockMvc mockMvc;

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

}
