package kr.ac.jejunu;

import org.junit.jupiter.api.Test;

public class LombokTests {
    @Test
    public void equals() {
        String name = "test";
        String password = "1234";
        User user1 = User.builder().name(name).password(password).build();
        User user2 = User.builder().name(name).password(password).build();

    }
}
