package io.github.оharvestrogalia;


import com.badlogic.gdx.Game;
import io.github.оharvestrogalia.screen.GameScreen;


public class HarvestRogaliaGame extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
