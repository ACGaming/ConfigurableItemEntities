package mod.acgaming.cie;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import mod.acgaming.cie.config.CIEConfigHandler;

@Mod(CIE.MODID)
public class CIE
{
    public static final String MODID = "cie";

    public CIE()
    {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CIEConfigHandler.SPEC);
        eventBus.register(CIEConfigHandler.class);
    }
}