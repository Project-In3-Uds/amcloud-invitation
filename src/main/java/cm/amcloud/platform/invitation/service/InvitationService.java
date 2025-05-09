package cm.amcloud.platform.invitation.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cm.amcloud.platform.invitation.model.Invitation;
import cm.amcloud.platform.invitation.repository.InvitationRepository;

@Service
public class InvitationService {

    private final InvitationRepository invitationRepository;

    public InvitationService(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    public Invitation createInvitation(String email) {
        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setToken(generateRandomToken());
        invitation.setExpirationDate(LocalDateTime.now().plusDays(7)); // Token valid for 7 days
        invitation.setAccepted(false);
        return invitationRepository.save(invitation);
    }

    public boolean validateInvitation(String token) {
        return invitationRepository.findByToken(token)
                .filter(invitation -> !invitation.isAccepted() && invitation.getExpirationDate().isAfter(LocalDateTime.now()))
                .map(invitation -> {
                    invitation.setAccepted(true);
                    invitationRepository.save(invitation);
                    return true;
                }).orElse(false);
    }

    private String generateRandomToken() {
        return UUID.randomUUID().toString(); // Generate a random UUID as the token
    }
}
