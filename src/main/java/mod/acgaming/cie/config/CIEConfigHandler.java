package mod.acgaming.cie.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;

public class CIEConfigHandler
{
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final GameplaySettings GAMEPLAY_SETTINGS = new GameplaySettings(BUILDER);
    public static final RenderingSettings RENDERING_SETTINGS = new RenderingSettings(BUILDER);
    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean isCollectionTool(Item item)
    {
        String regName = item.getRegistryName().toString();
        for (String s : CIEConfigHandler.GAMEPLAY_SETTINGS.collectionTool.get())
        {
            if (regName.equals(s))
            {
                return true;
            }
        }
        return false;
    }

    public static class GameplaySettings
    {
        //public final ForgeConfigSpec.ConfigValue<Integer> entityLifespan;
        public final ForgeConfigSpec.ConfigValue<Integer> pickupDelay;
        public final ForgeConfigSpec.ConfigValue<Boolean> itemPhysics;
        public final ForgeConfigSpec.ConfigValue<Boolean> pickupAutomatic;
        public final ForgeConfigSpec.ConfigValue<Boolean> pickupCrouching;
        public final ForgeConfigSpec.ConfigValue<Boolean> combining;
        public final ForgeConfigSpec.ConfigValue<List<String>> collectionTool;
        public final ForgeConfigSpec.ConfigValue<InteractionHand> collectionToolHand;

        GameplaySettings(ForgeConfigSpec.Builder builder)
        {
            builder.push("Gameplay Settings");

            //entityLifespan = builder.comment("Time in ticks until item entities get despawned", "Default = 6000", "Vanilla = 6000").define("Entity Lifespan", 6000);
            pickupDelay = builder.comment("Pickup delay after dropping items", "Default = 20", "Vanilla = 40").define("Pickup Delay", 20);
            itemPhysics = builder.comment("Should item entities have physical aspects such as collision boxes?", "Default = true", "Vanilla = false").define("Item Physics", true);
            pickupAutomatic = builder.comment("Should item entities be picked up automatically?", "Default = false", "Vanilla = true").define("Automatic Pickup", false);
            pickupCrouching = builder.comment("Should item entities only be picked up when sneaking?", "Default = false", "Vanilla = false").define("Sneaking Pickup", false);
            combining = builder.comment("Should item entities be combined in the world?", "Default = false", "Vanilla = true").define("Combining", false);
            collectionTool = builder.comment("Tools which enable picking up items automatically when held in the off-hand").define("Collection Tool", new ArrayList<>());
            collectionToolHand = builder.comment("Tools which enable picking up items automatically when held").define("Collection Tool Hand", InteractionHand.OFF_HAND);

            builder.pop();
        }
    }

    public static class RenderingSettings
    {
        public final ForgeConfigSpec.ConfigValue<Integer> variationStrength;
        public final ForgeConfigSpec.ConfigValue<Boolean> bob;
        public final ForgeConfigSpec.ConfigValue<Boolean> rotate;

        RenderingSettings(ForgeConfigSpec.Builder builder)
        {
            builder.push("Rendering Settings");

            variationStrength = builder.comment("Strength of the angular variation", "Default = 10", "Vanilla = 0").define("Variation", 10);
            bob = builder.comment("Should item entities have a bob effect?", "Default = false", "Vanilla = true").define("Bob", false);
            rotate = builder.comment("Should item entities rotate?", "Default = false", "Vanilla = true").define("Rotate", false);

            builder.pop();
        }
    }
}