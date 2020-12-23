package moe.caa.fabric.quitconfirm.client.mixin;

import moe.caa.fabric.quitconfirm.client.Config;
import moe.caa.fabric.quitconfirm.client.ConfirmScreen;
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
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient  {

    @Shadow @Final private Window window;

    @Shadow @Nullable public Screen currentScreen;

    @Shadow public abstract void openScreen(@Nullable Screen screen);

    @Shadow public abstract void scheduleStop();

    @Inject(method = "scheduleStop", at = @At("HEAD"), cancellable = true)
    private void onScheduleStop(CallbackInfo ci){
        if(Config.CONFIG.finalE && !ConfirmScreen.confirm){
            if(!(currentScreen instanceof ConfirmScreen)){
                openScreen(new ConfirmScreen(currentScreen, new TranslatableText("gui.quitconfirm.final.text"), new TranslatableText("menu.quit"), ()->{
                    ConfirmScreen.confirm = true;
                    scheduleStop();
                }));
            }
            GLFW.glfwSetWindowShouldClose(window.getHandle(), false);
            ci.cancel();
        }
    }

    @Inject(method = "stop", at = @At("HEAD"))
    private void onStop(CallbackInfo ci){
        Config.CONFIG.pushSave();
    }
}
