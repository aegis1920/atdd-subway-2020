package wooteco.subway.maps.fare;

public class SubwayFare {

    public static final Long DEFAULT_FARE = 1250L;

    private final int distance;

    public SubwayFare(int distance) {
        this.distance = distance;
    }


    public Long calculateSubwayFareByDistance() {
        int plusFare = 0;
        if (distance <= 10) {
            return DEFAULT_FARE + plusFare;
        }
        if (distance <= 50) {
            plusFare = (distance / 5) * 100;
            return DEFAULT_FARE + plusFare;
        }
        plusFare = (distance / 8) * 100;
        return DEFAULT_FARE + plusFare;
    }
}
