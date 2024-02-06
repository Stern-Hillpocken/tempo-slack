package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.ServerRepository;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.model.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServerService {
    @Autowired
    ServerRepository serverRepository;

    public void add(Server server) {
        serverRepository.save(server);
    }

    public List<Server> findAll() {
        return serverRepository.findAll();
    }

    public Optional<Server> findById(Long id) {
        return serverRepository.findById(id);
    }

    public void update(Server server) {
        serverRepository.save(server);
    }

    public void delete(Long id) {
        serverRepository.deleteById(id);
    }

}
