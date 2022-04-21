package mod.acgaming.cie.config;

import net.minecraft.item.Item;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.cie.CIE;

@Config(modid = CIE.MODID, name = "CIE")
public class CIEConfig
{
    @Config.Name("Gameplay: Automatic Pickup")
    @Config.Comment({"Should item entities be picked up automatically?", "Default = false, Vanilla = true"})
    public static boolean gPickupAutomatic = false;

    @Config.Name("Gameplay: Sneaking Pickup")
    @Config.Comment({"Should item entities only be picked up when sneaking?", "Default = false, Vanilla = false"})
    public static boolean gPickupSneaking = false;

    @Config.Name("Gameplay: Entity Lifespan")
    @Config.Comment({"Time in ticks until item entities get despawned", "Default = 6000, Vanilla = 6000"})
    public static int gEntityLifespan = 6000;

    @Config.Name("Gameplay: Combining")
    @Config.Comment({"Should item entities be combined in the world?", "Default = false, Vanilla = true"})
    public static boolean gCombining = false;

    @Config.Name("Gameplay: Collection Tool")
    @Config.Comment("Tools which enable picking up items automatically when held in the off-hand")
    public static String[] collectionTool = {"minecraft:bucket"};

    @Config.Name("Rendering: Bob")
    @Config.Comment({"Should item entities have a bob effect?", "Default = false, Vanilla = true"})
    public static boolean rBob = false;

    @Config.Name("Rendering: Rotate")
    @Config.Comment({"Should item entities rotate?", "Default = false, Vanilla = true"})
    public static boolean rRotate = false;

    @Config.Name("Rendering: Variation")
    @Config.Comment({"Strength of the angular variation", "Default = 10, Vanilla = 0"})
    public static int rVariationStrength = 10;

    public static boolean isCollectionTool(Item item)
    {
        String regName = item.getRegistryName().toString();
        for (String s : collectionTool)
        {
            if (regName.equals(s))
            {
                return true;
            }
        }
        return false;
    }

    @Mod.EventBusSubscriber(modid = CIE.MODID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(CIE.MODID))
            {
                ConfigManager.sync(CIE.MODID, Config.Type.INSTANCE);
            }
        }
    }
}