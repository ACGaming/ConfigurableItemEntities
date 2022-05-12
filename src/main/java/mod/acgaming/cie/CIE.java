package mod.acgaming.cie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = CIE.MODID, name = CIE.NAME, version = CIE.VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = "required-after:mixinbooter;after:hardcorebuoy;after:itemphysic;after:realdrops;after:aquaacrobatics")
public class CIE
{
    public static final String MODID = "cie";
    public static final String NAME = "Configurable Item Entities (CIE)";
    public static final String VERSION = "1.12.2-1.0.3";
    public static final Logger LOGGER = LogManager.getLogger("CIE");
    public static boolean aquaacrobatics;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LOGGER.info("CIE Initialized");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (Loader.isModLoaded("aquaacrobatics"))
        {
            LOGGER.info("Aqua Acrobatics Found");
            aquaacrobatics = true;
        }
    }
}