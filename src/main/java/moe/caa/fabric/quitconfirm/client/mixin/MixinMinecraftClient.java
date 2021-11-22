package moe.caa.fabric.quitconfirm.client.mixin;

import moe.caa.fabric.quitconfirm.client.screen.confirm.ConfirmScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

    @Shadow
    @Nullable
    public Screen currentScreen;
    @Shadow
    @Final
    private Window window;
    @Mutable
    @Shadow
    private volatile boolean running;

    @Shadow
    public abstract void setScreen(@Nullable Screen screen);

    @Inject(method = "scheduleStop", at = @At("HEAD"), cancellable = true)
    private void onScheduleStop(CallbackInfo ci) {
        this.setScreen(new ConfirmScreen(currentScreen, new TranslatableText("gui.quitconfirm.final.text"), () -> {
            running = false;
        }));

        GLFW.glfwSetWindowShouldClose(this.window.getHandle(), false);
        ci.cancel();
    }
}
