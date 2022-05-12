package mod.acgaming.cie.mixin;

import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import mod.acgaming.cie.config.CIEConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = Player.class, priority = 1002)
public abstract class PlayerMixin extends LivingEntity
{
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_)
    {
        super(p_20966_, p_20967_);
    }

    /**
     * @author ACGaming
     */
    @Overwrite
    @Nullable
    public ItemEntity drop(ItemStack droppedItem, boolean dropAround, boolean traceItem)
    {
        if (droppedItem.isEmpty())
        {
            return null;
        }
        else
        {
            if (this.level.isClientSide)
            {
                this.swing(InteractionHand.MAIN_HAND);
            }

            double d0 = this.getEyeY() - (double) 0.3F;
            double xOffset = 0.0D;
            double zOffset = 0.0D;

            if (this.getDirection() == Direction.NORTH) zOffset = -0.5D;
            else if (this.getDirection() == Direction.EAST) xOffset = 0.5D;
            else if (this.getDirection() == Direction.SOUTH) zOffset = 0.5D;
            else if (this.getDirection() == Direction.WEST) xOffset = -0.5D;

            ItemEntity itementity = new ItemEntity(this.level, this.getX() + xOffset, d0, this.getZ() + zOffset, droppedItem);
            itementity.setPickUpDelay(CIEConfigHandler.GAMEPLAY_SETTINGS.pickupDelay.get());

            if (traceItem)
            {
                itementity.setThrower(this.getUUID());
            }

            if (dropAround)
            {
                float f = this.random.nextFloat() * 0.5F;
                float f1 = this.random.nextFloat() * ((float) Math.PI * 2F);
                itementity.setDeltaMovement(-Mth.sin(f1) * f, 0.2F, Mth.cos(f1) * f);
            }
            else
            {
                float f7 = 0.3F;
                float f8 = Mth.sin(this.getXRot() * ((float) Math.PI / 180F));
                float f2 = Mth.cos(this.getXRot() * ((float) Math.PI / 180F));
                float f3 = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
                float f4 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
                float f5 = this.random.nextFloat() * ((float) Math.PI * 2F);
                float f6 = 0.02F * this.random.nextFloat();
                itementity.setDeltaMovement((double) (-f3 * f2 * 0.3F) + Math.cos(f5) * (double) f6, -f8 * 0.3F + 0.1F + (this.random.nextFloat() - this.random.nextFloat()) * 0.1F, (double) (f4 * f2 * 0.3F) + Math.sin(f5) * (double) f6);
            }

            return itementity;
        }
    }
}