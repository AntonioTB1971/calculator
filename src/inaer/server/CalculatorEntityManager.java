package inaer.server;

import javax.persistence.PersistenceUnit;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@PersistenceUnit
public final class CalculatorEntityManager {
  private static final EntityManagerFactory emfInstance = Persistence
      .createEntityManagerFactory("transactions-optional");

  private CalculatorEntityManager() {
  }

  public static EntityManagerFactory get() {
    return emfInstance;
  }
}
