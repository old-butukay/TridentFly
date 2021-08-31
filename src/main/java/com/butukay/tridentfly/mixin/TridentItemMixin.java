package com.butukay.tridentfly.mixin;

import com.butukay.tridentfly.TridentFly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.item.*;

import java.util.function.Consumer;

@Mixin(TridentItem.class)
public abstract class TridentItemMixin extends Item implements Vanishable {

    public TridentItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("RETURN"), method = "use", cancellable = true)
    public void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (user.isTouchingWaterOrRain() || TridentFly.getConfig().isEnabled()) {
            user.setCurrentHand(hand);
            cir.setReturnValue(TypedActionResult.consume(itemStack));
        } else {
            cir.setReturnValue(TypedActionResult.fail(itemStack));
        }
    }

    /**
     * @author Butukay
     * @reason I need it
     */

    @Overwrite
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        PlayerEntity playerEntity = (PlayerEntity) user;

        int j = EnchantmentHelper.getRiptide(stack);

        if (!world.isClient) {
            stack.damage(1, (LivingEntity) playerEntity, (Consumer<LivingEntity>) ((p) -> {
                p.sendToolBreakStatus(user.getActiveHand());
            }));
            if (j == 0) {
                TridentEntity tridentEntity = new TridentEntity(world, playerEntity, stack);
                tridentEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F + (float) j * 0.5F, 1.0F);
                if (playerEntity.getAbilities().creativeMode) {
                    tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                }

                world.spawnEntity(tridentEntity);
                world.playSoundFromEntity((PlayerEntity) null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                if (!playerEntity.getAbilities().creativeMode) {
                    playerEntity.getInventory().removeOne(stack);
                }
            }
        }

        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        if (j > 0) {
            float f = playerEntity.getYaw();
            float g = playerEntity.getPitch();
            float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
            float k = -MathHelper.sin(g * 0.017453292F);
            float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
            float m = MathHelper.sqrt(h * h + k * k + l * l);
            float n = 3.0F * ((1.0F + (float) j) / 4.0F);
            h *= n / m;
            k *= n / m;
            l *= n / m;
            playerEntity.addVelocity((double) h, (double) k, (double) l);
            playerEntity.setRiptideTicks(20);
            if (playerEntity.isOnGround()) {
                float o = 1.1999999F;
                playerEntity.move(MovementType.SELF, new Vec3d(0.0D, 1.1999999284744263D, 0.0D));
            }

            SoundEvent soundEvent3;
            if (j >= 3) {
                soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
            } else if (j == 2) {
                soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
            } else {
                soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
            }

            world.playSoundFromEntity((PlayerEntity) null, playerEntity, soundEvent3, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
    }
}
