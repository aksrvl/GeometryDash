package Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.geometrydash.game.SkinsScreen;

public class TextureHelper {
    public static Texture playerTexture1 = new Texture(Gdx.files.internal("skin_1.png"));
    public static Texture playerTexture2 = new Texture(Gdx.files.internal("skin_2.png"));
    public static Texture playerTexture3 = new Texture(Gdx.files.internal("skin_3.png"));
    public static Texture playerTexture4 = new Texture(Gdx.files.internal("skin_4.png"));
    public static Texture playerTexture5 = new Texture(Gdx.files.internal("skin_5.png"));
    public static Texture playerTexture6 = new Texture(Gdx.files.internal("skin_6.png"));
    public static Texture playerTexture7 = new Texture(Gdx.files.internal("skin_7.png"));
    public static Texture playerTexture8 = new Texture(Gdx.files.internal("skin_8.png"));

    public static Texture changeTexture() {
        if(SkinsScreen.selectedSkin== 1){
            return playerTexture2;
        }
        if(SkinsScreen.selectedSkin== 2){
            return playerTexture3;
        }
        if(SkinsScreen.selectedSkin== 3){
            return playerTexture4;
        }
        if(SkinsScreen.selectedSkin== 4){
            return playerTexture5;
        }
        if(SkinsScreen.selectedSkin== 5){
            return playerTexture6;
        }
        if(SkinsScreen.selectedSkin== 6){
            return playerTexture7;
        }
        if(SkinsScreen.selectedSkin== 7){
            return playerTexture8;
        }
        else{
            return playerTexture1;
        }
    }
}
