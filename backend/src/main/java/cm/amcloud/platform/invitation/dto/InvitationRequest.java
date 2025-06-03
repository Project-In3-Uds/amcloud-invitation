package cm.amcloud.platform.invitation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * InvitationRequest: DTO for creating a new invitation.
 * Contains the email address to which the invitation should be sent.
 */
@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all fields
public class InvitationRequest {
    private String email; // The email address for the invitation
}
