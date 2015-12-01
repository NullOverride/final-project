
public class Command {

	public String command;
    public DShapeModel shape;

    public Command() {
        command = null;
        shape = null;
    }

    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }

    public DShapeModel getShape() { 
        return shape;
    }
    public void setShape(DShapeModel shape) {
        this.shape = shape;
    }

}
