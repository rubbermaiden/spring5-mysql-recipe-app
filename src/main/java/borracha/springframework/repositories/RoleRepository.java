package borracha.springframework.repositories;


import borracha.springframework.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByName(String name);

  @Override
  void delete(Role role);

}
