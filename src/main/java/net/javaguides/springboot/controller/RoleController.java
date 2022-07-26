package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class RoleController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RoleRepository roleRepository;

//    get all roles
//    GET http://localhost:9092/api/v2/roles
    @GetMapping("/roles")
    public List<Role> getAllUsers() {
        return roleRepository.findAll();
    }

//    get roles by name
//    GET http://localhost:9092/api/v2/roles/Admin
    @GetMapping("/roles/{title}")
    public ResponseEntity<Role> getRoleByTitle(@PathVariable(value = "title") String roleTitle) {
        Role role = roleRepository.findByTitle(roleTitle);
        return ResponseEntity.ok().body(role);
    }

//    save role
//    POST http://localhost:9092/api/v2/roles
    @PostMapping("/roles")
    public Role createRole(@RequestBody Role role) {
        logger.info("Create Roles");
        return roleRepository.save(role);
    }

//    update role
    @PutMapping("/roles/{title}")
    public ResponseEntity<Role> updateRoles(@PathVariable(value = "title") String roleTitle, @Validated @RequestBody Role roleDetails) {
        Role role = roleRepository.findByTitle(roleTitle);
        role.setTitle(roleDetails.getTitle());
        role.setDescription(roleDetails.getDescription());
        final Role updatedRole = roleRepository.save(role);
        return ResponseEntity.ok(updatedRole);
    }

//    delete role
    @DeleteMapping("/roles/{title}")
    public Map<String, Boolean> deleteRole(@PathVariable(value = "title") String roleTitle) {
       Role role = roleRepository.findByTitle(roleTitle);
       roleRepository.delete(role);
       Map<String, Boolean> response = new HashMap<>();
       response.put("deleted", Boolean.TRUE);
       return response;
    }
}
