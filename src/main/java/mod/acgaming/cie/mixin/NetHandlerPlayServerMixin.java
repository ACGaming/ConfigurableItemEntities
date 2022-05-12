package mod.acgaming.cie.mixin;

import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketUseEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetHandlerPlayServer.class, priority = 1002)
public abstract class NetHandlerPlayServerMixin
{
    @Inject(method = "processUseEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetHandlerPlayServer;disconnect(Lnet/minecraft/util/text/ITextComponent;)V"), cancellable = true)
    private void CIE_processUseEntity(CPacketUseEntity packetIn, CallbackInfo ci)
    {
        ci.cancel();
    }
}