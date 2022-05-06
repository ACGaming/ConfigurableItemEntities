package mod.acgaming.cie.mixin;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import mod.acgaming.cie.CIE;
import mod.acgaming.cie.config.CIEConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityLivingBase
{
    public EntityPlayerMixin(World worldIn)
    {
        super(worldIn);
    }

    @Shadow
    public ItemStack dropItemAndGetStack(EntityItem p_184816_1_)
    {
        return null;
    }

    @Shadow
    public void addStat(StatBase stat)
    {
    }

    @Shadow
    public void addStat(StatBase stat, int amount)
    {
    }

    /**
     * @author ACGaming
     */
    @Overwrite
    @Nullable
    public EntityItem dropItem(ItemStack droppedItem, boolean dropAround, boolean traceItem)
    {
        if (droppedItem.isEmpty())
        {
            return null;
        }
        else
        {
            double d0 = this.posY - 0.3D + (double) this.getEyeHeight();

            double xOffset = 0.0D;
            double zOffset = 0.0D;

            if (CIEConfig.gDropOffset || CIE.aquaacrobatics)
            {
                if (this.getHorizontalFacing() == EnumFacing.NORTH) zOffset = -0.5D;
                else if (this.getHorizontalFacing() == EnumFacing.EAST) xOffset = 0.5D;
                else if (this.getHorizontalFacing() == EnumFacing.SOUTH) zOffset = 0.5D;
                else if (this.getHorizontalFacing() == EnumFacing.WEST) xOffset = -0.5D;
            }

            EntityItem entityitem = new EntityItem(this.world, this.posX + xOffset, d0, this.posZ + zOffset, droppedItem);
            entityitem.setPickupDelay(CIEConfig.gPickupDelay);

            if (traceItem)
            {
                entityitem.setThrower(this.getName());
            }

            if (dropAround)
            {
                float f = this.rand.nextFloat() * 0.5F;
                float f1 = this.rand.nextFloat() * ((float) Math.PI * 2F);
                entityitem.motionX = -MathHelper.sin(f1) * f;
                entityitem.motionZ = MathHelper.cos(f1) * f;
                entityitem.motionY = 0.2D;
            }
            else
            {
                float f2 = 0.3F;
                entityitem.motionX = -MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * f2;
                entityitem.motionZ = MathHelper.cos(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * f2;
                entityitem.motionY = -MathHelper.sin(this.rotationPitch * 0.017453292F) * f2 + 0.1F;
                float f3 = this.rand.nextFloat() * ((float) Math.PI * 2F);
                f2 = 0.02F * this.rand.nextFloat();
                entityitem.motionX += Math.cos(f3) * (double) f2;
                entityitem.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
                entityitem.motionZ += Math.sin(f3) * (double) f2;
            }

            ItemStack itemstack = this.dropItemAndGetStack(entityitem);

            if (traceItem)
            {
                if (!itemstack.isEmpty())
                {
                    this.addStat(StatList.getDroppedObjectStats(itemstack.getItem()), droppedItem.getCount());
                }
                this.addStat(StatList.DROP);
            }

            return entityitem;
        }
    }
}