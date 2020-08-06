package wooteco.subway.maps.map.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wooteco.subway.maps.fare.acceptance.FareAcceptanceTest.TEST_AGE;
import static wooteco.subway.members.member.acceptance.MemberAcceptanceTest.EMAIL;
import static wooteco.subway.members.member.acceptance.MemberAcceptanceTest.PASSWORD;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.members.member.domain.LoginMember;

public class MapControllerTest {
    @Test
    void findPath() {
        MapService mapService = mock(MapService.class);
        MapController controller = new MapController(mapService);
        when(mapService.findPath(anyInt(), anyLong(), anyLong(), any())).thenReturn(new PathResponse());

        ResponseEntity<PathResponse> entity = controller.findPath(new LoginMember(1L, EMAIL, PASSWORD, TEST_AGE), 1L, 2L, PathType.DISTANCE);

        assertThat(entity.getBody()).isNotNull();
    }

    @Test
    void findMap() {
        MapService mapService = mock(MapService.class);
        MapController controller = new MapController(mapService);

        ResponseEntity entity = controller.findMap();

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(mapService).findMap();
    }
}
