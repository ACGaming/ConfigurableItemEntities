package mod.acgaming.cie.mixin;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityItem.class)
public abstract class EntityItemMixin extends Entity
{
    public EntityItemMixin(World worldIn)
    {
        super(worldIn);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox()
    {
        return this.getEntityBoundingBox();
    }

    @Override
    public void applyEntityCollision(Entity entity)
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

    @Override
    public boolean canBePushed()
    {
        return true;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entity)
    {
        return entity.canBePushed() ? entity.getEntityBoundingBox() : null;
    }

    /**
     * @author ACGaming
     */
    @Overwrite
    private void searchForOtherItemsNearby()
    {
    }

    /**
     * @author ACGaming
     */
    @Overwrite
    private boolean combineItems(EntityItem other)
    {
        return false;
    }
}