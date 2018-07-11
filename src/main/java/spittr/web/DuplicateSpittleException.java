package spittr.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.IM_USED, reason="Duplicated Spittle")
public class DuplicateSpittleException extends RuntimeException {
    
}
