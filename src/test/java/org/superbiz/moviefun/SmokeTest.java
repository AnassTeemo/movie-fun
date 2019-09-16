package org.superbiz.moviefun;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class SmokeTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void smokeTest() {

        String homePage = testRestTemplate.getForObject("/", String.class);

        assertThat(homePage, containsString("Please select one of the following links:"));

        String setupPage = testRestTemplate.getForObject("/setup", String.class);

        assertThat(setupPage, containsString("Wedding Crashers"));
        assertThat(setupPage, containsString("Starsky & Hutch"));
        assertThat(setupPage, containsString("Shanghai Knights"));
        assertThat(setupPage, containsString("I-Spy"));
        assertThat(setupPage, containsString("The Royal Tenenbaums"));

        String movieFunPage = testRestTemplate.getForObject("/moviefun", String.class);

        assertThat(movieFunPage, containsString("Wedding Crashers"));
        assertThat(movieFunPage, containsString("David Dobkin"));
    }

}
