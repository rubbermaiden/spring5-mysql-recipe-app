package borracha.springframework.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import borracha.springframework.spring.TestDbConfig;
import borracha.springframework.spring.TestIntegrationConfig;
import borracha.springframework.commands.UserCommand;
import borracha.springframework.domain.Privilege;
import borracha.springframework.domain.Role;
import borracha.springframework.domain.User;
import borracha.springframework.domain.VerificationToken;
import borracha.springframework.error.UserAlreadyExistException;
import borracha.springframework.repositories.RoleRepository;
import borracha.springframework.repositories.UserRepository;
import borracha.springframework.repositories.VerificationTokenRepository;

import borracha.springframework.service.UserService;
import borracha.springframework.spring.ServiceConfig;
import borracha.springframework.spring.TestDbConfig;
import borracha.springframework.spring.TestIntegrationConfig;
import borracha.springframework.validation.EmailExistsException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestDbConfig.class, ServiceConfig.class, TestIntegrationConfig.class })
public class UserServiceIntegrationTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private VerificationTokenRepository tokenRepository;

  //

  @Test
  public void givenNewUser_whenRegistered_thenCorrect() throws EmailExistsException {
    final String userEmail = UUID.randomUUID().toString();
    final UserCommand userDto = createUserDto(userEmail);

    final User user = userService.registerNewUserAccount(userDto);

    assertNotNull(user);
    assertNotNull(user.getEmail());
    assertEquals(userEmail, user.getEmail());
    assertNotNull(user.getId());
  }

  @Test
  public void givenDetachedUser_whenAccessingEntityAssociations_thenCorrect() throws EmailExistsException {
    final User user = registerUser();
    assertNotNull(user.getRoles());
    user.getRoles().stream().filter(r -> r != null).forEach(Role::getId);
    user.getRoles().stream().filter(r -> r != null).forEach(Role::getName);
    user.getRoles().stream().filter(r -> r != null).forEach(r -> r.getPrivileges().stream().filter(p -> p != null).forEach(Privilege::getId));
    user.getRoles().stream().filter(r -> r != null).forEach(r -> r.getPrivileges().stream().filter(p -> p != null).forEach(Privilege::getName));
    user.getRoles().stream().filter(r -> r != null).forEach(r -> r.getPrivileges().stream().map(
        Privilege::getRoles).forEach(
        Assert::assertNotNull));
  }

  @Test
  public void givenDetachedUser_whenServiceLoadById_thenCorrect() throws EmailExistsException {
    final User user = registerUser();
    final User user2 = userService.getUserByID(user.getId());
    assertEquals(user, user2);
  }

  @Test
  public void givenDetachedUser_whenServiceLoadByEmail_thenCorrect() throws EmailExistsException {
    final User user = registerUser();
    final User user2 = userService.findUserByEmail(user.getEmail());
    assertEquals(user, user2);
  }

  @Test(expected = UserAlreadyExistException.class)
  public void givenUserRegistered_whenDuplicatedRegister_thenCorrect() {
    final String email = UUID.randomUUID().toString();
    final UserCommand userDto = createUserDto(email);

    userService.registerNewUserAccount(userDto);
    userService.registerNewUserAccount(userDto);
  }

  @Transactional
  public void givenUserRegistered_whenDtoRoleAdmin_thenUserNotAdmin() {
    assertNotNull(roleRepository);
    final UserCommand userDto = new UserCommand();
    userDto.setEmail(UUID.randomUUID().toString());
    userDto.setPassword("SecretPassword");
    userDto.setMatchingPassword("SecretPassword");
    userDto.setFirstName("First");
    userDto.setLastName("Last");
    assertNotNull(roleRepository.findByName("ROLE_ADMIN"));
    final Long adminRoleId = roleRepository.findByName("ROLE_ADMIN").getId();
    assertNotNull(adminRoleId);
    userDto.setRole(adminRoleId.intValue());
    final User user = userService.registerNewUserAccount(userDto);
    assertFalse(user.getRoles().stream().map(Role::getId).anyMatch(ur -> ur.equals(adminRoleId)));
  }

  @Test
  public void givenUserRegistered_whenCreateToken_thenCorrect() {
    final User user = registerUser();
    final String token = UUID.randomUUID().toString();
    userService.createVerificationTokenForUser(user, token);
  }

  @Test
  public void givenUserRegistered_whenCreateTokenCreateDuplicate_thenCorrect() {
    final User user = registerUser();
    final String token = UUID.randomUUID().toString();
    userService.createVerificationTokenForUser(user, token);
    userService.createVerificationTokenForUser(user, token);
  }

  @Test
  public void givenUserAndToken_whenLoadToken_thenCorrect() {
    final User user = registerUser();
    final String token = UUID.randomUUID().toString();
    userService.createVerificationTokenForUser(user, token);
    final VerificationToken verificationToken = userService.getVerificationToken(token);
    assertNotNull(verificationToken);
    assertNotNull(verificationToken.getId());
    assertNotNull(verificationToken.getUser());
    assertEquals(user, verificationToken.getUser());
    assertEquals(user.getId(), verificationToken.getUser().getId());
    assertEquals(token, verificationToken.getToken());
    assertTrue(verificationToken.getExpiryDate().toInstant().isAfter(Instant.now()));
  }

  @Test
  public void givenUserAndToken_whenRemovingUser_thenCorrect() {
    final User user = registerUser();
    final String token = UUID.randomUUID().toString();
    userService.createVerificationTokenForUser(user, token);
    userService.deleteUser(user);
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void givenUserAndToken_whenRemovingUserByDao_thenFKViolation() {
    final User user = registerUser();
    final String token = UUID.randomUUID().toString();
    userService.createVerificationTokenForUser(user, token);
    final long userId = user.getId();
    userService.getVerificationToken(token).getId();
    userRepository.deleteById(userId);
  }

  @Test
  public void givenUserAndToken_whenRemovingTokenThenUser_thenCorrect() {
    final User user = registerUser();
    final String token = UUID.randomUUID().toString();
    userService.createVerificationTokenForUser(user, token);
    final long userId = user.getId();
    final long tokenId = userService.getVerificationToken(token).getId();
    tokenRepository.deleteById(tokenId);
    userRepository.deleteById(userId);
  }

  @Test
  public void givenUserAndToken_whenRemovingToken_thenCorrect() {
    final User user = registerUser();
    final String token = UUID.randomUUID().toString();
    userService.createVerificationTokenForUser(user, token);
    final long tokenId = userService.getVerificationToken(token).getId();
    tokenRepository.deleteById(tokenId);
  }

  @Test
  public void givenUserAndToken_whenNewTokenRequest_thenCorrect() {
    final User user = registerUser();
    final String token = UUID.randomUUID().toString();
    userService.createVerificationTokenForUser(user, token);
    final VerificationToken origToken = userService.getVerificationToken(token);
    final VerificationToken newToken = userService.generateNewVerificationToken(token);
    assertNotEquals(newToken.getToken(), origToken.getToken());
    assertNotEquals(newToken.getExpiryDate(), origToken.getExpiryDate());
    assertNotEquals(newToken, origToken);
  }

  @Test
  public void givenTokenValidation_whenValid_thenUserEnabled_andTokenRemoved() {
    User user = registerUser();
    final String token = UUID.randomUUID().toString();
    userService.createVerificationTokenForUser(user, token);
    final long userId = user.getId();
    final String token_status = userService.validateVerificationToken(token);
    assertEquals(token_status, UserService.TOKEN_VALID);
    user = userService.getUserByID(userId);
    assertTrue(user.isEnabled());
  }

  @Test
  public void givenTokenValidation_whenInvalid_thenCorrect() {
    final User user = registerUser();
    final String token = UUID.randomUUID().toString();
    final String invalid_token = "INVALID_" + UUID.randomUUID().toString();
    userService.createVerificationTokenForUser(user, token);
    userService.getVerificationToken(token).getId();
    final String token_status = userService.validateVerificationToken(invalid_token);
    token_status.equals(UserService.TOKEN_INVALID);
  }

  @Test
  public void givenTokenValidation_whenExpired_thenCorrect() {
    final User user = registerUser();
    final String token = UUID.randomUUID().toString();
    userService.createVerificationTokenForUser(user, token);
    user.getId();
    final VerificationToken verificationToken = userService.getVerificationToken(token);
    verificationToken.setExpiryDate(
        Date.from(verificationToken.getExpiryDate().toInstant().minus(2, ChronoUnit.DAYS)));
    tokenRepository.saveAndFlush(verificationToken);
    final String token_status = userService.validateVerificationToken(token);
    assertNotNull(token_status);
    token_status.equals(UserService.TOKEN_EXPIRED);
  }

  //

  private UserCommand createUserDto(final String email) {
    final UserCommand userDto = new UserCommand();
    userDto.setEmail(email);
    userDto.setPassword("SecretPassword");
    userDto.setMatchingPassword("SecretPassword");
    userDto.setFirstName("First");
    userDto.setLastName("Last");
    userDto.setRole(0);
    return userDto;
  }

  private User registerUser() {
    final String email = UUID.randomUUID().toString();
    final UserCommand userDto = createUserDto(email);
    final User user = userService.registerNewUserAccount(userDto);
    assertNotNull(user);
    assertNotNull(user.getId());
    assertEquals(email, user.getEmail());
    return user;
  }

}
