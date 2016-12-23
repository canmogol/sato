package io.sato.command;

public class Invoker {
    public void execute(Command command) {
        command.execute();
    }
}
