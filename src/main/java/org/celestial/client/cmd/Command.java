package org.celestial.client.cmd;

@FunctionalInterface
public interface Command {
    void execute(String... strings);
}