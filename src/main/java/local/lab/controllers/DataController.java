package local.lab.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {

    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> getUserData(){
        return ResponseEntity.ok("Hi User!");
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getAdminData(){
        return ResponseEntity.ok("Hi Admin!");
    }

    @GetMapping(value = "/hello")
    public ResponseEntity<String> getHelloData(){
        return ResponseEntity.ok("Hello world!");
    }
}
