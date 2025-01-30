package mk.ukim.finki.eventguide.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LocalNotFoundException extends RuntimeException {

    public LocalNotFoundException(Long id) {
        super(String.format("Local with id: %d was not found", id));
    }

}
