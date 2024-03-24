package org.zombiesplugin.zombies.Lobby;

import java.util.Optional;
import java.util.UUID;

public class LobbyMapCreatedResult {
    public Boolean Success;

    public Optional<UUID> TempName;

    public LobbyMapCreatedResult() {
        Success = false;
    }

    public LobbyMapCreatedResult(UUID tempName) {
        Success = true;
        TempName = Optional.of(tempName);
    }
}
