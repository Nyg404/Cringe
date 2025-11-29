package io.github.оharvestrogalia.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.EnumMap;

public class PlayerInputBind {
    public enum Action {
        MOVE_LEFT, MOVE_RIGHT, MOVE_UP, MOVE_DOWN, JUMP, CTRL
    }

    private static EnumMap<Action, Integer> keyMap = new EnumMap<>(Action.class);

    static {
        keyMap.put(Action.MOVE_LEFT, Input.Keys.A);
        keyMap.put(Action.MOVE_RIGHT, Input.Keys.D);
        keyMap.put(Action.MOVE_UP, Input.Keys.W);
        keyMap.put(Action.MOVE_DOWN, Input.Keys.S);
        keyMap.put(Action.JUMP, Input.Keys.SPACE);
        keyMap.put(Action.CTRL, Input.Keys.CONTROL_LEFT);
    }

    public static boolean isPressed(Action action) {
        return Gdx.input.isKeyPressed(keyMap.get(action));
    }
    public static boolean justPressed(Action action){
        return Gdx.input.isKeyJustPressed(keyMap.get(action));
    }
}
