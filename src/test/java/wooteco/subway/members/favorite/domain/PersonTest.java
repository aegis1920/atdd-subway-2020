package wooteco.subway.members.favorite.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.members.member.domain.Person;

public class PersonTest {

    @DisplayName("나이가 어린이인 경우 요금 계산 테스트")
    @Test
    void ageIsChild() {
        int age = 7;
        int fare = 1000;
        int expectedFare = 325;
        assertThat(Person.calculate(fare, age)).isEqualTo(expectedFare);
    }

    @DisplayName("나이가 청소년인 경우 요금 계산 테스트")
    @Test
    void ageIsYouth() {
        int age = 14;
        int fare = 1000;
        int expectedFare = 520;
        assertThat(Person.calculate(fare, age)).isEqualTo(expectedFare);
    }
}
