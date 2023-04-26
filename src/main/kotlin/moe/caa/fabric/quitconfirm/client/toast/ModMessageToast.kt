package moe.caa.fabric.quitconfirm.client.toast

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.toast.Toast
import net.minecraft.client.toast.ToastManager
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color


class ModMessageToast(
    private val title: String,
    private val message: String,
    private val keepTime: Long
) : Toast {
    override fun draw(matrices: MatrixStack, manager: ToastManager, startTime: Long): Toast.Visibility {
        RenderSystem.setShaderTexture(0, Toast.TEXTURE)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, this.width, this.height)

        manager.client.textRenderer.draw(matrices, title, 10.0f, 7.0f, Color.WHITE.rgb)
        manager.client.textRenderer.draw(matrices, message, 10.0f, 18.0f, Color.WHITE.rgb)

        return if (startTime >= keepTime) Toast.Visibility.HIDE else Toast.Visibility.SHOW
    }
}