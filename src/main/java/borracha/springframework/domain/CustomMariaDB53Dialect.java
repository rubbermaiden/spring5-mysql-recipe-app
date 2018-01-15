package borracha.springframework.domain;

import org.hibernate.dialect.MariaDB53Dialect;

public class CustomMariaDB53Dialect extends MariaDB53Dialect {
  @Override
  public boolean dropConstraints() {
    return false;
  }
}