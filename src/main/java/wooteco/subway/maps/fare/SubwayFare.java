package wooteco.subway.maps.fare;

public class SubwayFare {

    public static final Long DEFAULT_FARE = 1250L;
    private final Long distance;

    public SubwayFare(Long distance) {
        this.distance = distance;
    }


    public Long calculateSubwayFareByDistance() {
        if (distance <= 10) {
            return DEFAULT_FARE;
        }
        throw new IllegalArgumentException(distance + " 거리는 요금 체계에 해당하지 않는 거리입니다!");
    }
}
