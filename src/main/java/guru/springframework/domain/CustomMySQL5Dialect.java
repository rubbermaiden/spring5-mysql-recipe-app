package guru.springframework.domain;

import org.hibernate.dialect.MySQL5Dialect;

public class CustomMySQL5Dialect extends MySQL5Dialect {
  @Override
  public boolean dropConstraints() {
    return false;
  }
}