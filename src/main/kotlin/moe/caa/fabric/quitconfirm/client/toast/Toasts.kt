package moe.caa.fabric.quitconfirm.client.toast

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.toast.Toast
import net.minecraft.client.toast.ToastManager
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import java.awt.Color


sealed class BaseToast (
    protected val title: String,
    protected val message: String,
    private val keepTime: Long
) : Toast {
    companion object {
        val texture = Identifier("quitconfirm", "textures/gui/toasts.png")
    }

    override fun draw(matrices: MatrixStack, manager: ToastManager, startTime: Long): Toast.Visibility {
        drawToast(matrices, manager)
        return if (startTime >= keepTime) Toast.Visibility.HIDE else Toast.Visibility.SHOW
    }

    abstract fun drawToast(matrices: MatrixStack, manager: ToastManager)
}

class StartToast(
    startTime: Double,
    keepTime: Long
) : BaseToast("启动耗时提醒", String.format("%.2fs", startTime), keepTime) {
    override fun drawToast(matrices: MatrixStack, manager: ToastManager) {
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, 160, 32)

        DrawableHelper.drawTexture(matrices, 8, 0, 200, 0, 30, 30)

        manager.client.textRenderer.draw(matrices, title, 35.0f, 7.0f, Color.WHITE.rgb)
        manager.client.textRenderer.draw(matrices, message, 35.0f, 18.0f, Color.WHITE.rgb)
    }
}

class QuitToast(
    message: String,
    keepTime: Long
) : BaseToast("退出提醒", message, keepTime) {
    override fun drawToast(matrices: MatrixStack, manager: ToastManager) {
        RenderSystem.setShaderTexture(0, texture)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, 160, 32)

        DrawableHelper.drawTexture(matrices, 8, 0, 241, 0, 14, 30)

        manager.client.textRenderer.draw(matrices, title, 35.0f, 7.0f, Color.WHITE.rgb)
        manager.client.textRenderer.draw(matrices, message, 35.0f, 18.0f, Color.WHITE.rgb)
    }
}