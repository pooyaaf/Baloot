package Baloot.Controllers;

import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.User;
import Baloot.Model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
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

class GithubUser {
    public String login;
    public String email;
    public Date createdAt;
    public String name;
}


@RestController
public class LoginController {
    private String clientSecret = "0954082025c33039693fc39544446ae97893abfc";
    private String clientId = "c2527d4cba3fd0dbd9c0";

    @GetMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody String code) {
        String accessTokenString = String.format(
                "https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s", clientId,
                clientSecret, code);

        HttpClient client = HttpClient.newHttpClient();
        URI accessTokenUri = URI.create(accessTokenString);
        HttpRequest.Builder accessTokenBuilder = HttpRequest.newBuilder().uri(accessTokenUri);
        HttpRequest accessTokenRequest = accessTokenBuilder.POST(HttpRequest.BodyPublishers.noBody())
                .header("Accept", "Application/json").build();

        HttpResponse<String> accessTokenResult = null;
        try {
            accessTokenResult = client.send(accessTokenRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> resultBody = new HashMap<>();
        try {
            resultBody = mapper.readValue(accessTokenResult.body(), HashMap.class);
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        String accessToken = (String) resultBody.get("access_token");

        URI userDataUri = URI.create("https://api.github.com/user");
        HttpRequest.Builder userDataBuilder = HttpRequest.newBuilder().uri(userDataUri);
        HttpRequest req = userDataBuilder.GET().header("Authorization", String.format("token %s", accessToken)).build();
        try {
            HttpResponse<String> userDataResult = client.send(req, HttpResponse.BodyHandlers.ofString());
            GithubUser user = mapper.readValue(userDataResult.body(), GithubUser.class);
            UserModel model = new UserModel();
            model.username = user.name;
            model.email = user.email;
            model.address = null;
            model.credit = 0;
            model.password=null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(user.createdAt);
            calendar.add(Calendar.YEAR, -18);
            model.birthDate = String.valueOf(calendar.getTime());
            // TODO :  ResponseEntity.ok(JwtService.createToken(user.username))
            return null;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
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
