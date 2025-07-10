package pyre.tinkerslevellingaddon.command;

import static pyre.tinkerslevellingaddon.ReinforceModifier.REINFORCE_KEY;
import static pyre.tinkerslevellingaddon.command.ModCommands.PERMISSION_GAME_COMMANDS;

import java.util.List;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import pyre.tinkerslevellingaddon.setup.Registration;
import pyre.tinkerslevellingaddon.util.ModUtil;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.ToolDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.shared.command.HeldModifiableItemIterator;

public class ReinforceCommand {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> subCommand) {
        subCommand.requires(sender -> sender.hasPermission(PERMISSION_GAME_COMMANDS))
                .then(Commands.argument("targets", EntityArgument.entities())
                        .then(Commands.literal("add")
                                .executes(context -> run(context, ModCommands.Operation.ADD, 1))
                                .then(Commands.argument("count", IntegerArgumentType.integer())
                                        .executes(context -> run(context, ModCommands.Operation.ADD))))
                        .then(Commands.literal("set")
                                .then(Commands.argument("count", IntegerArgumentType.integer(0))
                                        .executes(context -> run(context, ModCommands.Operation.SET)))));
    }

    private static int run(CommandContext<CommandSourceStack> context, ModCommands.Operation op)
            throws CommandSyntaxException {
        return run(context, op, IntegerArgumentType.getInteger(context, "count"));
    }

    private static int run(CommandContext<CommandSourceStack> context, ModCommands.Operation op, int count)
            throws CommandSyntaxException {
        List<LivingEntity> successes = HeldModifiableItemIterator.apply(context, (living, stack) -> {
            if (ModifierUtil.getModifierLevel(stack, Registration.REINFORCE.getId()) <= 0) return false;
            
            ToolStack tool = ToolStack.copyFrom(stack);
            ToolDataNBT persistentData = tool.getPersistentData();
            int currentReinforce = persistentData.getInt(REINFORCE_KEY);

            int offset = op == ModCommands.Operation.ADD ? currentReinforce : 0;
            persistentData.putInt(REINFORCE_KEY, Math.max(0, Math.min(offset+count, 5)));
            
            Component error = tool.tryValidate();
            if (error != null) {
                throw ModCommands.TOOL_VALIDATION_ERROR.create(error);
            }
            
            living.setItemInHand(InteractionHand.MAIN_HAND, tool.createStack(stack.getCount()));
            return true;
        });
        
        CommandSourceStack source = context.getSource();
        
        int size = successes.size();
        source.sendSuccess(() -> ModUtil.makeTranslation("command", "reinforce.success."+(op == ModCommands.Operation.ADD ? "add" : "set")), true);
        
        return size;
    }
}
