package br.com.mariojp.game.factory;

import br.com.mariojp.game.model.Inimigo;
import br.com.mariojp.game.model.Nave;
import br.com.mariojp.game.model.Sprite;

public class SpriteFactory {


    public static Sprite createSprite(String type, int x, int y, int alcance, String imageName) {
        if (type.equals("Nave")) {
            Nave nave = new Nave(x, y, alcance);
            nave.carregarImagem(imageName);
            return nave;
        } else if (type.equals("Inimigo")) {

            if (type.equals("InimigoTipo1")) {
                Inimigo inimigo = new Inimigo(x, y);
                inimigo.carregarImagem(imageName);
                return inimigo;
            } else if (type.equals("InimigoTipo2")) {
                Inimigo inimigo = new Inimigo(x, y);
                inimigo.carregarImagem(imageName);
                return inimigo;
            }


        }
        return null;
    }
}




