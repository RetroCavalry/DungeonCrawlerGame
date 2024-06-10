package com.dungeoncrawler.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SettingsScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private SelectBox<String> resolutionSelectBox;
    private SelectBox<String> aspectRatioSelectBox;
    private TextButton backButton;
    private Game game;
    private Texture backgroundTexture;
    private String currentAction;
    private int currentKeyBinding;
    private boolean isKeyBindingActive = false;

    public SettingsScreen(Game game) {
        this.game = game;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/UI/Interface/kenney-pixel/skin/skin.json"));

        backgroundTexture = new Texture(Gdx.files.absolute("assets/Tiled/Tilemaps/BeginningFields.png"));

        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        int resolution = prefs.getInteger("resolution", 800); // значение по умолчанию 800
        String aspectRatio = prefs.getString("aspectRatio", "16:9"); // значение по умолчанию "16:9"

        // Создание выпадающего списка для выбора разрешения
        resolutionSelectBox = new SelectBox<>(skin);
        resolutionSelectBox.setItems("800x600", "1024x768", "1280x720", "1920x1080");
        resolutionSelectBox.setSelected(resolution + "x" + (int)(resolution * 9f / 16f)); // установка текущего разрешения
        resolutionSelectBox.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 150);
        resolutionSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Изменение разрешения
                String[] parts = resolutionSelectBox.getSelected().split("x");
                int newWidth = Integer.parseInt(parts[0]);
                int newHeight = Integer.parseInt(parts[1]);

                // Если новое разрешение меньше текущего, переключаемся в полноэкранный режим
                if (newWidth < Gdx.graphics.getWidth() || newHeight < Gdx.graphics.getHeight()) {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                } else {
                    Gdx.graphics.setWindowedMode(newWidth, newHeight);
                }

                // Сохранение настроек
                Preferences prefs = Gdx.app.getPreferences("My Preferences");
                prefs.putInteger("resolution", newWidth);
                prefs.flush();

                // Восстановление обработчика ввода
                Gdx.input.setInputProcessor(stage);
                if (!isKeyBindingActive) {
                    Gdx.input.setInputProcessor(stage);
                }
            }
        });
        stage.addActor(resolutionSelectBox);

        // Создание выпадающего списка для выбора соотношения сторон
        aspectRatioSelectBox = new SelectBox<>(skin);
        aspectRatioSelectBox.setItems("4:3", "16:9", "16:10", "21:9"); // здесь вы можете добавить все соотношения сторон, которые хотите использовать
        aspectRatioSelectBox.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 100); // установка позиции
        aspectRatioSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Изменение соотношения сторон
                String[] parts = aspectRatioSelectBox.getSelected().split(":");
                float aspectRatio = Float.parseFloat(parts[0]) / Float.parseFloat(parts[1]);

                // Вычисление нового разрешения с учетом выбранного соотношения сторон
                int newWidth = Gdx.graphics.getWidth();
                int newHeight = (int)(newWidth / aspectRatio);

                // Если новое разрешение меньше текущего, переключаемся в полноэкранный режим
                if (newWidth < Gdx.graphics.getWidth() || newHeight < Gdx.graphics.getHeight()) {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                } else {
                    Gdx.graphics.setWindowedMode(newWidth, newHeight);
                }

                // Сохранение настроек
                Preferences prefs = Gdx.app.getPreferences("My Preferences");
                prefs.putString("aspectRatio", aspectRatioSelectBox.getSelected());
                prefs.flush();

                // Восстановление обработчика ввода
                Gdx.input.setInputProcessor(stage);

                if (!isKeyBindingActive) {
                    Gdx.input.setInputProcessor(stage);
                }
            }
        });
        stage.addActor(aspectRatioSelectBox);

        // Создание выпадающих списков для настройки управления
        String[] actions = {"Вверх", "Вниз", "Вправо", "Влево", "Атака"};
        String[] keys = {"W", "A", "S", "D", "SPACE"}; // здесь вы можете добавить все клавиши, которые хотите использовать
        for (int i = 0; i < actions.length; i++) {
            String action = actions[i];

            // Создание метки для действия
            Label actionLabel = new Label(action, skin);
            actionLabel.setPosition(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - i * 50);
            stage.addActor(actionLabel);

            // Создание выпадающего списка для выбора клавиши
            SelectBox<String> keySelectBox = new SelectBox<>(skin);
            keySelectBox.setItems(keys);
            keySelectBox.setSelected(prefs.getString(action, "W")); // установка текущей клавиши
            keySelectBox.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - i * 50);
            keySelectBox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    // Начать процесс забиндивания клавиш
                    currentAction = action;
                    isKeyBindingActive = true;

                    Gdx.input.setInputProcessor(new InputAdapter() {
                        @Override
                        public boolean keyDown(int keycode) {
                            currentKeyBinding = keycode;

                            // Сохранение настроек
                            Preferences prefs = Gdx.app.getPreferences("My Preferences");
                            prefs.putInteger(currentAction, currentKeyBinding);
                            prefs.flush();

                            isKeyBindingActive = false;
                            // Вернуться к обычному обработчику ввода
                            Gdx.input.setInputProcessor(stage);
                            return true;
                        }
                    });
                }
            });
            stage.addActor(keySelectBox);
        }

        // Создание кнопки "Назад"
        backButton = new TextButton("Назад", skin);
        backButton.setPosition(Gdx.graphics.getWidth() / 2 + 100, Gdx.graphics.getHeight() / 2 - 50);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Вернуться к предыдущему экрану
                game.setScreen(new MainMenuScreen(game)); // предполагая, что у вас есть экран MainMenuScreen
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Метод вызывается при изменении размера окна
    }

    @Override
    public void pause() {
        // Метод вызывается, когда приложение сворачивается
    }

    @Override
    public void resume() {
        // Метод вызывается, когда приложение разворачивается
    }

    @Override
    public void hide() {
        // Метод вызывается, когда текущий экран перестает быть активным
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}



