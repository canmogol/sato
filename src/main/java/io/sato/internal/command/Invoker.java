package io.sato.internal.command;

public class Invoker {
    public void execute(Command command) {
        command.execute();
    }
}
