package moe.caa.fabric.quitconfirm.client.screen.confirm.styles;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class BaseStyle extends DrawableHelper {
    private static final Identifier BACKGROUND = new Identifier("quitconfirm:textures/gui/background.png");

    public abstract Text getDisplayName();

    public abstract ButtonWidget[] generateButtons(Screen screen, ButtonWidget.PressAction onCancel, ButtonWidget.PressAction onConfirm);

    public abstract void render(Screen screen, Text title, Text message,
                                MatrixStack matrices, int mouseX, int mouseY, float delta);

    protected void renderBackground(int startX, int startY, int endX, int endY) {
        int width = endX - startX;
        int height = endY - startY;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(startX, endY, 0.0D).texture(0.0F, height / 32.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex(endX, endY, 0.0D).texture(width / 32.0F, height / 32.0F).color(64, 64, 64, 255).next();
        bufferBuilder.vertex(endX, startY, 0.0D).texture(width / 32.0F, 0).color(64, 64, 64, 255).next();
        bufferBuilder.vertex(startX, startY, 0.0D).texture(0.0F, 0).color(64, 64, 64, 255).next();
        tessellator.draw();
    }
}
