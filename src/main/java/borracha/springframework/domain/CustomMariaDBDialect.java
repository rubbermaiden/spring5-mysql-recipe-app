package borracha.springframework.domain;

import org.hibernate.dialect.MariaDBDialect;

public class CustomMariaDBDialect extends MariaDBDialect {
  @Override
  public boolean dropConstraints() {
    return false;
  }
}