package mod.acgaming.cie.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import mod.acgaming.cie.config.CIEConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemEntity.class, priority = 1002)
public abstract class ItemEntityMixin extends Entity
{
    @Inject(method = "areMergable", at = @At("HEAD"), cancellable = true)
    private static void CIE_areMergable(CallbackInfoReturnable<Boolean> cir)
    {
        if (!CIEConfigHandler.GAMEPLAY_SETTINGS.combining.get()) cir.setReturnValue(false);
    }

    public boolean playerInteraction;

    @Shadow
    private int pickupDelay;

    public ItemEntityMixin(EntityType<?> entityType, Level level)
    {
        super(entityType, level);
    }

    @Shadow
    public abstract void playerTouch(Player player);

    @Override
    public void push(Entity entity)
    {
        if (this.tickCount > 10 && CIEConfigHandler.GAMEPLAY_SETTINGS.itemPhysics.get())
        {
            if (entity.getBoundingBox().minY < this.getBoundingBox().maxY)
            {
                super.push(entity);
            }
            else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY)
            {
                super.push(entity);
            }
        }
    }

    @Override
    public boolean isPickable()
    {
        return !this.isRemoved();
    }

    @Override
    public boolean isPushable()
    {
        return this.tickCount > 10 && CIEConfigHandler.GAMEPLAY_SETTINGS.itemPhysics.get() && !this.isRemoved();
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand)
    {
        if (!CIEConfigHandler.GAMEPLAY_SETTINGS.pickupCrouching.get() || player.isCrouching())
        {
            playerInteraction = true;
            this.playerTouch(player);
            playerInteraction = false;
            return InteractionResult.SUCCESS;
        }
        playerInteraction = false;
        return InteractionResult.PASS;
    }

    @Override
    public boolean canCollideWith(Entity entity)
    {
        return this.tickCount > 10 && CIEConfigHandler.GAMEPLAY_SETTINGS.itemPhysics.get();
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return this.tickCount > 10 && CIEConfigHandler.GAMEPLAY_SETTINGS.itemPhysics.get();
    }

    @Override
    public boolean skipAttackInteraction(Entity p_20357_)
    {
        return true;
    }

    @Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
    public void CIE_playerTouch(Player player, CallbackInfo ci)
    {
        Item heldItem = player.getItemInHand(CIEConfigHandler.GAMEPLAY_SETTINGS.collectionToolHand.get()).getItem();
        if (!CIEConfigHandler.GAMEPLAY_SETTINGS.pickupAutomatic.get() && !playerInteraction && !CIEConfigHandler.isCollectionTool(heldItem) || CIEConfigHandler.GAMEPLAY_SETTINGS.pickupCrouching.get() && !player.isCrouching())
        {
            ci.cancel();
        }
    }

    /**
     * @author ACGaming
     */
    @Overwrite
    public void setDefaultPickUpDelay()
    {
        this.pickupDelay = CIEConfigHandler.GAMEPLAY_SETTINGS.pickupDelay.get();
    }

    @Inject(method = "isMergable", at = @At("HEAD"), cancellable = true)
    private void CIE_isMergable(CallbackInfoReturnable<Boolean> cir)
    {
        if (!CIEConfigHandler.GAMEPLAY_SETTINGS.combining.get()) cir.setReturnValue(false);
    }

    @Inject(method = "tryToMerge", at = @At("HEAD"), cancellable = true)
    private void CIE_tryToMerge(ItemEntity p_32016_, CallbackInfo ci)
    {
        if (!CIEConfigHandler.GAMEPLAY_SETTINGS.combining.get()) ci.cancel();
    }

    @Inject(method = "mergeWithNeighbours", at = @At("HEAD"), cancellable = true)
    private void CIE_mergeWithNeighbours(CallbackInfo ci)
    {
        if (!CIEConfigHandler.GAMEPLAY_SETTINGS.combining.get()) ci.cancel();
    }
}