package Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.geometrydash.game.SkinsScreen;

/**
 * The TextureHelper class provides static methods to manage textures for player and ship skins in the Geometry Dash game.
 */
public class TextureHelper {
    public static Texture playerTexture1 = new Texture(Gdx.files.internal("skins/skin_1.png"));
    public static Texture playerTexture2 = new Texture(Gdx.files.internal("skins/skin_2.png"));
    public static Texture playerTexture3 = new Texture(Gdx.files.internal("skins/skin_3.png"));
    public static Texture playerTexture4 = new Texture(Gdx.files.internal("skins/skin_4.png"));
    public static Texture playerTexture5 = new Texture(Gdx.files.internal("skins/skin_5.png"));
    public static Texture playerTexture6 = new Texture(Gdx.files.internal("skins/skin_6.png"));
    public static Texture playerTexture7 = new Texture(Gdx.files.internal("skins/skin_7.png"));
    public static Texture playerTexture8 = new Texture(Gdx.files.internal("skins/skin_8.png"));

    public static Texture shipTexture1 = new Texture(Gdx.files.internal("skins_plane/ship1.png"));
    public static Texture shipTexture2 = new Texture(Gdx.files.internal("skins_plane/ship2.png"));
    public static Texture shipTexture3 = new Texture(Gdx.files.internal("skins_plane/ship3.png"));
    public static Texture shipTexture4 = new Texture(Gdx.files.internal("skins_plane/ship4.png"));
    public static Texture shipTexture5 = new Texture(Gdx.files.internal("skins_plane/ship5.png"));
    public static Texture shipTexture6 = new Texture(Gdx.files.internal("skins_plane/ship6.png"));
    public static Texture shipTexture7 = new Texture(Gdx.files.internal("skins_plane/ship7.png"));
    public static Texture shipTexture8 = new Texture(Gdx.files.internal("skins_plane/ship8.png"));

    /**
     * Changes the player texture based on the selected skin.
     *
     * @return the texture corresponding to the selected player skin
     */
    public static Texture changeTexture() {
        switch (SkinsScreen.selectedSkin) {
            case 1:
                return playerTexture2;
            case 2:
                return playerTexture3;
            case 3:
                return playerTexture4;
            case 4:
                return playerTexture5;
            case 5:
                return playerTexture6;
            case 6:
                return playerTexture7;
            case 7:
                return playerTexture8;
            default:
                return playerTexture1;
        }
    }

    /**
     * Changes the ship texture based on the selected skin.
     *
     * @return the texture corresponding to the selected ship skin
     */
    public static Texture shipTexture() {
        switch (SkinsScreen.selectedSkin) {
            case 1:
                return shipTexture2;
            case 2:
                return shipTexture3;
            case 3:
                return shipTexture4;
            case 4:
                return shipTexture5;
            case 5:
                return shipTexture6;
            case 6:
                return shipTexture7;
            case 7:
                return shipTexture8;
            default:
                return shipTexture1;
        }
    }
}
