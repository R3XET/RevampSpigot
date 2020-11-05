package eu.revamp.spigot.enums;
/*
import eu.revamp.spigot.RevampSpigot;
import eu.revamp.spigot.files.LanguageFile;
import eu.revamp.spigot.utils.chat.color.CC;
import eu.revamp.spigot.utils.chat.color.Replacement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Lang {
    //----PREFIX----//
    PREFIX("PREFIX", "&3&lRevampMC &8» "),
    PREFIX_SUCCESS("PREFIX_SUCCESS", "&a&l(!)&r "),
    PREFIX_FAILURE("PREFIX_FAILURE", "&4&l(x)&r "),
    //----GENERAL-----//
    NO_PERMISSION("GENERAL.NO_PERMISSION", "&cYou do not have permission to perform this command."),
    PLAYER_USE_ONLY("GENERAL.PLAYER_USE_ONLY", "&cOnly players can perform this command."),
    CONSOLE_ONLY("GENERAL.CONSOLE_ONLY", "&cThis command can be performer only by console."),
    //----USAGE-----//
    COMMAND_USAGE("COMMAND_USAGE", "&c/examplecommand give (player) (amount)."),
    //----COMMANDS-----//
    COMMANDS_PLAYER_NOT_FOUND("COMMANDS.PLAYER_NOT_FOUND", "%prefix% &cPlayer not found."),
    COMMANDS_MUST_BE_INTEGER("COMMANDS.MUST_BE_INTEGER", "%prefix% &cThis must be an integer!"),
    //----LEVER-----//
    LEVER_COOLDOWN_MESSAGE("LEVER.COOLDOWN_MESSAGE", "&cPlease wait &61.5s&c before using the lever again!"),
    //----CONFIG-----//
    CONFIG_LOADING("CONFIG.LOADING", "&cPlease wait, the config file is still loading..."),
    CONFIG_RELOADED("CONFIG.RELOADED", "&aConfig has been reloaded."),
    //----COLLECTOR-----//


    END("", "");

    private String path;
    private String value;
    private List<String> listValue;

    private LanguageFile languageFile = RevampSpigot.getInstance().getLanguageFile();

    Lang(String path, String value) {
        this.path = path;
        this.value = value;
        this.listValue = new ArrayList<>(Collections.singletonList(value));
    }

    public String toString() {
        Replacement replacement = new Replacement(CC.translate(languageFile.getString(this.path)));
        replacement.add("%prefix% ", languageFile.getString("PREFIX"));
        return replacement.toString().replace(" %newline% ", "\n");
    }

    public String getPath() {
        return this.path;
    }

    public String getValue() {
        return this.value;
    }

    public List<String> getListValue() {
        return this.listValue;
    }

    public LanguageFile getLanguageFile() {
        return this.languageFile;
    }
}
*/