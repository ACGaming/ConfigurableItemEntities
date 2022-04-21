package mod.acgaming.cie.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import mod.acgaming.cie.config.CIEConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderEntityItem.class)
public abstract class RenderEntityItemMixin extends Render<EntityItem>
{
    protected RenderEntityItemMixin(RenderManager renderManager)
    {
        super(renderManager);
    }

    @Shadow
    protected int getModelCount(ItemStack stack) {return 0;}

    /**
     * @author ACGaming
     */
    @Overwrite
    private int transformModelCount(EntityItem itemIn, double x, double y, double z, float partialTicks, IBakedModel ibakedmodel)
    {
        ItemStack itemstack = itemIn.getItem();

        boolean flag = ibakedmodel.isGui3d();
        int i = this.getModelCount(itemstack);
        float f1 = CIEConfig.rBob ? MathHelper.sin(((float) itemIn.getAge() + partialTicks) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F : 0;
        float f2 = CIEConfig.rBob ? ibakedmodel.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y : 0;

        if (CIEConfig.rBob) GlStateManager.translate((float) x, (float) y + f1 + 0.25F * f2, (float) z);
        else GlStateManager.translate((float) x, (float) y - 0.05F, (float) z);

        if (flag || this.renderManager.options != null)
        {
            float f3 = CIEConfig.rRotate ? (((float) itemIn.getAge() + partialTicks) / 20.0F + itemIn.hoverStart) * (180F / (float) Math.PI) : 0;
            if (CIEConfig.rRotate) GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
            else if (CIEConfig.rVariationStrength > 0) GlStateManager.rotate(itemIn.hoverStart * (float) CIEConfig.rVariationStrength, 1.0F, 2.0F, 1.0F);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        return i;
    }
}