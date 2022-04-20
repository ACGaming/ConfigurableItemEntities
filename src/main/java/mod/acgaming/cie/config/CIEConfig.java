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
    @Config.Name("Disable Stacking")
    @Config.Comment("Disable stacking of item entities in the world")
    public static boolean disableStacking = true;

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