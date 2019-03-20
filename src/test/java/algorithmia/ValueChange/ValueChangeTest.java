package algorithmia.ValueChange;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ValueChangeTest {

    private final ValueChange underTest;

    public ValueChangeTest() {
        underTest = new ValueChange();
    }

    @Test
    public void testValueChange() throws Exception {
        assertThat(underTest.apply("Bob"), equalTo("Hello Bob"));
    }
}
