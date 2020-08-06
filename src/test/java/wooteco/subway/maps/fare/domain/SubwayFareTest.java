package wooteco.subway.maps.fare.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import wooteco.subway.maps.fare.SubwayFare;

public class SubwayFareTest {

    @DisplayName("기본운임(10Km 이내)일 경우 지하철 요금")
    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    void defaultSubwayFareTest(int inputDistance) {
        SubwayFare subwayFare = new SubwayFare(inputDistance);

        assertThat(subwayFare.calculateSubwayFareByDistance()).isEqualTo(1250L);
    }

    @DisplayName("이용 거리초과 시 추가운임(10km초과 ∼ 50km까지)일 경우 지하철 요금")
    @ParameterizedTest
    @CsvSource({"11,1450", "50,2250"})
    void overSubwayFareTest(int inputDistance, Long expectedFare) {
        SubwayFare subwayFare = new SubwayFare(inputDistance);

        assertThat(subwayFare.calculateSubwayFareByDistance()).isEqualTo(expectedFare);
    }


}
