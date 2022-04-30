package mod.acgaming.cie.mixin;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFallingBlock.class)
public abstract class EntityFallingBlockMixin extends Entity
{
    @Shadow
    public boolean shouldDropItem;
    @Shadow
    public int fallTime;
    @Shadow
    private IBlockState fallTile;

    public EntityFallingBlockMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void CIE_onUpdate(CallbackInfo ci)
    {
        if (!this.world.isRemote)
        {
            List<EntityItem> listEntityItem = this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(0, -0.1, 0));
            if (listEntityItem.size() > 0)
            {
                for (EntityItem entity : listEntityItem)
                {
                    if (entity.onGround)
                    {
                        if (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops"))
                        {
                            this.entityDropItem(new ItemStack(this.fallTile.getBlock(), 1, this.fallTile.getBlock().damageDropped(this.fallTile)), 0.0F);
                        }
                        this.setDead();
                        break;
                    }
                }
            }
        }
    }
}