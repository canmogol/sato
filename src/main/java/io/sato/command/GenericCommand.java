package io.sato.command;

public class GenericCommand implements Command {

    private CommandLogger logger = new CommandLogger(getClass());
    private final String command;

    public GenericCommand(String command) {
	this.command = command;
    }

    @Override
    public void execute() {
	logger.willExecutingCommand(this.command);
//        try {
//            Runtime.getRuntime().exec(this.command);
//        } catch (IOException e) {
//            logger.runtimeExecError(getClass().getSimpleName(), e.getMessage());
//        }
	logger.executedCommand(this.command);
    }
}
