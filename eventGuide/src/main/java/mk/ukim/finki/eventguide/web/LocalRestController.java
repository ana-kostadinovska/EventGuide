package mk.ukim.finki.eventguide.web;

import mk.ukim.finki.eventguide.service.LocalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locals")
public class LocalRestController {

    private final LocalService localService;

    public LocalRestController(LocalService localService) {
        this.localService = localService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.localService.deleteById(id);
        if(this.localService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

}
