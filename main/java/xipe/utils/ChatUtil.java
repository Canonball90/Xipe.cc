package xipe.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;


public class ChatUtil {
    private static final Formatting GRAY = Formatting.GRAY;
    private static final Formatting DARK_AQUA = Formatting.DARK_AQUA;
    private static final Formatting RED = Formatting.RED;
    private static final Formatting WHITE = Formatting.WHITE;
    private static final Formatting YELLOW = Formatting.YELLOW;

    private static final String PREFIX = GRAY + "[" + DARK_AQUA + "Xipe.cc" + GRAY + "] " + WHITE;

    private static void printClientMessage(String message) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(message));
    }

    public static void printInfo(String message) {
        printClientMessage(PREFIX + message);
    }

    public static void printInfo(String message, int id) {
        printClientMessage(PREFIX + message);
    }

    public static void printInfoNoPrefix(String message, int id) {
        printClientMessage(message);
    }

    public static void printWarning(String message) {
        printClientMessage(PREFIX + YELLOW + message);
    }

    public static void printError(String message) {
        printClientMessage(PREFIX + RED + message);
    }

    public static void printError(String message, int id) {
        printClientMessage(PREFIX + RED + message);
    }
}