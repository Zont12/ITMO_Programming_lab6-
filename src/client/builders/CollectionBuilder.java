package client.builders;
import coomon.exceptions.IncorrectInputEX;
import java.io.Serializable;
// CollectionBuilder - это класс для обработки моих объектов, Еlement - это generic, то бишь обобщенный тип данных, это сделано для обработки любых объектов
// Serializable - это сериализация данных, она нужно для передачи данных по сети

public abstract class CollectionBuilder<Element> implements Serializable {
    public abstract Element build() throws IncorrectInputEX;
}
