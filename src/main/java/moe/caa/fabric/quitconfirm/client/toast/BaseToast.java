package moe.caa.fabric.quitconfirm.client.toast;

import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public abstract class BaseToast implements Toast {
    protected static final Identifier texture = new Identifier("quitconfirm", "textures/gui/toasts.png");
    protected final String title;
    protected final String message;
    protected final long keepTime;

    protected BaseToast(String title, String message, long keepTime) {
        this.title = title;
        this.message = message;
        this.keepTime = keepTime;
    }

    @Override
    public Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        drawToast(matrices, manager);
        if (startTime >= keepTime) return Visibility.HIDE;
        return Visibility.SHOW;
    }

    protected abstract void drawToast(MatrixStack matrices, ToastManager manager);
}
