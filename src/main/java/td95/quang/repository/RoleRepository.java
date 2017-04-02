package td95.quang.repository;

import org.springframework.data.repository.CrudRepository;

import td95.quang.domain.Role;

public interface RoleRepository  extends CrudRepository<Role, Integer>{
Role findByName(String name);
}
