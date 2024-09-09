package coomon.Models;
import client.UI.Validatable;
import java.io.Serializable;

public class Coordinates implements Validatable, Serializable {
    private int x; //Значение поля должно быть больше -296
    private float y;

    public Coordinates(int x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean validate() {
        if (x <= -296) {
            return false;
        } else {
            return true;
        }
    }
}

