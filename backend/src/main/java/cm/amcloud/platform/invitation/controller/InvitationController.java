package cm.amcloud.platform.invitation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader; // Import added
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cm.amcloud.platform.invitation.model.Invitation;
import cm.amcloud.platform.invitation.service.InvitationService;
import cm.amcloud.platform.invitation.dto.InvitationRequest; // Assuming this DTO exists
import cm.amcloud.platform.invitation.dto.TokenRequest; // Assuming this DTO exists

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping("/create")
    public String invite(@RequestBody InvitationRequest request,
                         @RequestHeader(name = "X-User-ID", required = false) String userId,
                         @RequestHeader(name = "X-User-Roles", required = false) String userRoles,
                         @RequestHeader(name = "X-User-Scopes", required = false) String userScopes) {
        System.out.println("User ID: " + userId + ", Roles: " + userRoles + ", Scopes: " + userScopes + " is creating an invitation.");
        Invitation invitation = invitationService.createInvitation(request.getEmail());
        return invitation.getToken();
    }

    @PostMapping("/validate")
    public boolean validate(@RequestBody TokenRequest request,
                            @RequestHeader(name = "X-User-ID", required = false) String userId,
                            @RequestHeader(name = "X-User-Roles", required = false) String userRoles,
                            @RequestHeader(name = "X-User-Scopes", required = false) String userScopes) {
        System.out.println("User ID: " + userId + ", Roles: " + userRoles + ", Scopes: " + userScopes + " is validating an invitation.");
        return invitationService.validateInvitation(request.getToken());
    }
}
