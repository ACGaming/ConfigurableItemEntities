package mod.acgaming.cie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = CIE.MODID, name = CIE.NAME, version = CIE.VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = "required-after:mixinbooter;after:realdrops")
public class CIE
{
    public static final String MODID = "cie";
    public static final String NAME = "Configurable Item Entities (CIE)";
    public static final String VERSION = "1.12.2-1.0.0";
    public static final Logger LOGGER = LogManager.getLogger("CIE");

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LOGGER.info("CIE Initialized");
    }
}