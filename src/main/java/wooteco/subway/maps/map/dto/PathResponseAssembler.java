package wooteco.subway.maps.map.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import wooteco.subway.maps.fare.SubwayFare;
import wooteco.subway.maps.line.dto.LineResponse;
import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.dto.StationResponse;

public class PathResponseAssembler {
    public static PathResponse assemble(SubwayPath subwayPath, Map<Long, Station> stations, List<LineResponse> lineResponses) {
        List<StationResponse> stationResponses = subwayPath.extractStationId().stream()
                .map(it -> StationResponse.of(stations.get(it)))
                .collect(Collectors.toList());

        int distance = subwayPath.calculateDistance();

        SubwayFare subwayFare = new SubwayFare(distance, lineResponses);

        return new PathResponse(stationResponses, subwayPath.calculateDuration(), distance, subwayFare.calculateSubwayFare());
    }
}
