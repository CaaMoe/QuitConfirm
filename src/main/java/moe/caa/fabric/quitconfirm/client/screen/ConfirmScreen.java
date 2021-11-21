package moe.caa.fabric.quitconfirm.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class ConfirmScreen extends Screen {
    private static final Timer TIMER = new Timer("QuitConfirm");

    private static final Identifier WINDOW_TEXTURE = new Identifier("quitconfirm:textures/gui/window/window.png");
    private static final Identifier BACKGROUND = new Identifier("quitconfirm:textures/gui/background.png");
    // 窗口宽度
    private static final int windowWidth = 252;
    // 窗口长度
    private static final int windowHeight = 140;
    // 按钮旁边距
    private static final int buttonLRMargin = 20;
    // 按钮下边距
    private static final int buttonBMargin = 20;
    // 按钮宽度
    private static final int buttonWidth = 100;
    // 按钮长度
    private static final int buttonHeight = 20;
    // 信息文本下边距
    private static final int messageBMargin = 100;

    private final Screen parentScreen;
    private final Text message;
    private final Runnable confirm;

    public ConfirmScreen(Screen parentScreen, Text message, Runnable confirm) {
        super(new TranslatableText("gui.quitconfirm.window.title"));
        this.parentScreen = parentScreen;
        this.message = message;
        this.confirm = confirm;
    }

    @Override
    protected void init() {
        initButton();
    }

    private void initButton(){
        ButtonWidget cancelButtonWidget = new ButtonWidget(
                (this.width - windowWidth) / 2 + buttonLRMargin,
                (this.height - windowHeight) / 2 + windowHeight - buttonBMargin - buttonHeight,
                buttonWidth, buttonHeight, new TranslatableText("gui.cancel"), (buttonWidget) -> {
                    this.client.setScreen(parentScreen);
        });

        ButtonWidget confirmButtonWidget = new ButtonWidget(
                (this.width - windowWidth) / 2 + windowWidth - buttonWidth - buttonLRMargin,
                (this.height - windowHeight) / 2 + windowHeight - buttonBMargin - buttonHeight,
                buttonWidth, buttonHeight, new TranslatableText("gui.ok"), (buttonWidget) -> {
                    confirm.run();
        });
        cancelButtonWidget.active = false;
        confirmButtonWidget.active = false;

        TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                cancelButtonWidget.active = true;
                confirmButtonWidget.active = true;
            }
        }, 1000);

        this.addDrawableChild(cancelButtonWidget);
        this.addDrawableChild(confirmButtonWidget);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawBackground(matrices);
        drawWindow(matrices, (this.width - windowWidth) / 2, (this.height - windowHeight) / 2);
        drawMessage(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }

    private void drawBackground(MatrixStack matrices) {
        if (this.client.world != null) {
            this.fillGradient(matrices, 0, 0, this.width, this.height, -1072689136, -804253680);
        } else {
            this.renderBackground(0, 0, width, height);
            DrawableHelper.fill(matrices, 0, 0, this.width, this.height, new Color(16, 16, 16, 179).getRGB());
        }
    }

    private void drawWindow(MatrixStack matrices, int x, int y){
        renderBackground(x, y, x + windowWidth, y + windowHeight);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WINDOW_TEXTURE);
        this.drawTexture(matrices, x,y, 0, 0, 252, 140);
        this.textRenderer.draw(matrices, this.getTitle(), (float)(x + 8), (float)(y + 6), 4210752);
    }

    private void drawMessage(MatrixStack matrices){
        drawCenteredText(matrices, this.client.textRenderer, message, this.width / 2, (this.height - windowHeight) / 2 + windowHeight - messageBMargin, 10526880);
    }

    private void renderBackground(int startX, int startY, int endX, int endY){
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
