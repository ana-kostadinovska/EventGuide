package mk.ukim.finki.eventguidefrontend.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/locals")
public class LocalController {

    private final String backendUrl = "http://localhost:9090/api/locals";

    @GetMapping
    public String getLocals(@RequestParam(required = false) String type, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token = "Bearer " + "eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIzMGVjYzllYy0yMDkxLTcwZDktZjc2ZS0xZWRiN2RiZmI2ZWUiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6ImJjMmI5MjJmLWM4ZWYtNGExNC1iZjM0LTlhOGYzNmZhYzRlMSIsImV2ZW50X2lkIjoiNTA3NzViNjQtM2NmNS00ZmIyLWI4MjgtNmRkNjRkYzQ4MWE0IiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJwaG9uZSBvcGVuaWQgZW1haWwiLCJhdXRoX3RpbWUiOjE3NDAyMjgyODAsImV4cCI6MTc0MDIyODg0OCwiaWF0IjoxNzQwMjI4NTQ4LCJqdGkiOiJlZTlkNzM1Ni1mMTBkLTQ0NDMtODlhOC1mZTMxNzkwMDQ5ODEiLCJ1c2VybmFtZSI6IjMwZWNjOWVjLTIwOTEtNzBkOS1mNzZlLTFlZGI3ZGJmYjZlZSJ9.wnFOR_LMaQIYUttl847UQokgMhu--PqPJnBMVLkc4Lcg8o4w0Ds-lkpvFhc43Kc1bw4PbPdRPGOOrgI8CIaFATDYNSgs8rAr97u4DxRn-g2SPZa0dRSJDgAJ4MfGJ-JJpr_07q2c-kUuHNYuNvgBmyxB8BPrAxcjFebDEGnbWtSCLK0l8k3PezFVeutz0ZBOlc_TXJWpk2hP46sNf_dcF6NBb7B9FvzJJIw2WzSSquHcftpbyY3ipzVY-uDerFwWHjXaQtk9qolQ-Bxz13XwqPyyh_q2jUsARF_Cq73QE1zVDVm7OP0QyzeBoIy9nYoHE59jv_9mNKSbauvO5sM4XQ";        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        String url = backendUrl;
        if (type != null && !type.isEmpty()) {
            url += "/filter?type=" + type;
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        model.addAttribute("locals", response.getBody());
        return "locals";
    }

    @GetMapping("/{id}")
    public String getLocalDetails(@PathVariable Long id, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token = "Bearer " + "eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIzMGVjYzllYy0yMDkxLTcwZDktZjc2ZS0xZWRiN2RiZmI2ZWUiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6ImJjMmI5MjJmLWM4ZWYtNGExNC1iZjM0LTlhOGYzNmZhYzRlMSIsImV2ZW50X2lkIjoiNTA3NzViNjQtM2NmNS00ZmIyLWI4MjgtNmRkNjRkYzQ4MWE0IiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJwaG9uZSBvcGVuaWQgZW1haWwiLCJhdXRoX3RpbWUiOjE3NDAyMjgyODAsImV4cCI6MTc0MDIyODg0OCwiaWF0IjoxNzQwMjI4NTQ4LCJqdGkiOiJlZTlkNzM1Ni1mMTBkLTQ0NDMtODlhOC1mZTMxNzkwMDQ5ODEiLCJ1c2VybmFtZSI6IjMwZWNjOWVjLTIwOTEtNzBkOS1mNzZlLTFlZGI3ZGJmYjZlZSJ9.wnFOR_LMaQIYUttl847UQokgMhu--PqPJnBMVLkc4Lcg8o4w0Ds-lkpvFhc43Kc1bw4PbPdRPGOOrgI8CIaFATDYNSgs8rAr97u4DxRn-g2SPZa0dRSJDgAJ4MfGJ-JJpr_07q2c-kUuHNYuNvgBmyxB8BPrAxcjFebDEGnbWtSCLK0l8k3PezFVeutz0ZBOlc_TXJWpk2hP46sNf_dcF6NBb7B9FvzJJIw2WzSSquHcftpbyY3ipzVY-uDerFwWHjXaQtk9qolQ-Bxz13XwqPyyh_q2jUsARF_Cq73QE1zVDVm7OP0QyzeBoIy9nYoHE59jv_9mNKSbauvO5sM4XQ";        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                backendUrl + "/" + id, HttpMethod.GET, entity, Map.class
        );

        model.addAttribute("local", response.getBody());
        return "local-details";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token = "Bearer " + "eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIzMGVjYzllYy0yMDkxLTcwZDktZjc2ZS0xZWRiN2RiZmI2ZWUiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6ImJjMmI5MjJmLWM4ZWYtNGExNC1iZjM0LTlhOGYzNmZhYzRlMSIsImV2ZW50X2lkIjoiNTA3NzViNjQtM2NmNS00ZmIyLWI4MjgtNmRkNjRkYzQ4MWE0IiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJwaG9uZSBvcGVuaWQgZW1haWwiLCJhdXRoX3RpbWUiOjE3NDAyMjgyODAsImV4cCI6MTc0MDIyODg0OCwiaWF0IjoxNzQwMjI4NTQ4LCJqdGkiOiJlZTlkNzM1Ni1mMTBkLTQ0NDMtODlhOC1mZTMxNzkwMDQ5ODEiLCJ1c2VybmFtZSI6IjMwZWNjOWVjLTIwOTEtNzBkOS1mNzZlLTFlZGI3ZGJmYjZlZSJ9.wnFOR_LMaQIYUttl847UQokgMhu--PqPJnBMVLkc4Lcg8o4w0Ds-lkpvFhc43Kc1bw4PbPdRPGOOrgI8CIaFATDYNSgs8rAr97u4DxRn-g2SPZa0dRSJDgAJ4MfGJ-JJpr_07q2c-kUuHNYuNvgBmyxB8BPrAxcjFebDEGnbWtSCLK0l8k3PezFVeutz0ZBOlc_TXJWpk2hP46sNf_dcF6NBb7B9FvzJJIw2WzSSquHcftpbyY3ipzVY-uDerFwWHjXaQtk9qolQ-Bxz13XwqPyyh_q2jUsARF_Cq73QE1zVDVm7OP0QyzeBoIy9nYoHE59jv_9mNKSbauvO5sM4XQ";        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        try {
            // Fetch the local details
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> localResponse = restTemplate.exchange(
                    backendUrl + "/" + id, HttpMethod.GET, entity, Map.class
            );

            // Fetch all events
            ResponseEntity<List> eventsResponse = restTemplate.exchange(
                    "http://localhost:9090/api/events", HttpMethod.GET, entity, List.class
            );

            if (localResponse.getStatusCode().is2xxSuccessful() && eventsResponse.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("local", localResponse.getBody());
                model.addAttribute("allEvents", eventsResponse.getBody());
                return "edit-local";
            } else {
                model.addAttribute("error", "Failed to fetch data.");
                return "error"; // Create an error.html page
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "error"; // Create an error.html page
        }
    }

    @PutMapping("/edit/{id}")
    public String editLocal(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam String location,
                            @RequestParam String workingHours,
                            @RequestParam String contact_number,
                            @RequestParam String type,
                            Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token = "Bearer " + "eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIzMGVjYzllYy0yMDkxLTcwZDktZjc2ZS0xZWRiN2RiZmI2ZWUiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6ImJjMmI5MjJmLWM4ZWYtNGExNC1iZjM0LTlhOGYzNmZhYzRlMSIsImV2ZW50X2lkIjoiNTA3NzViNjQtM2NmNS00ZmIyLWI4MjgtNmRkNjRkYzQ4MWE0IiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJwaG9uZSBvcGVuaWQgZW1haWwiLCJhdXRoX3RpbWUiOjE3NDAyMjgyODAsImV4cCI6MTc0MDIyODg0OCwiaWF0IjoxNzQwMjI4NTQ4LCJqdGkiOiJlZTlkNzM1Ni1mMTBkLTQ0NDMtODlhOC1mZTMxNzkwMDQ5ODEiLCJ1c2VybmFtZSI6IjMwZWNjOWVjLTIwOTEtNzBkOS1mNzZlLTFlZGI3ZGJmYjZlZSJ9.wnFOR_LMaQIYUttl847UQokgMhu--PqPJnBMVLkc4Lcg8o4w0Ds-lkpvFhc43Kc1bw4PbPdRPGOOrgI8CIaFATDYNSgs8rAr97u4DxRn-g2SPZa0dRSJDgAJ4MfGJ-JJpr_07q2c-kUuHNYuNvgBmyxB8BPrAxcjFebDEGnbWtSCLK0l8k3PezFVeutz0ZBOlc_TXJWpk2hP46sNf_dcF6NBb7B9FvzJJIw2WzSSquHcftpbyY3ipzVY-uDerFwWHjXaQtk9qolQ-Bxz13XwqPyyh_q2jUsARF_Cq73QE1zVDVm7OP0QyzeBoIy9nYoHE59jv_9mNKSbauvO5sM4XQ";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        try {
            String url = backendUrl + "/edit/" + id;

            String requestBody = String.format(
                    "name=%s&location=%s&workingHours=%s&contact_number=%s&type=%s",
                    name, location, workingHours, contact_number, type
            );

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.PUT, entity, String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return "redirect:/locals";
            } else {
                model.addAttribute("error", "Failed to update local.");
                return "error"; // Create an error.html page
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "error"; // Create an error.html page
        }
    }
}
