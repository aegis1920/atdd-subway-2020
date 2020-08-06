package wooteco.subway.maps.map.dto;

import java.util.List;
import wooteco.subway.maps.station.dto.StationResponse;

public class PathResponse {
    private List<StationResponse> stations;
    private int duration;
    private int distance;
    private Long fare;

    public PathResponse() {
    }

    public PathResponse(List<StationResponse> stations, int duration, int distance, Long fare) {
        this.stations = stations;
        this.duration = duration;
        this.distance = distance;
        this.fare = fare;
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDuration() {
        return duration;
    }

    public int getDistance() {
        return distance;
    }

    public Long getFare() {
        return fare;
    }
}
