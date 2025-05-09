package cm.amcloud.platform.invitation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cm.amcloud.platform.invitation.model.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByToken(String token);
}
