package guru.springframework.repositories;

import guru.springframework.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);

  User findUserById(Long id);

  @Override
  void delete(User user);

}