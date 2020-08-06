package wooteco.subway.maps.fare.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import wooteco.subway.maps.fare.SubwayFare;

public class SubwayFareTest {

    @DisplayName("기본운임(10Km 이내)일 경우 지하철 요금 ")
    @ParameterizedTest
    @ValueSource(longs = {1L, 10L})
    void defaultSubwayFareTest(Long inputDistance) {
        SubwayFare subwayFare = new SubwayFare(inputDistance);

        assertThat(subwayFare.calculateSubwayFareByDistance()).isEqualTo(1250L);
    }
}
