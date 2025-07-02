package cm.amcloud.platform.invitation.service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import cm.amcloud.platform.invitation.model.Invitation;
import cm.amcloud.platform.invitation.repository.InvitationRepository;

class InvitationServiceUnitTest {

    @Mock
    private InvitationRepository invitationRepository;

    @InjectMocks
    private InvitationService invitationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createInvitation_shouldSaveAndReturnInvitation() {
        String email = "test@example.com";
        Invitation savedInvitation = new Invitation();
        savedInvitation.setId(1L);
        savedInvitation.setEmail(email);
        savedInvitation.setToken("token");
        savedInvitation.setExpirationDate(LocalDateTime.now().plusDays(7));
        savedInvitation.setAccepted(false);

        when(invitationRepository.save(any(Invitation.class))).thenReturn(savedInvitation);

        Invitation result = invitationService.createInvitation(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertNotNull(result.getToken());
        assertFalse(result.isAccepted());
        assertTrue(result.getExpirationDate().isAfter(LocalDateTime.now()));
        verify(invitationRepository, times(1)).save(any(Invitation.class));
    }

    @Test
    void validateInvitation_shouldAcceptValidToken() {
        String token = "valid-token";
        Invitation invitation = new Invitation();
        invitation.setToken(token);
        invitation.setAccepted(false);
        invitation.setExpirationDate(LocalDateTime.now().plusDays(1));

        when(invitationRepository.findByToken(token)).thenReturn(Optional.of(invitation));
        when(invitationRepository.save(any(Invitation.class))).thenReturn(invitation);

        boolean result = invitationService.validateInvitation(token);

        assertTrue(result);
        assertTrue(invitation.isAccepted());
        verify(invitationRepository, times(1)).findByToken(token);
        verify(invitationRepository, times(1)).save(invitation);
    }

    @Test
    void validateInvitation_shouldReturnFalseForExpiredToken() {
        String token = "expired-token";
        Invitation invitation = new Invitation();
        invitation.setToken(token);
        invitation.setAccepted(false);
        invitation.setExpirationDate(LocalDateTime.now().minusDays(1));

        when(invitationRepository.findByToken(token)).thenReturn(Optional.of(invitation));

        boolean result = invitationService.validateInvitation(token);

        assertFalse(result);
        assertFalse(invitation.isAccepted());
        verify(invitationRepository, times(1)).findByToken(token);
        verify(invitationRepository, never()).save(any());
    }

    @Test
    void validateInvitation_shouldReturnFalseForAlreadyAccepted() {
        String token = "accepted-token";
        Invitation invitation = new Invitation();
        invitation.setToken(token);
        invitation.setAccepted(true);
        invitation.setExpirationDate(LocalDateTime.now().plusDays(1));

        when(invitationRepository.findByToken(token)).thenReturn(Optional.of(invitation));

        boolean result = invitationService.validateInvitation(token);

        assertFalse(result);
        verify(invitationRepository, times(1)).findByToken(token);
        verify(invitationRepository, never()).save(any());
    }

    @Test
    void validateInvitation_shouldReturnFalseForNotFound() {
        String token = "not-found-token";
        when(invitationRepository.findByToken(token)).thenReturn(Optional.empty());

        boolean result = invitationService.validateInvitation(token);

        assertFalse(result);
        verify(invitationRepository, times(1)).findByToken(token);
        verify(invitationRepository, never()).save(any());
    }
}
