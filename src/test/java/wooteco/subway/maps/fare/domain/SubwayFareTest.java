package wooteco.subway.maps.fare.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static wooteco.subway.maps.fare.acceptance.FareAcceptanceTest.TEST_AGE;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.subway.common.TestObjectUtils;
import wooteco.subway.common.acceptance.AcceptanceTest;
import wooteco.subway.maps.fare.SubwayFare;
import wooteco.subway.maps.line.application.LineService;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.line.dto.LineResponse;
import wooteco.subway.maps.map.domain.LineStationEdge;
import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.station.domain.Station;

public class SubwayFareTest extends AcceptanceTest {

    @Autowired
    private LineService lineService;

    private Map<Long, Station> stations;
    private List<Line> lines;

    private SubwayPath subwayPath;

    @BeforeEach
    public void setUp() {
        stations = new HashMap<>();
        stations.put(1L, TestObjectUtils.createStation(1L, "교대역"));
        stations.put(2L, TestObjectUtils.createStation(2L, "강남역"));
        stations.put(3L, TestObjectUtils.createStation(3L, "양재역"));
        stations.put(4L, TestObjectUtils.createStation(4L, "남부터미널역"));

        Line line1 = TestObjectUtils.createLine(1L, "2호선", "GREEN", 0);
        line1.addLineStation(new LineStation(1L, null, 0, 0));
        LineStation lineStation2 = new LineStation(2L, 1L, 2, 2);
        line1.addLineStation(new LineStation(2L, 1L, 2, 2));

        Line line2 = TestObjectUtils.createLine(2L, "신분당선", "RED", 500);
        line2.addLineStation(new LineStation(2L, null, 0, 0));
        line2.addLineStation(new LineStation(3L, 2L, 2, 1));

        Line line3 = TestObjectUtils.createLine(3L, "3호선", "ORANGE", 900);
        line3.addLineStation(new LineStation(1L, null, 0, 0));
        LineStation lineStation6 = new LineStation(4L, 1L, 1, 2);
        LineStation lineStation7 = new LineStation(3L, 4L, 2, 2);
        line3.addLineStation(lineStation6);
        line3.addLineStation(lineStation7);

        lines = Lists.newArrayList(line1, line2, line3);

        List<LineStationEdge> lineStations = Lists.newArrayList(
            new LineStationEdge(lineStation6, line3.getId()),
            new LineStationEdge(lineStation7, line3.getId())
        );
        subwayPath = new SubwayPath(lineStations);
    }

    // TODO: 2020/08/06 다른 경우의 수도 할 것

    @DisplayName("0Km 이하일 경우 지하철 요금")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void down0SubwayFareTest(int inputDistance) {
        List<LineResponse> lineResponses = lineService
            .findEdgeLineResponses(subwayPath.extractLineEdgeIds());

        SubwayFare subwayFare = new SubwayFare(inputDistance, lineResponses, TEST_AGE);

        assertThatThrownBy(subwayFare::calculateSubwayFare)
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("기본운임(10Km 이내)일 경우 지하철 요금")
    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    void defaultSubwayFareTest(int inputDistance) {
        List<LineResponse> lineResponses = lineService
            .findEdgeLineResponses(subwayPath.extractLineEdgeIds());

        SubwayFare subwayFare = new SubwayFare(inputDistance, lineResponses, TEST_AGE);

        assertThat(subwayFare.calculateSubwayFare()).isEqualTo(2150L);
    }

    @DisplayName("이용 거리초과 시 추가운임(10km초과 ∼ 50km까지)일 경우 지하철 요금")
    @ParameterizedTest
    @CsvSource({"11,2450", "50,3150"})
    void between10to50SubwayFareTest(int inputDistance, Long expectedFare) {
        List<LineResponse> lineResponses = lineService
            .findEdgeLineResponses(subwayPath.extractLineEdgeIds());

        SubwayFare subwayFare = new SubwayFare(inputDistance, lineResponses, TEST_AGE);

        assertThat(subwayFare.calculateSubwayFare()).isEqualTo(expectedFare);
    }

    @DisplayName("이용 거리초과 시 추가운임(50km초과)일 경우 지하철 요금")
    @ParameterizedTest
    @CsvSource({"53,2850", "100,3450"})
    void over50SubwayFareTest(int inputDistance, Long expectedFare) {
        List<LineResponse> lineResponses = lineService
            .findEdgeLineResponses(subwayPath.extractLineEdgeIds());

        SubwayFare subwayFare = new SubwayFare(inputDistance, lineResponses, TEST_AGE);

        assertThat(subwayFare.calculateSubwayFare()).isEqualTo(expectedFare);
    }
}
