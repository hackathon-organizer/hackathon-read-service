package com.hackathonorganizer.hackathonreadservice.hackathon.service;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.HackathonResponse;
import com.hackathonorganizer.hackathonreadservice.repository.HackathonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class HackathonServiceTests {

    @InjectMocks
    private HackathonService hackathonService;

    @Mock
    private HackathonRepository hackathonRepository;

    private Hackathon mockHackathon = Hackathon.builder()
            .id(5L)
            .name("Hackathon")
            .description("Desc")
            .organizerInfo("Org Info")
            .isActive(true)
            .eventStartDate(LocalDateTime.now())
            .eventEndDate(LocalDateTime.now().plusDays(2))
            .teams(new ArrayList<>())
            .build();

    @Test
    void shouldReturnHackathonById() {
        // given

        Long hackathonId = 5L;

        when(hackathonRepository.findById(anyLong())).thenReturn(Optional.of(
                mockHackathon));

        // when

        HackathonResponse hackathonResponse =
                hackathonService.getHackathonById(hackathonId);


        //then

        verify(hackathonRepository).findById(anyLong());

        assertThat(hackathonResponse.id()).isEqualTo(hackathonId);
        assertThat(hackathonResponse.name()).isEqualTo(mockHackathon.getName());
        assertThat(hackathonResponse.description()).isEqualTo(mockHackathon.getDescription());
    }

    @Test
    void shouldReturnAllHackathons() {
        // given

        Long hackathonId = 5L;
        Pageable pageableMock = mock(Pageable.class);

        PageImpl<Hackathon> hackathonPage =
                new PageImpl<>(List.of(mockHackathon));

        when(hackathonRepository.findAll(any(Pageable.class))).thenReturn(hackathonPage);

        // when

        List<HackathonResponse> hackathonResponseList =
                hackathonService.getAllHackathons(pageableMock);


        //then

        verify(hackathonRepository).findAll(pageableMock);

        assertThat(hackathonResponseList.size()).isEqualTo(1);
    }

}
