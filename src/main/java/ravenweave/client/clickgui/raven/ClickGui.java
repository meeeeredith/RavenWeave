package ravenweave.client.clickgui.raven;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ravenweave.client.Raven;
import ravenweave.client.clickgui.raven.components.CategoryComponent;
import ravenweave.client.module.Module;
import ravenweave.client.module.Module.ModuleCategory;
import ravenweave.client.module.modules.client.ClickGuiModule;
import ravenweave.client.utils.Timer;
import ravenweave.client.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ClickGui extends GuiScreen {
    private ScheduledFuture<?> sf;
    private Timer aT, aL, aE, aR;
    private final ArrayList<CategoryComponent> categoryList;
    private CategoryComponent lastCategory;
    public static int mouseX, mouseY;
    public final Terminal terminal;

    public ClickGui() {
        this.terminal = new Terminal();
        this.categoryList = new ArrayList<>();
        Module.ModuleCategory[] values;
        int categoryAmount = (values = Module.ModuleCategory.values()).length;
        int xOffSet = 5;
        int yOffSet = 5;
        for (int category = 0; category < categoryAmount; ++category) {
            Module.ModuleCategory moduleCategory = values[category];
            CategoryComponent currentModuleCategory = new CategoryComponent(moduleCategory);
            currentModuleCategory.visable = (currentModuleCategory.categoryName.isShownByDefault());
            categoryList.add(currentModuleCategory);
            currentModuleCategory.setCoords(xOffSet, yOffSet);
            xOffSet = xOffSet + 100;
            if (xOffSet > 400) {
                xOffSet = 5;
                yOffSet += 120;
            }
        }
        terminal.setLocation(380, 0);
        terminal.setSize((int) (92 * 1.5), (int) ((92 * 1.5) * 0.75));
    }

    public void initMain() {
        (this.aT = this.aE = this.aR = new Timer(500.0F)).start();
        this.sf = Raven.getExecutor().schedule(() -> (this.aL = new Timer(650.0F)).start(), 650L,
                TimeUnit.MILLISECONDS);
    }

    @Override
	public void initGui() {
        super.initGui();
        categoryList.forEach(CategoryComponent::initGui);
    }

    @Override
	public void drawScreen(int x, int y, float p) {
    	super.drawScreen(x, y, p);
    	mouseX = x; mouseY = y;

        drawRect(0, 0, this.width, this.height, (int) (this.aR.getValueFloat(0.0F, 0.7F, 2) * 255.0F) << 24);

        if (!ClickGuiModule.clean.isToggled()) {
            int quarterScreenHeight = this.height / 4;
            int halfScreenWidth = this.width / 2;
            int w_c = 30 - this.aT.getValueInt(0, 30, 3);
            if (ClickGuiModule.preset.getMode() == ClickGuiModule.Preset.B4) {
                this.drawCenteredString(this.fontRendererObj, "r", halfScreenWidth + 1 - w_c, quarterScreenHeight - 25, Utils.Client.rainbowDraw(2L, 1500L));
                this.drawCenteredString(this.fontRendererObj, "a", halfScreenWidth - w_c, quarterScreenHeight - 15, Utils.Client.rainbowDraw(2L, 1200L));
                this.drawCenteredString(this.fontRendererObj, "v", halfScreenWidth - w_c, quarterScreenHeight - 5, Utils.Client.rainbowDraw(2L, 900L));
                this.drawCenteredString(this.fontRendererObj, "e", halfScreenWidth - w_c, quarterScreenHeight + 5, Utils.Client.rainbowDraw(2L, 600L));
                this.drawCenteredString(this.fontRendererObj, "n", halfScreenWidth - w_c, quarterScreenHeight + 15, Utils.Client.rainbowDraw(2L, 300L));
                this.drawCenteredString(this.fontRendererObj, "b4", halfScreenWidth + 1 + w_c, quarterScreenHeight + 30, Utils.Client.rainbowDraw(2L, 0L));
                this.drawVerticalLine(halfScreenWidth - 10 - w_c, quarterScreenHeight - 30, quarterScreenHeight + 43, -1);
                this.drawVerticalLine(halfScreenWidth + 10 + w_c, quarterScreenHeight - 30, quarterScreenHeight + 43, -1);
                this.drawVerticalLine(halfScreenWidth - 10 - w_c, quarterScreenHeight - 30, quarterScreenHeight + 38, -1);
                this.drawVerticalLine(halfScreenWidth + 10 + w_c, quarterScreenHeight - 30, quarterScreenHeight + 38, -1);
            } else {
                this.drawCenteredString(this.fontRendererObj, "r", (halfScreenWidth + 1) - w_c, quarterScreenHeight - 25,
                        Utils.Client.rainbowDraw(2L, 1500L));
                this.drawCenteredString(this.fontRendererObj, "a", halfScreenWidth - w_c, quarterScreenHeight - 15,
                        Utils.Client.rainbowDraw(2L, 1200L));
                this.drawCenteredString(this.fontRendererObj, "v", halfScreenWidth - w_c, quarterScreenHeight - 5,
                        Utils.Client.rainbowDraw(2L, 900L));
                this.drawCenteredString(this.fontRendererObj, "e", halfScreenWidth - w_c, quarterScreenHeight + 5,
                        Utils.Client.rainbowDraw(2L, 600L));
                this.drawCenteredString(this.fontRendererObj, "n", halfScreenWidth - w_c, quarterScreenHeight + 15,
                        Utils.Client.rainbowDraw(2L, 300L));
                this.drawCenteredString(this.fontRendererObj, "b", halfScreenWidth + 1 + w_c, quarterScreenHeight + 25,
                        Utils.Client.rainbowDraw(2L, 0L));
                this.drawCenteredString(this.fontRendererObj, "+ +", halfScreenWidth + 1 + w_c, quarterScreenHeight + 30,
                        Utils.Client.rainbowDraw(2L, 0L));

                this.drawVerticalLine(halfScreenWidth - 10 - w_c, quarterScreenHeight - 30, quarterScreenHeight + 38,
                        Utils.Client.customDraw(0));

                this.drawVerticalLine(halfScreenWidth + 10 + w_c, quarterScreenHeight - 30, quarterScreenHeight + 38,
                        Utils.Client.customDraw(0));
            }



            int animationProggress;
            if (this.aL != null) {
                animationProggress = this.aL.getValueInt(0, 20, 2);
                if (ClickGuiModule.preset.getMode() == ClickGuiModule.Preset.B4) {
                    this.drawHorizontalLine(halfScreenWidth - 10, halfScreenWidth - 10 + animationProggress, quarterScreenHeight - 29, -1);
                    this.drawHorizontalLine(halfScreenWidth + 10, halfScreenWidth + 10 - animationProggress, quarterScreenHeight + 42, -1);
                } else {
                    this.drawHorizontalLine(halfScreenWidth - 10, (halfScreenWidth - 10) + animationProggress,
                            quarterScreenHeight - 29, Utils.Client.customDraw(0));
                    this.drawHorizontalLine(halfScreenWidth + 10, (halfScreenWidth + 10) - animationProggress,
                            quarterScreenHeight + 38, Utils.Client.customDraw(0));
                }
            }

            GL11.glColor4f(1f, 1f, 1f, 1f);
            GuiInventory.drawEntityOnScreen((this.width + 15) - this.aE.getValueInt(0, 40, 2),
                    this.height - 19 - this.fontRendererObj.FONT_HEIGHT, 40, (float) (this.width - 25 - x),
                    (float) (this.height - 50 - y), this.mc.thePlayer);
        }

        visableCategoryList().forEach(category -> category.draw(x, y));

        terminal.update(x, y);
        terminal.draw();
    }

    @Override
	public void mouseClicked(int x, int y, int mouseButton) {
        terminal.mouseDown(x, y, mouseButton);
        for(CategoryComponent category : visableCategoryList())
            if(category.mouseDown(x, y, mouseButton)) {
                lastCategory = category;
                return;
            }
    }

    @Override
	public void mouseReleased(int x, int y, int mouseButton) {
        terminal.mouseReleased(x, y, mouseButton);
        if (terminal.overPosition(x, y))
            return;

        visableCategoryList().forEach(category -> category.mouseReleased(x, y, mouseButton));

        if (Raven.clientConfig != null)
			Raven.clientConfig.saveConfig();
    }

    @Override
	public void keyTyped(char t, int k) {
        terminal.keyTyped(t, k);
        if(lastCategory != null)
            lastCategory.keyTyped(t, k);
        if (k == 1) {
            Raven.mc.displayGuiScreen(null);
            Raven.configManager.save();
            Raven.clientConfig.saveConfig();
        }
    }

    @Override
	public void handleMouseInput() {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel() * 5;
        visableCategoryList().forEach(category -> {
            if(category.isMouseOver(mouseX, mouseY))
                category.scroll(i);
            });
    }

    @Override
	public void onGuiClosed() {
        visableCategoryList().forEach(CategoryComponent::guiClosed);
        Raven.configManager.save();
        Raven.clientConfig.saveConfig();
    }

    @Override
	public boolean doesGuiPauseGame() {
        return false;
    }

    public ArrayList<CategoryComponent> getCategoryList() {
        return categoryList;
    }

    public CategoryComponent getCategoryComponent(ModuleCategory mCat) {
        for (CategoryComponent cc : categoryList)
			if (cc.categoryName == mCat)
                return cc;
        return null;
    }

    public ArrayList<CategoryComponent> visableCategoryList() {
        ArrayList<CategoryComponent> newList = (ArrayList<CategoryComponent>) categoryList.clone();
        newList.removeIf(obj -> !obj.visable);
        return newList;
    }

    public void resetSort() {
        int xOffSet = 5;
        int yOffSet = 5;
        for(CategoryComponent category : categoryList) {
            category.setCoords(xOffSet, yOffSet);
            xOffSet = xOffSet + 100;
            if (xOffSet > 400) {
                xOffSet = 5;
                yOffSet += 120;
            }
        }

    }
}
