package Baloot.Controllers;

import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.User;
import Baloot.Exception.UserNotFound;
import Baloot.Model.AuthenticatedModel;
import Baloot.Model.UserModel;
import Baloot.Utilities.HashCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
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
import java.util.Objects;

@RestController
public class LoginController {
    private String clientSecret = "fd22ab66840eac5dcd03624b27cca4051b915607";
    private String clientId = "f3cc203f5df4b2e30d5c";
    public static final String KEY = "baloot2023";

    private String createToken(String userId) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date exp = c.getTime();

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, KEY)
                .setHeaderParam("typ", "JWT")
                .setIssuer("baloot")
                .setIssuedAt(new Date())
                .setExpiration(exp)
                .claim("userId", userId)
                .compact();

        return jwt;
    }


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
        UserModel model = new UserModel();
        model.password = null;
        model.email = (String) userData.get("email");
        model.username = (String) userData.get("login");
        model.address = (String) userData.get("location");

        String createdAtString = (String) userData.get("created_at");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date createdAt = dateFormat.parse(createdAtString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdAt);
        calendar.add(Calendar.YEAR, -18);
        model.birthDate = dateFormat.format(calendar.getTime());

        //Database
        ContextManager.getInstance().putUser(model.username, new User(model));

        return userDataResult.body();
    }

    @SneakyThrows
    @PostMapping("/login")
    public AuthenticatedModel login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (username.isEmpty() || password.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password is empty");

        boolean check = ContextManager.getInstance().isUserPassExist(username, HashCreator.getInstance().getMD5Hash(password));
        if (!check) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password is incorrect");
        }
        User user = ContextManager.getInstance().getUser(username);
        UserContext.username = username;
        String jwt = createToken(user.getUsername());
        AuthenticatedModel model = new AuthenticatedModel();
        model.login = jwt;
        model.userId = username;
        return model;
    }
}
