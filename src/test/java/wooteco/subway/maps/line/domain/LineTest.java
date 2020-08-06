package wooteco.subway.maps.line.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.common.TestObjectUtils;

public class LineTest {

    @DisplayName("추가 요금을 설정한 Line 생성")
    @Test
    void constructorTest() {
        Line line = TestObjectUtils.createLine(1L, "신분당선", "RED", 900);

        assertThat(line.getExtraFare()).isEqualTo(900);
    }
}
