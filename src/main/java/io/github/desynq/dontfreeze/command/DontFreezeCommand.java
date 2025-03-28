package io.github.desynq.dontfreeze.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import io.github.desynq.dontfreeze.config.ConfigManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class DontFreezeCommand {

    public static void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("dontfreeze")
                .requires(DontFreezeCommand::isHostOrHasPermission)
                .then(Commands.literal("config")
                        .then(Commands.literal("reload")
                                .executes(DontFreezeCommand::reloadConfig)
                        )
                )
        );
    }

    private static boolean isHostOrHasPermission(@NotNull CommandSourceStack source) {
        boolean isHost = Optional.ofNullable(source.getPlayer())
                .map(player -> source.getServer().isSingleplayerOwner(player.getGameProfile()))
                .orElse(false);

        return isHost || source.hasPermission(2);
    }

    private static int reloadConfig(@NotNull CommandContext<CommandSourceStack> cc) {
        CommandSourceStack source = cc.getSource();

        ConfigManager.loadConfigValues();
        source.sendSuccess(() -> Component.translatable("commands.dontfreeze.config.reload"), true);
        return 1;
    }
}
