package com.butukay.tridentfly.config;

public class TridentFlyConfig {

    private boolean enabled = true;
    private String commandName = ".trident";
    private boolean enabledCommand = false;
    private boolean actionBar = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggleEnabled(){
        this.enabled = !this.enabled;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public boolean isEnabledCommand() {
        return enabledCommand;
    }

    public void setEnabledCommand(boolean enabledCommand) {
        this.enabledCommand = enabledCommand;
    }

    public boolean isActionBar() {
        return actionBar;
    }

    public void setActionBar(boolean actionBar) {
        this.actionBar = actionBar;
    }
}
