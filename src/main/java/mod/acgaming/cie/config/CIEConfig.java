package mod.acgaming.cie.config;

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
    public static boolean automaticPickup = false;

    @Config.Name("Gameplay: Sneaking Pickup")
    @Config.Comment({"Should item entities only be picked up when sneaking?", "Default = false, Vanilla = false"})
    public static boolean sneakingPickup = false;

    @Config.Name("Gameplay: Entity Lifespan")
    @Config.Comment({"Time in ticks until item entities get despawned", "Default = 6000, Vanilla = 6000"})
    public static int entityLifespan = 6000;

    @Config.Name("Gameplay: Combining")
    @Config.Comment({"Should item entities be combined in the world?", "Default = false, Vanilla = true"})
    public static boolean shouldCombine = false;

    @Config.Name("Rendering: Bob")
    @Config.Comment({"Should item entities have a bob effect?", "Default = false, Vanilla = true"})
    public static boolean shouldBob = false;

    @Config.Name("Rendering: Rotate")
    @Config.Comment({"Should item entities rotate?", "Default = false, Vanilla = true"})
    public static boolean shouldRotate = false;

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