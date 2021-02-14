package moe.caa.fabric.quitconfirm.client.mixin;

import moe.caa.fabric.quitconfirm.client.screen.IButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ButtonWidget.class)
public abstract class MixinButtonWidget implements IButtonWidget {

    @Accessor
    protected abstract void setOnPress(ButtonWidget.PressAction onPress);

    @Override
    public void setTheOnPress(ButtonWidget.PressAction onPress) {
        setOnPress(onPress);
    }
}
