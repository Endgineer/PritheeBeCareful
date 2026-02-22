package pyre.tinkerslevellingaddon.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import pyre.tinkerslevellingaddon.config.Config;
import pyre.tinkerslevellingaddon.setup.Registration;
import pyre.tinkerslevellingaddon.util.ModUtil;
import pyre.tinkerslevellingaddon.util.ToolLevellingUtil;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.shared.command.HeldModifiableItemIterator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LevelsCommand {
    
    public static void register(LiteralArgumentBuilder<CommandSourceStack> subCommand) {
        subCommand.requires(sender -> sender.hasPermission(ModCommands.PERMISSION_GAME_COMMANDS))
                .then(Commands.argument("targets", EntityArgument.entities())
                        .then(Commands.literal("add")
                                .then(Commands.argument("count", IntegerArgumentType.integer())
                                        .executes(context -> run(context, ModCommands.Operation.ADD))))
                        .then(Commands.literal("set")
                                .then(Commands.argument("count", IntegerArgumentType.integer())
                                        .executes(context -> run(context, ModCommands.Operation.SET)))));
    }
    
    private static int run(CommandContext<CommandSourceStack> context, ModCommands.Operation op)
            throws CommandSyntaxException {
        return run(context, op, IntegerArgumentType.getInteger(context, "count"));
    }
    
    private static int run(CommandContext<CommandSourceStack> context, ModCommands.Operation op, int count)
            throws CommandSyntaxException {
        List<LivingEntity> successes = HeldModifiableItemIterator.apply(context, (living, stack) -> {
            if (ModifierUtil.getModifierLevel(stack, Registration.REINFORCE.get().getId()) <= 0) {
                return false;
            }
            
            ToolStack tool = ToolStack.copyFrom(stack);
            if (op == ModCommands.Operation.ADD) {
                if (!addLevels(tool, count, living)) {
                    throw new SimpleCommandExceptionType(ModUtil.makeTranslation("command", "levels.failure.add.already_max_level", stack.getDisplayName())).create();
                }
            } else {
                if (!setLevel(tool, count, living)) {
                    throw new SimpleCommandExceptionType(ModUtil.makeTranslation("command", "levels.failure.set.current_level", stack.getDisplayName(), count)).create();
                }
                
            }
            
            Component error = tool.tryValidate();
            if (error != null) {
                throw ModCommands.TOOL_VALIDATION_ERROR.create(error);
            }
            
            living.setItemInHand(InteractionHand.MAIN_HAND, tool.createStack(stack.getCount()));
            return true;
        });
        
        // success message
        CommandSourceStack source = context.getSource();
        int size = successes.size();
        if (op == ModCommands.Operation.ADD) {
            if (size == 1) {
                source.sendSuccess(() -> ModUtil.makeTranslation("command", "levels.success.add.single", count,
                        successes.get(0).getDisplayName()), true);
            } else {
                source.sendSuccess(() -> ModUtil.makeTranslation("command", "levels.success.add.multiple", count,
                        size), true);
            }
        } else {
            if (size == 1) {
                source.sendSuccess(() -> ModUtil.makeTranslation("command", "levels.success.set.single", count,
                        successes.get(0).getDisplayName()), true);
            } else {
                source.sendSuccess(() -> ModUtil.makeTranslation("command", "levels.success.set.multiple", count,
                        size), true);
            }
        }
        return size;
    }
    
    private static boolean addLevels(ToolStack tool, int count, LivingEntity living) {
        ServerPlayer player = living instanceof ServerPlayer p ? p : null;
        return ToolLevellingUtil.addLevels(tool, count, player);
    }
    
    private static boolean setLevel(ToolStack tool, int count, LivingEntity living) {
        ServerPlayer player = living instanceof ServerPlayer p ? p : null;
        return ToolLevellingUtil.setLevel(tool, count, player);
    }
}
