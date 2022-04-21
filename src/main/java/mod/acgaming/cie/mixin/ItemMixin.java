package mod.acgaming.cie.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mod.acgaming.cie.config.CIEConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Item.class)
public class ItemMixin
{
    /**
     * @author ACGaming
     */
    @Overwrite(remap = false)
    public int getEntityLifespan(ItemStack itemStack, World world)
    {
        return CIEConfig.gEntityLifespan;
    }
}