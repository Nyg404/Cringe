package io.github.оharvestrogalia.input;

import io.github.оharvestrogalia.entity.Player;

public class PlayerInput {
    private final Player player;
    private int jumpCount = 2;
    private boolean wasOnGround = false;

    public PlayerInput(Player player) {
        this.player = player;
    }

    public void update() {
        boolean left = PlayerInputBind.isPressed(PlayerInputBind.Action.MOVE_LEFT);
        boolean right = PlayerInputBind.isPressed(PlayerInputBind.Action.MOVE_RIGHT);
        player.move(right, left);


        if (player.isOnGround() && !wasOnGround) {
            jumpCount = 2;
        }

        wasOnGround = player.isOnGround();


        if (PlayerInputBind.justPressed(PlayerInputBind.Action.JUMP)) {
            if (jumpCount > 0) {
                player.jump();
                jumpCount -= 1;
            }
        }
    }
}
