package xipe.ui.screens.utils;

import net.minecraft.util.Identifier;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.function.Supplier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import xipe.utils.font.FontRenderer;
import net.minecraft.util.Util;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.SharedConstants;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import java.util.function.BiFunction;
import java.util.Objects;
import net.minecraft.text.Text;
import java.util.function.Predicate;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.widget.ClickableWidget;

public class TextBox extends ClickableWidget implements Drawable, Element
{
    public static MinecraftClient mc;
    protected static FontRenderer customFont;
    public static final int field_32194 = -1;
    public static final int field_32195 = 1;
    public static final int DEFAULT_EDITABLE_COLOR = 14737632;
    private String text;
    private int maxLength;
    private int focusedTicks;
    private boolean drawsBackground;
    private boolean focusUnlocked;
    private boolean editable;
    private boolean selecting;
    private int firstCharacterIndex;
    private int selectionStart;
    private int selectionEnd;
    private int editableColor;
    private int uneditableColor;
    @Nullable
    private String suggestion;
    @Nullable
    private Consumer<String> changedListener;
    private Predicate<String> textPredicate;
    
    public TextBox(final int x, final int y, final int width, final int height, final String text) {
        this(x, y, width, height, null, text);
    }
    
    public TextBox(final int x, final int y, final int width, final int height, @Nullable final TextBox copyFrom, final String text) {
        super(x, y, width, height, (Text)Text.literal(text));
        this.text = "search box";
        this.maxLength = 32;
        this.drawsBackground = true;
        this.focusUnlocked = true;
        this.editable = true;
        this.editableColor = 14737632;
        this.uneditableColor = 7368816;
        this.textPredicate = Objects::nonNull;
        if (copyFrom != null) {
            this.setText(copyFrom.getText());
        }
    }
    
    public void setChangedListener(final Consumer<String> changedListener) {
        this.changedListener = changedListener;
    }
    
    public void setRenderTextProvider(final BiFunction<String, Integer, OrderedText> renderTextProvider) {
    }
    
    public void tick() {
        ++this.focusedTicks;
    }
    
    protected MutableText getNarrationMessage() {
        final Text text = this.getMessage();
        return Text.translatable("gui.narrate.editBox", new Object[] { text, this.text });
    }
    
    public void setText(final String text) {
        if (this.textPredicate.test(text)) {
            if (text.length() > this.maxLength) {
                this.text = text.substring(0, this.maxLength);
            }
            else {
                this.text = text;
            }
            this.setCursorToEnd();
            this.setSelectionEnd(this.selectionStart);
            this.onChanged(text);
        }
    }
    
    public String getText() {
        return this.text;
    }
    
    public String getSelectedText() {
        final int i = Math.min(this.selectionStart, this.selectionEnd);
        final int j = Math.max(this.selectionStart, this.selectionEnd);
        return this.text.substring(i, j);
    }
    
    public void setTextPredicate(final Predicate<String> textPredicate) {
        this.textPredicate = textPredicate;
    }
    
    public void write(final String text) {
        final int i = Math.min(this.selectionStart, this.selectionEnd);
        final int j = Math.max(this.selectionStart, this.selectionEnd);
        final int k = this.maxLength - this.text.length() - (i - j);
        String string = SharedConstants.stripInvalidChars(text);
        int l = string.length();
        if (k < l) {
            string = string.substring(0, k);
            l = k;
        }
        final String string2 = new StringBuilder(this.text).replace(i, j, string).toString();
        if (this.textPredicate.test(string2)) {
            this.text = string2;
            this.setSelectionStart(i + l);
            this.setSelectionEnd(this.selectionStart);
            this.onChanged(this.text);
        }
    }
    
    private void onChanged(final String newText) {
        if (this.changedListener != null) {
            this.changedListener.accept(newText);
        }
    }
    
    private void erase(final int offset) {
        if (Screen.hasControlDown()) {
            this.eraseWords(offset);
        }
        else {
            this.eraseCharacters(offset);
        }
    }
    
    public void eraseWords(final int wordOffset) {
        if (!this.text.isEmpty()) {
            if (this.selectionEnd != this.selectionStart) {
                this.write("");
            }
            else {
                this.eraseCharacters(this.getWordSkipPosition(wordOffset) - this.selectionStart);
            }
        }
    }
    
    public void eraseCharacters(final int characterOffset) {
        if (!this.text.isEmpty()) {
            if (this.selectionEnd != this.selectionStart) {
                this.write("");
            }
            else {
                final int i = this.getCursorPosWithOffset(characterOffset);
                final int j = Math.min(i, this.selectionStart);
                final int k = Math.max(i, this.selectionStart);
                if (j != k) {
                    final String string = new StringBuilder(this.text).delete(j, k).toString();
                    if (this.textPredicate.test(string)) {
                        this.text = string;
                        this.setCursor(j);
                    }
                }
            }
        }
    }
    
    public int getWordSkipPosition(final int wordOffset) {
        return this.getWordSkipPosition(wordOffset, this.getCursor());
    }
    
    private int getWordSkipPosition(final int wordOffset, final int cursorPosition) {
        return this.getWordSkipPosition(wordOffset, cursorPosition, true);
    }
    
    private int getWordSkipPosition(final int wordOffset, final int cursorPosition, final boolean skipOverSpaces) {
        int i = cursorPosition;
        final boolean bl = wordOffset < 0;
        for (int j = Math.abs(wordOffset), k = 0; k < j; ++k) {
            if (!bl) {
                final int l = this.text.length();
                i = this.text.indexOf(32, i);
                if (i == -1) {
                    i = l;
                }
                else {
                    while (skipOverSpaces && i < l && this.text.charAt(i) == ' ') {
                        ++i;
                    }
                }
            }
            else {
                while (skipOverSpaces && i > 0 && this.text.charAt(i - 1) == ' ') {
                    --i;
                }
                while (i > 0 && this.text.charAt(i - 1) != ' ') {
                    --i;
                }
            }
        }
        return i;
    }
    
    public void moveCursor(final int offset) {
        this.setCursor(this.getCursorPosWithOffset(offset));
    }
    
    private int getCursorPosWithOffset(final int offset) {
        return Util.moveCursor(this.text, this.selectionStart, offset);
    }
    
    public void setCursor(final int cursor) {
        this.setSelectionStart(cursor);
        if (!this.selecting) {
            this.setSelectionEnd(this.selectionStart);
        }
        this.onChanged(this.text);
    }
    
    public void setSelectionStart(final int cursor) {
        this.selectionStart = MathHelper.clamp(cursor, 0, this.text.length());
    }
    
    public void setCursorToStart() {
        this.setCursor(0);
    }
    
    public void setCursorToEnd() {
        this.setCursor(this.text.length());
    }
    
    public boolean keyPressed(final int keyCode, final int scanCode, final int modifiers) {
        if (!this.isActive()) {
            return false;
        }
        this.selecting = Screen.hasShiftDown();
        if (Screen.isSelectAll(keyCode)) {
            this.setCursorToEnd();
            this.setSelectionEnd(0);
            return true;
        }
        if (Screen.isCopy(keyCode)) {
            TextBox.mc.keyboard.setClipboard(this.getSelectedText());
            return true;
        }
        if (Screen.isPaste(keyCode)) {
            if (this.editable) {
                this.write(TextBox.mc.keyboard.getClipboard());
            }
            return true;
        }
        if (Screen.isCut(keyCode)) {
            TextBox.mc.keyboard.setClipboard(this.getSelectedText());
            if (this.editable) {
                this.write("");
            }
            return true;
        }
        switch (keyCode) {
            case 259: {
                if (this.editable) {
                    this.selecting = false;
                    this.erase(-1);
                    this.selecting = Screen.hasShiftDown();
                }
                return true;
            }
            default: {
                return false;
            }
            case 261: {
                if (this.editable) {
                    this.selecting = false;
                    this.erase(1);
                    this.selecting = Screen.hasShiftDown();
                }
                return true;
            }
            case 262: {
                if (Screen.hasControlDown()) {
                    this.setCursor(this.getWordSkipPosition(1));
                }
                else {
                    this.moveCursor(1);
                }
                return true;
            }
            case 263: {
                if (Screen.hasControlDown()) {
                    this.setCursor(this.getWordSkipPosition(-1));
                }
                else {
                    this.moveCursor(-1);
                }
                return true;
            }
            case 268: {
                this.setCursorToStart();
                return true;
            }
            case 269: {
                this.setCursorToEnd();
                return true;
            }
        }
    }
    
    public boolean isActive() {
        return this.isVisible() && this.isFocused() && this.isEditable();
    }
    
    public boolean charTyped(final char chr, final int modifiers) {
        if (!this.isActive()) {
            return false;
        }
        if (SharedConstants.isValidChar(chr)) {
            if (this.editable) {
                this.write(Character.toString(chr));
            }
            return true;
        }
        return false;
    }
    
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        if (!this.isVisible()) {
            return false;
        }
        final boolean bl = mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
        if (this.focusUnlocked) {
            this.setTextFieldFocused(bl);
        }
        if (this.isFocused() && bl && button == 0) {
            int i = MathHelper.floor(mouseX) - this.x;
            if (this.drawsBackground) {
                i -= 4;
            }
            final String string = TextBox.customFont.trimToWidth(this.text.substring(this.firstCharacterIndex), this.getInnerWidth());
            this.setCursor(TextBox.customFont.trimToWidth(string, i).length() + this.firstCharacterIndex);
            return true;
        }
        return false;
    }
    
    public void setTextFieldFocused(final boolean focused) {
        this.setFocused(focused);
    }
    
    public void renderButton(final MatrixStack matrices, final int mouseX, final int mouseY, final float delta) {
        if (this.isVisible()) {
            if (this.drawsBackground()) {
                final int j = this.isFocused() ? -1 : -6250336;
                fill(matrices, this.x - 1, this.y + this.height + 2, this.x + this.width + 1, this.y + this.height + 3, j);
            }
            final int j = this.editable ? this.editableColor : this.uneditableColor;
            final int k = this.selectionStart - this.firstCharacterIndex;
            int l = this.selectionEnd - this.firstCharacterIndex;
            final String string = TextBox.customFont.trimToWidth(this.text.substring(this.firstCharacterIndex), this.getInnerWidth());
            final boolean bl = k >= 0 && k <= string.length();
            final boolean bl2 = this.isFocused() && this.focusedTicks / 6 % 2 == 0 && bl;
            final int m = this.drawsBackground ? (this.x + 4) : this.x;
            final int n = this.drawsBackground ? (this.y + (this.height - 8) / 2) : this.y;
            int o = m;
            if (l > string.length()) {
                l = string.length();
            }
            if (!string.isEmpty()) {
                final String string2 = bl ? string.substring(0, k) : string;
                TextBox.customFont.drawWithShadow(matrices, string2, (float)m, (float)n, j, false);
            }
            final boolean bl3 = this.selectionStart < this.text.length() || this.text.length() >= this.getMaxLength();
            int p = o;
            if (!bl) {
                p = ((k > 0) ? (m + this.width) : m);
            }
            else if (bl3) {
                p = o - 1;
                --o;
            }
            if (!bl3 && this.suggestion != null) {
                TextBox.customFont.drawWithShadow(matrices, this.suggestion, (float)(p - 1), (float)n, -8355712, false);
            }
            if (bl2) {
                if (bl3) {
                    final int var10002 = n - 1;
                    final int var10003 = p + 1;
                    final int var10004 = n + 1;
                    Objects.requireNonNull(TextBox.customFont);
                    DrawableHelper.fill(matrices, p, var10002, var10003, var10004 + 9, -3092272);
                }
                else {
                    TextBox.customFont.drawWithShadow(matrices, "_", p + TextBox.customFont.getStringWidth(this.getText(), false), (float)n, j, false);
                }
            }
            if (l != k) {
                final int q = (int)(m + TextBox.customFont.getStringWidth(string.substring(0, l), false));
                final int var10002 = n - 1;
                final int var10003 = q - 1;
                final int var10004 = n + 1;
                Objects.requireNonNull(TextBox.customFont);
                this.drawSelectionHighlight(p, var10002, var10003, var10004 + 9);
            }
        }
    }
    
    private void drawSelectionHighlight(int x1, int y1, int x2, int y2) {
        if (x1 < x2) {
            final int j = x1;
            x1 = x2;
            x2 = j;
        }
        if (y1 < y2) {
            final int j = y1;
            y1 = y2;
            y2 = j;
        }
        if (x2 > this.x + this.width) {
            x2 = this.x + this.width;
        }
        if (x1 > this.x + this.width) {
            x1 = this.x + this.width;
        }
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader((Supplier)GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(0.0f, 0.0f, 1.0f, 1.0f);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        bufferBuilder.vertex((double)x1, (double)y2, 0.0).next();
        bufferBuilder.vertex((double)x2, (double)y2, 0.0).next();
        bufferBuilder.vertex((double)x2, (double)y1, 0.0).next();
        bufferBuilder.vertex((double)x1, (double)y1, 0.0).next();
        tessellator.draw();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }
    
    public void setMaxLength(final int maxLength) {
        this.maxLength = maxLength;
        if (this.text.length() > maxLength) {
            this.onChanged(this.text = this.text.substring(0, maxLength));
        }
    }
    
    private int getMaxLength() {
        return this.maxLength;
    }
    
    public int getCursor() {
        return this.selectionStart;
    }
    
    private boolean drawsBackground() {
        return this.drawsBackground;
    }
    
    public void setDrawsBackground(final boolean drawsBackground) {
        this.drawsBackground = drawsBackground;
    }
    
    public void setEditableColor(final int color) {
        this.editableColor = color;
    }
    
    public void setUneditableColor(final int color) {
        this.uneditableColor = color;
    }
    
    public boolean changeFocus(final boolean lookForwards) {
        return this.visible && this.editable && super.changeFocus(lookForwards);
    }
    
    public boolean isMouseOver(final double mouseX, final double mouseY) {
        return this.visible && mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
    }
    
    protected void onFocusedChanged(final boolean newFocused) {
        if (newFocused) {
            this.focusedTicks = 0;
        }
    }
    
    private boolean isEditable() {
        return this.editable;
    }
    
    public void setEditable(final boolean editable) {
        this.editable = editable;
    }
    
    public int getInnerWidth() {
        return this.drawsBackground() ? (this.width - 8) : this.width;
    }
    
    public void setSelectionEnd(final int index) {
        final int i = this.text.length();
        this.selectionEnd = MathHelper.clamp(index, 0, i);
        if (TextBox.customFont != null) {
            if (this.firstCharacterIndex > i) {
                this.firstCharacterIndex = i;
            }
            final int j = this.getInnerWidth();
            final String string = TextBox.customFont.trimToWidth(this.text.substring(this.firstCharacterIndex), j);
            final int k = string.length() + this.firstCharacterIndex;
            if (this.selectionEnd == this.firstCharacterIndex) {
                this.firstCharacterIndex -= TextBox.customFont.trimToWidth(this.text, j, true).length();
            }
            if (this.selectionEnd > k) {
                this.firstCharacterIndex += this.selectionEnd - k;
            }
            else if (this.selectionEnd <= this.firstCharacterIndex) {
                this.firstCharacterIndex -= this.firstCharacterIndex - this.selectionEnd;
            }
            this.firstCharacterIndex = MathHelper.clamp(this.firstCharacterIndex, 0, i);
        }
    }
    
    public void setFocusUnlocked(final boolean focusUnlocked) {
        this.focusUnlocked = focusUnlocked;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public void setSuggestion(@Nullable final String suggestion) {
        this.suggestion = suggestion;
    }
    
    public int getCharacterX(final int index) {
        return (int)((index > this.text.length()) ? ((float)this.x) : (this.x + TextBox.customFont.getStringWidth(this.text.substring(0, index), false)));
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public void appendNarrations(final NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, (Text)Text.translatable("narration.edit_box", new Object[] { this.getText() }));
    }
    
    static {
        TextBox.mc = MinecraftClient.getInstance();
        TextBox.customFont = new FontRenderer("Comfortaa-Regular.ttf", new Identifier("xipe", "fonts"), 20.0f);
    }
}

