package io.github.оharvestrogalia.entity;

public class PlayerInput {
    private final Player player;
    private int jumpCount = 2;
    private boolean wasOnGround = false;

    public PlayerInput(Player player) {
        this.player = player;
    }

    public void update(){
        boolean left = PlayerInputBind.isPressed(PlayerInputBind.Action.MOVE_LEFT);
        boolean right = PlayerInputBind.isPressed(PlayerInputBind.Action.MOVE_RIGHT);
        player.move(right, left);

        // СБРОС jumpCount КОГДА КАСАЕМСЯ ЗЕМЛИ
        if (player.isOnGround() && !wasOnGround) {
            jumpCount = 2;  // сбрасываем когда приземлились
        }

        wasOnGround = player.isOnGround();

        // ОБРАБОТКА ПРЫЖКА
        if(PlayerInputBind.justPressed(PlayerInputBind.Action.JUMP)){
            if(jumpCount > 0){
                player.jump();
                jumpCount -= 1;
            }
        }
    }
}
