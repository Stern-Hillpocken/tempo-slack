package firstgroup.temposlack.controller;

import firstgroup.temposlack.model.Server;
import firstgroup.temposlack.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("servers")
public class ServerController {
    @Autowired
    ServerService serverService;

    @GetMapping
    public List<Server> findAll(){
        return serverService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        Optional<Server> s = serverService.findById(id);
        if(s.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(s.get());
        }
    }
    @PostMapping
    public ResponseEntity<?> add (@RequestBody  Server server){
        serverService.add(server);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Server server) {
        Optional<Server> s = serverService.findById(id);
        if (s.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            if (!id.equals(server.getId())) {
                return ResponseEntity.badRequest().build();
            } else {
                serverService.update(server);
                return ResponseEntity.ok(s.get());
            }
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Server> c = serverService.findById(id);
        if (c.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            serverService.delete(id);
            return ResponseEntity.ok().build();
        }
    }
}
