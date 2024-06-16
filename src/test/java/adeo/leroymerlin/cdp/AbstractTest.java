package adeo.leroymerlin.cdp;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public abstract class AbstractTest {
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

}
