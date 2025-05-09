package cm.amcloud.platform.invitation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cm.amcloud.platform.invitation.model.Invitation;
import cm.amcloud.platform.invitation.service.InvitationService;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping
    public String invite(@RequestBody InvitationRequest request) {
        Invitation invitation = invitationService.createInvitation(request.getEmail());
        return invitation.getToken();
    }

    @PostMapping("/validate")
    public boolean validate(@RequestBody TokenRequest request) {
        return invitationService.validateInvitation(request.getToken());
    }
}
