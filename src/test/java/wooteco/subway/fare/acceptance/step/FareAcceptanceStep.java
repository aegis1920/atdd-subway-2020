package wooteco.subway.fare.acceptance.step;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import wooteco.subway.maps.map.dto.PathResponse;

public class FareAcceptanceStep {

    public static void 적절한_요금을_응답(ExtractableResponse<Response> response, Long expectedFare) {
        PathResponse pathResponse = response.as(PathResponse.class);
        Long fare = pathResponse.getFare();

        assertThat(fare).isEqualTo(expectedFare);
    }

}
