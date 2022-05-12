package mod.acgaming.cie.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/server/network/ServerGamePacketListenerImpl$1", priority = 1002)
public abstract class ServerGamePacketListenerImplMixin
{
    @Inject(method = "onAttack()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;disconnect(Lnet/minecraft/network/chat/Component;)V"), cancellable = true)
    public void CIE_onAttack(CallbackInfo ci)
    {
        ci.cancel();
    }
}