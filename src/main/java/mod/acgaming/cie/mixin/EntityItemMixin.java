package mod.acgaming.cie.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import mod.acgaming.cie.config.CIEConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityItem.class, priority = 1002)
public abstract class EntityItemMixin extends Entity
{
    private boolean playerInteraction;

    public EntityItemMixin(World worldIn)
    {
        super(worldIn);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox()
    {
        if (this.ticksExisted > 10 && CIEConfig.gItemPhysics) return this.getEntityBoundingBox();
        else return null;
    }

    @Override
    public void applyEntityCollision(Entity entity)
    {
        if (this.ticksExisted > 10 && CIEConfig.gItemPhysics)
        {
            if (entity instanceof EntityItem)
            {
                if (entity.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY)
                {
                    super.applyEntityCollision(entity);
                }
            }
            else if (entity.getEntityBoundingBox().minY <= this.getEntityBoundingBox().minY)
            {
                super.applyEntityCollision(entity);
            }
        }
        else
        {
            super.applyEntityCollision(entity);
        }
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return this.ticksExisted > 10 && CIEConfig.gItemPhysics || !Loader.isModLoaded("realdrops");
    }

    @Override
    public boolean canBePushed()
    {
        return this.ticksExisted > 10 && CIEConfig.gItemPhysics;
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (!CIEConfig.gPickupAutomatic)
        {
            if (!CIEConfig.gPickupSneaking || player.isSneaking())
            {
                playerInteraction = true;
                this.onCollideWithPlayer(player);
                playerInteraction = false;
                return true;
            }
        }
        playerInteraction = false;
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity)
    {
        return this.ticksExisted > 10 && CIEConfig.gItemPhysics || entity.canBePushed() ? entity.getEntityBoundingBox() : null;
    }

    @Inject(method = "onCollideWithPlayer", at = @At("HEAD"), cancellable = true)
    public void CIE_onCollideWithPlayer(EntityPlayer entityIn, CallbackInfo ci)
    {
        Item heldItem = entityIn.getHeldItem(CIEConfig.gCollectionToolHand).getItem();
        if (!CIEConfig.gPickupAutomatic && !playerInteraction && !CIEConfig.isCollectionTool(heldItem) && !Loader.isModLoaded("realdrops") || CIEConfig.gPickupSneaking && !entityIn.isSneaking())
        {
            ci.cancel();
        }
    }

    @Inject(method = "searchForOtherItemsNearby", at = @At("HEAD"), cancellable = true)
    private void CIE_searchForOtherItemsNearby(CallbackInfo ci)
    {
        if (!CIEConfig.gCombining) ci.cancel();
    }

    @Inject(method = "combineItems", at = @At("HEAD"), cancellable = true)
    private void CIE_combineItems(EntityItem other, CallbackInfoReturnable<Boolean> cir)
    {
        if (!CIEConfig.gCombining) cir.setReturnValue(false);
    }
}