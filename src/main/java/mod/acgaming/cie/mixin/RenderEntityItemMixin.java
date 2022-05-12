package mod.acgaming.cie.mixin;

import java.util.Random;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import mod.acgaming.cie.config.CIEConfigHandler;
import org.spongepowered.asm.mixin.*;

@Mixin(value = ItemEntityRenderer.class, priority = 1002)
public abstract class RenderEntityItemMixin extends EntityRenderer<ItemEntity>
{
    @Mutable
    @Final
    @Shadow
    private final ItemRenderer itemRenderer;

    @Final
    @Shadow
    private final Random random = new Random();

    protected RenderEntityItemMixin(EntityRendererProvider.Context p_174008_)
    {
        super(p_174008_);
        itemRenderer = null;
    }

    @Shadow
    public boolean shouldSpreadItems()
    {
        return true;
    }

    /**
     * @author ACGaming
     */
    @Overwrite
    public void render(ItemEntity itemEntity, float p_115037_, float p_115038_, PoseStack poseStack, MultiBufferSource multiBufferSource, int p_115041_)
    {
        poseStack.pushPose();
        ItemStack itemstack = itemEntity.getItem();
        int i = itemstack.isEmpty() ? 187 : Item.getId(itemstack.getItem()) + itemstack.getDamageValue();
        this.random.setSeed(i);
        BakedModel bakedmodel = this.itemRenderer.getModel(itemstack, itemEntity.level, null, itemEntity.getId());
        boolean flag = bakedmodel.isGui3d();
        int j = this.getRenderAmount(itemstack);
        float f1 = CIEConfigHandler.RENDERING_SETTINGS.bob.get() ? Mth.sin(((float) itemEntity.getAge() + p_115038_) / 10.0F + itemEntity.bobOffs) * 0.1F + 0.1F : 0;
        float f2 = CIEConfigHandler.RENDERING_SETTINGS.bob.get() ? bakedmodel.getTransforms().getTransform(ItemTransforms.TransformType.GROUND).scale.y() : 0;

        if (!CIEConfigHandler.RENDERING_SETTINGS.rotate.get())
        {
            poseStack.translate(0.0D, f1 - 0.05F, 0.0D);
        }
        else
        {
            poseStack.translate(0.0D, f1 + 0.25F * f2, 0.0D);
            float f3 = itemEntity.getSpin(p_115038_);
            poseStack.mulPose(Vector3f.YP.rotation(f3));
        }

        if (!flag)
        {
            float f7 = -0.0F * (float) (j - 1) * 0.5F;
            float f8 = -0.0F * (float) (j - 1) * 0.5F;
            float f9 = -0.09375F * (float) (j - 1) * 0.5F;
            poseStack.translate(f7, f8, f9);
        }

        for (int k = 0; k < j; ++k)
        {
            poseStack.pushPose();
            if (k > 0)
            {
                if (flag)
                {
                    float f11 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f13 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f10 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    poseStack.translate(shouldSpreadItems() ? f11 : 0, shouldSpreadItems() ? f13 : 0, shouldSpreadItems() ? f10 : 0);
                }
                else
                {
                    float f12 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    float f14 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    poseStack.translate(shouldSpreadItems() ? f12 : 0, shouldSpreadItems() ? f14 : 0, 0.0D);
                }
            }

            this.itemRenderer.render(itemstack, ItemTransforms.TransformType.GROUND, false, poseStack, multiBufferSource, p_115041_, OverlayTexture.NO_OVERLAY, bakedmodel);
            poseStack.popPose();
            if (!flag)
            {
                poseStack.translate(0.0, 0.0, 0.09375F);
            }
        }

        poseStack.popPose();
        super.render(itemEntity, p_115037_, p_115038_, poseStack, multiBufferSource, p_115041_);
    }

    @Shadow
    protected int getRenderAmount(ItemStack stack) {return 0;}
}