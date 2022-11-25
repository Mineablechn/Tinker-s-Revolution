package mineablechn.tconevo.blocks.cilent;

import com.mojang.blaze3d.matrix.MatrixStack;
import mineablechn.tconevo.tileentity.WopperContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class WopperScreen extends ContainerScreen<WopperContainer> {
    private static final ResourceLocation GUI = new ResourceLocation("tconevo:textures/gui/wopper.png");

    public WopperScreen(WopperContainer container, PlayerInventory inv, ITextComponent titleIn) {
        super(container, inv, titleIn);
        this.passEvents = false;
        this.imageHeight = 133;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        if (this.minecraft != null) {
            this.minecraft.getTextureManager().bind(GUI);
            int i = (this.width - this.imageWidth) / 2;
            int j = (this.height - this.imageHeight) / 2;
            this.blit(p_230450_1_, i, j, 0, 0, this.imageWidth, this.imageHeight);
        }
    }
}
