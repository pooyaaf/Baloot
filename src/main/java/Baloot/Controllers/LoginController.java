package Baloot.Controllers;

import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.User;
import Baloot.Model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.Calendar;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.HashMap;

class   GithubUser {
    public String login;
    public String email;
    public Date createdAt;
    public String name;
}


@RestController
public class LoginController {
    private String clientSecret = "a0e7ed7a8da1513b7e3f57128d5d831f0cde78c1";
    private String clientId = "d4723977604163f60960";

    @GetMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public String auth(@RequestParam(value = "code") String code) throws Exception {
        String accessTokenURL = String.format(
                "https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s",
                clientId, clientSecret, code
        );
        HttpClient client = HttpClient.newHttpClient();
        URI accessTokenURI = URI.create(accessTokenURL);
        HttpRequest.Builder accessTokenBuilder = HttpRequest.newBuilder().uri(accessTokenURI);
        HttpRequest accessTokenRequest = accessTokenBuilder.POST(HttpRequest.BodyPublishers.noBody())
                .header("Accept", "application/json").build();
        HttpResponse<String> accessTokenResult = client.send(accessTokenRequest, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> resultBody = mapper.readValue(accessTokenResult.body(), HashMap.class);
        String accessToken = (String) resultBody.get("access_token");
        URI userDateURI = URI.create("https://api.github.com/user");
        HttpRequest.Builder userDataBuilder = HttpRequest.newBuilder().uri(userDateURI);
        HttpRequest req = userDataBuilder.GET().header("Authorization", String.format("token %s", accessToken)).build();
        HttpResponse<String> userDataResult = client.send(req, HttpResponse.BodyHandlers.ofString());
        HashMap<String, Object> userData = mapper.readValue(userDataResult.body(), HashMap.class);

        String email = (String) userData.get("email");
        String name = (String) userData.get("name");
        String nickname = (String) userData.get("login");
        String birth_date = (String) userData.get("created_at");
//        DataBase.getInstance().addUserWithGithub(email, null, name, nickname, birth_date);

        return userDataResult.body();
    }

    @PostMapping("/login")
    public void login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (ContextManager.getInstance().isUserPassExist(username, password)) {
            UserContext.username = username;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username and password");
        }
    }
}
