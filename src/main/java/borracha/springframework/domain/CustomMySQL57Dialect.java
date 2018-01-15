package borracha.springframework.domain;

import org.hibernate.dialect.MySQL57Dialect;

public class CustomMySQL57Dialect extends MySQL57Dialect {
  @Override
  public boolean dropConstraints() {
    return false;
  }
}