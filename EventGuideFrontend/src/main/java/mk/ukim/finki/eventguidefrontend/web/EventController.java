package mk.ukim.finki.eventguidefrontend.web;

import mk.ukim.finki.eventguidefrontend.services.ApiService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events")
public class EventController {

    private final String backendUrl = "http://localhost:9090/api/events";
    private final ApiService apiService;

    public EventController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    public String getEvents(Model model, Authentication authentication) {
        String response = apiService.fetchData("/events", authentication);
        model.addAttribute("events", apiService.parseJsonList(response));
        return "events";
    }
    @GetMapping("/{id}")
    public String getEventDetails(@PathVariable Long id, Model model, Authentication authentication) {
        String response = apiService.fetchData("/events/" + id, authentication);
        System.out.println(response);
        if (response.startsWith("redirect:")) {
            return response;
        }

        model.addAttribute("event", apiService.parseJsonMap(response));
        return "event-details";
    }

//    @GetMapping("/{id}")
//    public String getEventDetails(@PathVariable Long id, Model model) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        String token = "Bearer " + "eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIzMGVjYzllYy0yMDkxLTcwZDktZjc2ZS0xZWRiN2RiZmI2ZWUiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6ImJjMmI5MjJmLWM4ZWYtNGExNC1iZjM0LTlhOGYzNmZhYzRlMSIsImV2ZW50X2lkIjoiNTA3NzViNjQtM2NmNS00ZmIyLWI4MjgtNmRkNjRkYzQ4MWE0IiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJwaG9uZSBvcGVuaWQgZW1haWwiLCJhdXRoX3RpbWUiOjE3NDAyMjgyODAsImV4cCI6MTc0MDIyODg0OCwiaWF0IjoxNzQwMjI4NTQ4LCJqdGkiOiJlZTlkNzM1Ni1mMTBkLTQ0NDMtODlhOC1mZTMxNzkwMDQ5ODEiLCJ1c2VybmFtZSI6IjMwZWNjOWVjLTIwOTEtNzBkOS1mNzZlLTFlZGI3ZGJmYjZlZSJ9.wnFOR_LMaQIYUttl847UQokgMhu--PqPJnBMVLkc4Lcg8o4w0Ds-lkpvFhc43Kc1bw4PbPdRPGOOrgI8CIaFATDYNSgs8rAr97u4DxRn-g2SPZa0dRSJDgAJ4MfGJ-JJpr_07q2c-kUuHNYuNvgBmyxB8BPrAxcjFebDEGnbWtSCLK0l8k3PezFVeutz0ZBOlc_TXJWpk2hP46sNf_dcF6NBb7B9FvzJJIw2WzSSquHcftpbyY3ipzVY-uDerFwWHjXaQtk9qolQ-Bxz13XwqPyyh_q2jUsARF_Cq73QE1zVDVm7OP0QyzeBoIy9nYoHE59jv_9mNKSbauvO5sM4XQ";
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        ResponseEntity<Map> response = restTemplate.exchange(
//                backendUrl + "/" + id, HttpMethod.GET, entity, Map.class
//        );
//
//        model.addAttribute("event", response.getBody());
//        return "event-details";
//    }
//    @GetMapping("/interested/{userId}")
//    public String getInterestedEvents(@PathVariable Long userId, Model model) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        String token = "Bearer " + "eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIzMGVjYzllYy0yMDkxLTcwZDktZjc2ZS0xZWRiN2RiZmI2ZWUiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6ImYyODExMTc3LTljNGYtNGQwOC04NmEwLTY5OTE4NTFkMjMxYiIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoicGhvbmUgb3BlbmlkIGVtYWlsIiwiYXV0aF90aW1lIjoxNzQwMTc1ODUyLCJleHAiOjE3NDAxNzYxNTIsImlhdCI6MTc0MDE3NTg1MiwianRpIjoiM2IwYTlmYjItZjgwZC00YmQxLThhMmYtOTVjN2YxYTc0NmYzIiwidXNlcm5hbWUiOiIzMGVjYzllYy0yMDkxLTcwZDktZjc2ZS0xZWRiN2RiZmI2ZWUifQ.rmnQfduJlNh5-_WkSt3ZO4pRaPyspJsyeHbrAfcSQPop3_PePPfdB2ohyR0MCsYTHGQWoR9CMJOG5Sh6xO6RTUFA2GmtecPEJKRueMWLqBvR-t7IgOJZrCXfpsiPHI0JHheK-uRKXOa3D2n1HfNL2YnkH0bCug3GTrYMK7Fm6Wp-OmQQ25_As1rK8PUE13GyI_PQIyaoY1a3aKCvtjvSG85HB1vOekgDVrA591h_X1-LsqAPbtMPTEpgiSuoVJvOkHyzjTzaIxSi48TZ-edrOVY6_jXGQVZDeQjManZIxz1bTMJ66679PrdmasJ9ojJc9fvXhNThCrtcqE7OAeOwOA";
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        ResponseEntity<List> response = restTemplate.exchange(
//                backendUrl + "/interested/" + userId, HttpMethod.GET, entity, List.class
//        );
//
//        model.addAttribute("interestedEvents", response.getBody());
//        return "interested-events";
//    }
}

