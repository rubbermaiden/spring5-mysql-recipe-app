package guru.springframework.domain;

import org.hibernate.dialect.MySQL55Dialect;

public class CustomMySQL55Dialect extends MySQL55Dialect {
  @Override
  public boolean dropConstraints() {
    return false;
  }
}