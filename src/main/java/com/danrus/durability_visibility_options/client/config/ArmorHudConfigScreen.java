package com.danrus.durability_visibility_options.client.config;

import com.danrus.durability_visibility_options.client.ArmorHudRender;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import java.util.function.Function;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.danrus.durability_visibility_options.client.config.ModConfig.ArmorPositionHorizontal;
import com.danrus.durability_visibility_options.client.config.ModConfig.ArmorPositionVertical;
import com.danrus.durability_visibility_options.client.config.ModConfig.ArmorAlignment;
import com.danrus.durability_visibility_options.client.config.ModConfig.ArmorDisplayStyle;

public class ArmorHudConfigScreen extends Screen {

    private final Screen parent;
    private static final Identifier PREVIEW_TEXTURE = Identifier.of("durability_visibility_options", "textures/gui/preview.png");
    private int previewAreaWidth; // ширина области предпросмотра (2/3 экрана)
    private int controlsAreaWidth; // ширина области элементов управления (1/3 экрана)

    private List<Option<?>> yaclOptions = new ArrayList<>();
    private int scrollOffset = 0;
    private static final int OPTION_HEIGHT = 24;
    private static final int OPTION_PADDING = 4;

    public ArmorHudConfigScreen(Screen parent) {
        super(Text.literal("Armor HUD Config"));
        this.parent = parent;
        setupYaclOptions();
    }

    @Override
    protected void init() {
        super.init();

        // Рассчитываем ширину областей
        previewAreaWidth = (int)(this.width * 2f/3f);
        controlsAreaWidth = this.width - previewAreaWidth;

        // Кнопка "Готово" внизу области управления
        this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("gui.done"),
                        button -> close())
                .dimensions(previewAreaWidth + (controlsAreaWidth / 2) - 50, this.height - 30, 100, 20)
                .build());

        // Кнопка для прокрутки вверх
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("▲"),
                        button -> scrollUp())
                .dimensions(this.width - 30, 30, 20, 20)
                .build());

        // Кнопка для прокрутки вниз
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("▼"),
                        button -> scrollDown())
                .dimensions(this.width - 30, this.height - 50, 20, 20)
                .build());

        // Создаем YACL-подобные виджеты для каждой опции
        initOptionWidgets();
    }

    private void setupYaclOptions() {
        ModConfig config = ModConfig.get();

        // Добавляем основные опции для HUD'а брони
        yaclOptions.add(Option.createBuilder(boolean.class)
                .name(Text.literal("Show Armor Durability HUD"))
                .binding(
                        false,
                        () -> config.showArmorDurabilityHud,
                        value -> {
                            config.showArmorDurabilityHud = value;
                            ModConfig.save();
                        }
                )
                .controller(TickBoxControllerBuilder::create)
                .build());

        yaclOptions.add(Option.createBuilder(boolean.class)
                .name(Text.literal("Show Percent Symbol"))
                .binding(
                        true,
                        () -> config.showArmorDurabilityHudPercentSymbol,
                        value -> {
                            config.showArmorDurabilityHudPercentSymbol = value;
                            ModConfig.save();
                        }
                )
                .controller(TickBoxControllerBuilder::create)
                .build());

        yaclOptions.add(Option.createBuilder(int.class)
                .name(Text.literal("Show From Percent"))
                .binding(
                        100,
                        () -> config.showArmorDurabilityHudFromPercent,
                        value -> {
                            config.showArmorDurabilityHudFromPercent = value;
                            ModConfig.save();
                        }
                )
                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                        .range(0, 100)
                        .step(1))
                .build());

        yaclOptions.add(Option.createBuilder(ArmorPositionHorizontal.class)
                .name(Text.literal("Horizontal Position"))
                .binding(
                        ArmorPositionHorizontal.LEFT,
                        () -> config.armorHudPositionHorizontal,
                        value -> {
                            config.armorHudPositionHorizontal = value;
                            ModConfig.save();
                        }
                )
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(ArmorPositionHorizontal.class))
                .build());

        yaclOptions.add(Option.createBuilder(ArmorPositionVertical.class)
                .name(Text.literal("Vertical Position"))
                .binding(
                        ArmorPositionVertical.TOP,
                        () -> config.armorHudPositionVertical,
                        value -> {
                            config.armorHudPositionVertical = value;
                            ModConfig.save();
                        }
                )
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(ArmorPositionVertical.class))
                .build());

        yaclOptions.add(Option.createBuilder(ArmorAlignment.class)
                .name(Text.literal("Alignment"))
                .binding(
                        ArmorAlignment.VERTICAL,
                        () -> config.armorHudAlignment,
                        value -> {
                            config.armorHudAlignment = value;
                            ModConfig.save();
                        }
                )
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(ArmorAlignment.class))
                .build());
    }

    private void initOptionWidgets() {
        int y = 50 + scrollOffset;

        for (Option<?> option : yaclOptions) {
            // Здесь нужно будет создание виджетов для опций
            // В реальной реализации потребуется адаптер от YACL опций к виджетам Minecraft

            // Пример создания виджета для boolean опций:
            if (option.controller() instanceof TickBoxControllerBuilder) {
                boolean currentValue = (boolean) option.pendingValue();
                this.addDrawableChild(ButtonWidget.builder(
                                Text.literal((currentValue ? "☑ " : "☐ ") + option.name().getString()),
                                button -> {
                                    boolean newValue = !(boolean) option.pendingValue();
//                                    option.requestSet(newValue);
                                    button.setMessage(Text.literal((newValue ? "☑ " : "☐ ") + option.name().getString()));
                                })
                        .dimensions(previewAreaWidth + 10, y, controlsAreaWidth - 20, OPTION_HEIGHT)
                        .build());
            } else {
                // Для других типов здесь потребуются более сложные виджеты
                this.addDrawableChild(ButtonWidget.builder(
                                Text.literal(option.name().getString() + ": " + option.pendingValue().toString()),
                                button -> {
                                    // Здесь потребуется более сложная логика для разных типов опций
                                })
                        .dimensions(previewAreaWidth + 10, y, controlsAreaWidth - 20, OPTION_HEIGHT)
                        .build());
            }

            y += OPTION_HEIGHT + OPTION_PADDING;
        }
    }

    private void scrollUp() {
        scrollOffset += 20;
        if (scrollOffset > 0) scrollOffset = 0;
        clearChildren();
        init();
    }

    private void scrollDown() {
        int totalOptionsHeight = yaclOptions.size() * (OPTION_HEIGHT + OPTION_PADDING);
        int maxScroll = -Math.max(0, totalOptionsHeight - (this.height - 100));

        scrollOffset -= 20;
        if (scrollOffset < maxScroll) scrollOffset = maxScroll;

        clearChildren();
        init();
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
        ModConfig.save();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Отрисовка фона
        this.renderBackground(context, mouseX, mouseY, delta);

        // Разделитель между областями
        context.fill(previewAreaWidth, 0, previewAreaWidth + 1, this.height, 0xFFAAAAAA);

        // Область предварительного просмотра (левые 2/3)
        renderPreviewArea(context);

        // Заголовок в правой части
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.literal("Armor HUD Settings"),
                previewAreaWidth + controlsAreaWidth / 2,
                10,
                0xFFFFFF
        );

        // Отрисовка стандартных элементов (кнопки и т.д.)
        super.render(context, mouseX, mouseY, delta);
    }

    private void renderPreviewArea(DrawContext context) {
        // В будущем здесь будет отрисовка превью интерфейса
        // Пока показываем текст-заглушку

        // Опционально: отрисовка текстуры предпросмотра если она существует
        RenderSystem.setShaderTexture(0, PREVIEW_TEXTURE);
//        context.drawTexture(PREVIEW_TEXTURE, 0, 0, 0, 0, 0, previewAreaWidth, this.height, previewAreaWidth, this.height);
        context.drawTexture(
                (Function<Identifier, RenderLayer>) RenderLayer.getBlockLayers(), // функция для получения слоя рендеринга
                PREVIEW_TEXTURE,                 // идентификатор текстуры
                0, 0,                           // позиция x, y на экране
                0.0F, 0.0F,                     // позиция u, v в текстуре
                previewAreaWidth, this.height,  // ширина и высота на экране
                previewAreaWidth, this.height,  // ширина и высота региона в текстуре
                0xFFFFFF                        // цвет (белый)
        );
        // Временная заглушка
//        context.drawCenteredTextWithShadow(
//                this.textRenderer,
//                Text.literal("Preview Area (будущее превью интерфейса)"),
//                previewAreaWidth / 2,
//                this.height / 2 - 10,
//                0xFFFFFF
//        );

        // Отрисовка примера интерфейса с текущими настройками
        drawPreviewInterface(context);
    }

    private void drawPreviewInterface(DrawContext context) {
        ModConfig config = ModConfig.get();

        // Расчет позиции в зависимости от настроек
        int baseX = previewAreaWidth / 4;
        int baseY = this.height / 3;

        // Определение горизонтальной позиции
        switch (config.armorHudPositionHorizontal) {
            case LEFT -> baseX = 50;
            case CENTER -> baseX = previewAreaWidth / 4;
            case RIGHT -> baseX = previewAreaWidth / 2 - 50;
        }

        // Определение вертикальной позиции
        switch (config.armorHudPositionVertical) {
            case TOP -> baseY = 50;
            case CENTER -> baseY = this.height / 3;
            case BOTTOM -> baseY = this.height - 150;
        }

        // Отрисовка примера элементов брони и их прочности
        final int itemSize = 16;
        final int spacing = config.armorHudAlignment == ArmorAlignment.HORIZONTAL ? 20 : 0;
        final int verticalSpacing = config.armorHudAlignment == ArmorAlignment.VERTICAL ? 20 : 0;


        for (int i = 0; i < 4; i++) {
            int x = baseX + (config.armorHudAlignment == ArmorAlignment.HORIZONTAL ? i * spacing : 0);
            int y = baseY + (config.armorHudAlignment == ArmorAlignment.VERTICAL ? i * verticalSpacing : 0);

            // Рамка для визуализации элемента брони
            context.fill(x, y, x + itemSize, y + itemSize, 0xFF555555);
            context.fill(x + 1, y + 1, x + itemSize - 1, y + itemSize - 1, 0xFFAAAAAA);

            // Показывать процент прочности, если включено
            if (config.showArmorDurabilityHud) {
                String durabilityText = "75" + (config.showArmorDurabilityHudPercentSymbol ? "%" : "");
                int textColor = config.armorDurabilityHudTextColor;

                if (config.armorHudDisplayStyle == ArmorDisplayStyle.ICON_PERCENT) {
                    context.drawTextWithShadow(
                            this.textRenderer,
                            durabilityText,
                            x + itemSize + 2,
                            y + (itemSize / 2) - 4,
                            textColor
                    );
                } else {
                    context.drawTextWithShadow(
                            this.textRenderer,
                            durabilityText,
                            x - this.textRenderer.getWidth(durabilityText) - 2,
                            y + (itemSize / 2) - 4,
                            textColor
                    );
                }
            }
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}