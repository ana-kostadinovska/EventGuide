package mk.ukim.finki.eventguidefrontend.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events")
public class EventController {
    private final String backendUrl = "http://localhost:9090/api/events";

    @GetMapping
    public String getEvents(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token="Bearer "+"eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI3MDVjMjlhYy04MGYxLTcwZjYtZWNkYS01YjE4NmE1NTI5NTciLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6IjIyNDI4NjdiLTNkZmItNDAzMC1iODNiLWViZTY3Y2EwNGMzOSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoicGhvbmUgb3BlbmlkIGVtYWlsIiwiYXV0aF90aW1lIjoxNzQwMjI5OTkwLCJleHAiOjE3NDAyMzAyOTAsImlhdCI6MTc0MDIyOTk5MCwianRpIjoiNjQ0YjQxZDItYzk3NC00ZjUzLWI2NmEtNjM0MGNmODUwZDhjIiwidXNlcm5hbWUiOiI3MDVjMjlhYy04MGYxLTcwZjYtZWNkYS01YjE4NmE1NTI5NTcifQ.14m_tg_oEQexzJtVjdj3huzeMcTiz7w9fOVhbL6yyaRycxL2xyC6cNrJ-ojCUIngq7VQPe6efsq_NXV8sMH2Hn2xANAuPc_bCziSTGU1l35c7qn1LXItbON3CCS8h_WGr5DptyDcNlqc-Iz2AKkcKTKYZzNOsTTUpI9VV72QtQRFt2QzcCD8O59Nehaf4BvqCUiKkncXMR_JMIMsdudqwP2jUoRbkhC86tXrMFl3mFdXbzFoZIThhWGgtB7q383awdIRFwjfpnj2-LJZOiX8sxOrJ5ZJod9K-pRBFyn121INJwf1kj0uysdtChFAiAXdeizAT4lfz92o67p55IsEww";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(
                backendUrl, HttpMethod.GET, entity, List.class
        );

        model.addAttribute("events", response.getBody());
        model.addAttribute("bodyContent", "home");

        return "template";
    }

    @GetMapping("/{id}")
    public String getEventDetails(@PathVariable Long id, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token="Bearer "+"eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI3MDVjMjlhYy04MGYxLTcwZjYtZWNkYS01YjE4NmE1NTI5NTciLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6IjIyNDI4NjdiLTNkZmItNDAzMC1iODNiLWViZTY3Y2EwNGMzOSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoicGhvbmUgb3BlbmlkIGVtYWlsIiwiYXV0aF90aW1lIjoxNzQwMjI5OTkwLCJleHAiOjE3NDAyMzAyOTAsImlhdCI6MTc0MDIyOTk5MCwianRpIjoiNjQ0YjQxZDItYzk3NC00ZjUzLWI2NmEtNjM0MGNmODUwZDhjIiwidXNlcm5hbWUiOiI3MDVjMjlhYy04MGYxLTcwZjYtZWNkYS01YjE4NmE1NTI5NTcifQ.14m_tg_oEQexzJtVjdj3huzeMcTiz7w9fOVhbL6yyaRycxL2xyC6cNrJ-ojCUIngq7VQPe6efsq_NXV8sMH2Hn2xANAuPc_bCziSTGU1l35c7qn1LXItbON3CCS8h_WGr5DptyDcNlqc-Iz2AKkcKTKYZzNOsTTUpI9VV72QtQRFt2QzcCD8O59Nehaf4BvqCUiKkncXMR_JMIMsdudqwP2jUoRbkhC86tXrMFl3mFdXbzFoZIThhWGgtB7q383awdIRFwjfpnj2-LJZOiX8sxOrJ5ZJod9K-pRBFyn121INJwf1kj0uysdtChFAiAXdeizAT4lfz92o67p55IsEww";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                backendUrl + "/" + id, HttpMethod.GET, entity, Map.class
        );

        model.addAttribute("event", response.getBody());
        return "event-details";
    }
    @GetMapping("/interested/{userId}")
    public String getInterestedEvents(@PathVariable Long userId, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token="Bearer "+"eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI3MDVjMjlhYy04MGYxLTcwZjYtZWNkYS01YjE4NmE1NTI5NTciLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6IjIyNDI4NjdiLTNkZmItNDAzMC1iODNiLWViZTY3Y2EwNGMzOSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoicGhvbmUgb3BlbmlkIGVtYWlsIiwiYXV0aF90aW1lIjoxNzQwMjI5OTkwLCJleHAiOjE3NDAyMzAyOTAsImlhdCI6MTc0MDIyOTk5MCwianRpIjoiNjQ0YjQxZDItYzk3NC00ZjUzLWI2NmEtNjM0MGNmODUwZDhjIiwidXNlcm5hbWUiOiI3MDVjMjlhYy04MGYxLTcwZjYtZWNkYS01YjE4NmE1NTI5NTcifQ.14m_tg_oEQexzJtVjdj3huzeMcTiz7w9fOVhbL6yyaRycxL2xyC6cNrJ-ojCUIngq7VQPe6efsq_NXV8sMH2Hn2xANAuPc_bCziSTGU1l35c7qn1LXItbON3CCS8h_WGr5DptyDcNlqc-Iz2AKkcKTKYZzNOsTTUpI9VV72QtQRFt2QzcCD8O59Nehaf4BvqCUiKkncXMR_JMIMsdudqwP2jUoRbkhC86tXrMFl3mFdXbzFoZIThhWGgtB7q383awdIRFwjfpnj2-LJZOiX8sxOrJ5ZJod9K-pRBFyn121INJwf1kj0uysdtChFAiAXdeizAT4lfz92o67p55IsEww";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(
                backendUrl + "/interested/" + userId, HttpMethod.GET, entity, List.class
        );

        model.addAttribute("interestedEvents", response.getBody());
        return "interested-events";
    }
}

