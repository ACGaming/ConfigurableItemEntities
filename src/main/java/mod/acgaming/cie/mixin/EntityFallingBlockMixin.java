package mod.acgaming.cie.mixin;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityFallingBlock.class, priority = 1002)
public abstract class EntityFallingBlockMixin extends Entity
{
    public EntityFallingBlockMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void CIE_onUpdate(CallbackInfo ci)
    {
        if (!this.world.isRemote)
        {
            List<EntityItem> listEntityItem = this.world.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(-0.1, -0.1, -0.1).expand(0.1, 0, 0.1));
            if (listEntityItem.size() > 0)
            {
                for (EntityItem entity : listEntityItem)
                {
                    applyEntityCollision(entity);
                }
            }
        }
    }
}