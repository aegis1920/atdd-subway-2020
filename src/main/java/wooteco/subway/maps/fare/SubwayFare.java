package wooteco.subway.maps.fare;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import wooteco.subway.maps.line.dto.LineResponse;
import wooteco.subway.members.member.domain.Person;

public class SubwayFare {

    public static final Long DEFAULT_FARE = 1250L;

    private final int distance;
    private final List<LineResponse> lineResponses;
    private final int loginMemberAge;

    public SubwayFare(int distance, List<LineResponse> lineResponses,
        int loginMemberAge) {
        this.distance = distance;
        this.lineResponses = lineResponses;
        this.loginMemberAge = loginMemberAge;
    }

    public long calculateSubwayFare() {
        final long totalFare = Math.round(getFare());
        return Person.calculate(totalFare, loginMemberAge);
    }

    private double getFare() {
        int fareByDistance = 0;
        int fareByLine = getMaxExtraFare();

        if (distance <= 0) {
            throw new IllegalArgumentException(distance + " 거리는 요금 체계에 해당하지 않는 거리입니다!");
        }
        if (distance <= 10) {
            return DEFAULT_FARE + fareByDistance + fareByLine;
        }
        if (distance <= 50) {
            fareByDistance = (int) ((Math.ceil((distance - 1) / 5) + 1) * 100);
            return DEFAULT_FARE + fareByDistance + fareByLine;
        }
        fareByDistance = (int) ((Math.ceil((distance - 1) / 8) + 1) * 100);
        return DEFAULT_FARE + fareByDistance + fareByLine;
    }

    private int getMaxExtraFare() {
        List<Integer> extraFares = lineResponses.stream()
            .map(LineResponse::getExtraFare)
            .collect(Collectors.toList());

        return extraFares.stream()
            .max(Comparator.comparingInt(o -> o))
            .orElseThrow(IllegalAccessError::new);
    }
}
