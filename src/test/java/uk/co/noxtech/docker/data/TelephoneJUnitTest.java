package uk.co.noxtech.docker.data;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TelephoneJUnitTest {

    private Telephone testSubject;

    @Test
    public void shouldParseJsonIntoTelephone() throws Exception {
        // Given
        Telephone telephone = new Telephone(Telephone.createPhoneNumber("123456789", "GB", "US"));
        String json = telephone.toJsonString();

        // When
        Telephone result = Telephone.parseJsonAsTelephone(json);

        // Then
        assertThat(result, is(telephone));
    }
}
