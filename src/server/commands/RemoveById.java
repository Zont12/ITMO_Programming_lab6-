package server.commands;
import coomon.exceptions.CollectionisEmptyEX;
import coomon.exceptions.WrongAmountOfElementsEx;
import server.Managers.CollectionManager;
import server.Managers.ResponseOutputer;
import java.util.NoSuchElementException;

public class RemoveById extends AbstractCommand {
    private CollectionManager collectionManager;

    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id", "id", "Удалить элемент коллекции по id");
        this.collectionManager = collectionManager;
    }


    @Override
    public boolean execute(String stringArgument, Object objectArgument)  {
        try {
            Integer id;
            id = Integer.parseInt(stringArgument);
            if (this.collectionManager.getCollection().isEmpty()) throw new CollectionisEmptyEX("Коллекция пуста!");
            if (!this.collectionManager.containsId(id)) throw new NoSuchElementException("Не существует транспорта с таким id!");
            this.collectionManager.removeById(id);
            ResponseOutputer.appendln("Транспорт успешно удален");
            return true ;
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("Неккоректный id транспорта");
        } catch (NoSuchElementException exception) {
            ResponseOutputer.appenderror(exception.toString());
        }catch (CollectionisEmptyEX exception) {
            ResponseOutputer.appenderror(exception.toString());
        }
        return false;
    }
}
