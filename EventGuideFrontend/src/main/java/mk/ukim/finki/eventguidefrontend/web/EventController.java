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

        String token="Bearer "+"eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJmMGNjZDlkYy1mMDkxLTcwN2QtNThjZC01NTg0N2RmMGQwYjkiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6ImU3MWE0ZDY2LThkZTYtNGY3MC1iODM4LWI3YTQxMjhlNDc3MSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoicGhvbmUgb3BlbmlkIGVtYWlsIiwiYXV0aF90aW1lIjoxNzM4NDU2Nzk5LCJleHAiOjE3Mzg0NTcwOTksImlhdCI6MTczODQ1Njc5OSwianRpIjoiZWNhYjMzOWEtMmFkNC00YTI1LWIwZjQtYTMyYTA0NzRlNTlmIiwidXNlcm5hbWUiOiJmMGNjZDlkYy1mMDkxLTcwN2QtNThjZC01NTg0N2RmMGQwYjkifQ.k2-xojbsPO_jaERmWf4sz922JeM0ddKnlkLmGyLSuSgw46VJfuoLsgk8x1T7cP8v17QcxxOYB5h3JW_Bs7tmzXqtCQBixK93m6emu3CvFQoTzjg-bXMQUB0Ysm_rmHEnlbm2iN5hlPzg9s83lAkCc0OV6cXWYUaBp9VRL4eHHyQKwbfrBxUXKXFa-0Sa3IQb2tds4_LU87phkfZd7TQHuolpnK6CSRYqp8G0qF8I6sdg_ekpLY4AfgjFuluWGUCN0BJISRXOyQDUPl8P19GHq258SG9_1cwqgRFpTWVbPMsn_6K3DyHQIbJu6-LhHIkjuTOF4Fb4E5sdf0SzNmV9rg";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(
                backendUrl, HttpMethod.GET, entity, List.class
        );

        model.addAttribute("events", response.getBody());
        return "events";
    }

    @GetMapping("/{id}")
    public String getEventDetails(@PathVariable Long id, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token="Bearer "+"eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJmMGNjZDlkYy1mMDkxLTcwN2QtNThjZC01NTg0N2RmMGQwYjkiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6ImU3MWE0ZDY2LThkZTYtNGY3MC1iODM4LWI3YTQxMjhlNDc3MSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoicGhvbmUgb3BlbmlkIGVtYWlsIiwiYXV0aF90aW1lIjoxNzM4NDU2Nzk5LCJleHAiOjE3Mzg0NTcwOTksImlhdCI6MTczODQ1Njc5OSwianRpIjoiZWNhYjMzOWEtMmFkNC00YTI1LWIwZjQtYTMyYTA0NzRlNTlmIiwidXNlcm5hbWUiOiJmMGNjZDlkYy1mMDkxLTcwN2QtNThjZC01NTg0N2RmMGQwYjkifQ.k2-xojbsPO_jaERmWf4sz922JeM0ddKnlkLmGyLSuSgw46VJfuoLsgk8x1T7cP8v17QcxxOYB5h3JW_Bs7tmzXqtCQBixK93m6emu3CvFQoTzjg-bXMQUB0Ysm_rmHEnlbm2iN5hlPzg9s83lAkCc0OV6cXWYUaBp9VRL4eHHyQKwbfrBxUXKXFa-0Sa3IQb2tds4_LU87phkfZd7TQHuolpnK6CSRYqp8G0qF8I6sdg_ekpLY4AfgjFuluWGUCN0BJISRXOyQDUPl8P19GHq258SG9_1cwqgRFpTWVbPMsn_6K3DyHQIbJu6-LhHIkjuTOF4Fb4E5sdf0SzNmV9rg";
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

        String token="Bearer "+"eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJmMGNjZDlkYy1mMDkxLTcwN2QtNThjZC01NTg0N2RmMGQwYjkiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6ImU3MWE0ZDY2LThkZTYtNGY3MC1iODM4LWI3YTQxMjhlNDc3MSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoicGhvbmUgb3BlbmlkIGVtYWlsIiwiYXV0aF90aW1lIjoxNzM4NDU2Nzk5LCJleHAiOjE3Mzg0NTcwOTksImlhdCI6MTczODQ1Njc5OSwianRpIjoiZWNhYjMzOWEtMmFkNC00YTI1LWIwZjQtYTMyYTA0NzRlNTlmIiwidXNlcm5hbWUiOiJmMGNjZDlkYy1mMDkxLTcwN2QtNThjZC01NTg0N2RmMGQwYjkifQ.k2-xojbsPO_jaERmWf4sz922JeM0ddKnlkLmGyLSuSgw46VJfuoLsgk8x1T7cP8v17QcxxOYB5h3JW_Bs7tmzXqtCQBixK93m6emu3CvFQoTzjg-bXMQUB0Ysm_rmHEnlbm2iN5hlPzg9s83lAkCc0OV6cXWYUaBp9VRL4eHHyQKwbfrBxUXKXFa-0Sa3IQb2tds4_LU87phkfZd7TQHuolpnK6CSRYqp8G0qF8I6sdg_ekpLY4AfgjFuluWGUCN0BJISRXOyQDUPl8P19GHq258SG9_1cwqgRFpTWVbPMsn_6K3DyHQIbJu6-LhHIkjuTOF4Fb4E5sdf0SzNmV9rg";
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

