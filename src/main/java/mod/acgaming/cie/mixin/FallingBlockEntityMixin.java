package mod.acgaming.cie.mixin;

import java.util.List;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FallingBlockEntity.class, priority = 1002)
public abstract class FallingBlockEntityMixin extends Entity
{
    @Shadow
    public boolean dropItem = true;

    @Shadow
    private BlockState blockState = Blocks.SAND.defaultBlockState();

    public FallingBlockEntityMixin(EntityType<?> p_19870_, Level p_19871_)
    {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void CIE_tick(CallbackInfo ci)
    {
        if (!this.level.isClientSide)
        {
            List<ItemEntity> listItemEntity = this.level.getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(0, -0.1, 0));
            if (listItemEntity.size() > 0)
            {
                for (ItemEntity entity : listItemEntity)
                {
                    if (entity.isOnGround())
                    {
                        if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS))
                        {
                            this.spawnAtLocation(this.blockState.getBlock());
                        }
                        this.discard();
                        break;
                    }
                }
            }
        }
    }
}