package io.github.pltwgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import space.earlygrey.shapedrawer.ShapeDrawer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Taskbar {
    ShapeDrawer shapeDrawer;
    float outline;
    Skin skin;
    TextureAtlas textureAtlas;
    Stage stage;
    TextButton sinButton;
    TextButton cosButton;
    TextButton tanButton;
    TextButton divButton;
    TextButton sqrtButton;
    TextButton powerButton;
    TextButton logButton;
    TextButton greatButton;
    TextButton lessButton;
    TextButton absButton;
    ImageButton imgButton;
    TextField function1;
    TextField function2;
    TextField function3;
    TextField function4;
    Label text1;
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    float buttonsX;
    float buttonsY;
    float buttonsW;
    float buttonsH;
    float buttonsM;
    public Taskbar(ShapeDrawer shapeDrawer, Stage stage) {
        this.shapeDrawer = shapeDrawer;
        outline = 3;
        textureAtlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), textureAtlas);
        this.stage = stage;
        sinButton = new TextButton("sin(x)",skin);
        cosButton = new TextButton("cos(x)",skin);
        tanButton = new TextButton("tan(x)",skin);
        divButton = new TextButton("/",skin);
        sqrtButton = new TextButton("v--",skin);
        powerButton = new TextButton("x^y",skin);
        logButton = new TextButton("log(x)",skin);
        greatButton = new TextButton(">",skin);
        lessButton = new TextButton("<",skin);
        absButton = new TextButton("|x|",skin);
        imgButton = new ImageButton(skin);
        function1 = new TextField("", skin);
        function2 = new TextField("", skin);
        function3 = new TextField("", skin);
        function4 = new TextField("", skin);
        text1 = new Label("y1 = ", skin);
        stage.addActor(sinButton);
        stage.addActor(cosButton);
        stage.addActor(tanButton);
        stage.addActor(divButton);
        stage.addActor(sqrtButton);
        stage.addActor(powerButton);
        stage.addActor(logButton);
        stage.addActor(greatButton);
        stage.addActor(lessButton);
        stage.addActor(absButton);
        stage.addActor(imgButton);
        stage.addActor(function1);
        stage.addActor(function2);
        stage.addActor(function3);
        stage.addActor(function4);
        stage.addActor(text1);
        buttonsX = 950;
        buttonsY = 90;
        buttonsW = 50;
        buttonsH = 40;
        buttonsM = 10;
    }
    public void generateTaskbar(int screenWidth, int screenHeight) {
        shapeDrawer.getBatch().begin();
        //shapeDrawer.setColor(new Color(0,0,0,0.5f));
        shapeDrawer.filledRectangle(0,0,screenWidth, (float)(screenHeight/5), new Color(1,1,1,1));
        shapeDrawer.rectangle(outline/2,outline/2,screenWidth-outline, (float)(screenHeight/5), new Color(0.5f,0.5f,0.5f,1), 5f);
        /*
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.up = skin.getDrawable("up-button");
        textButtonStyle.down = skin.getDrawable("down-button");
        textButtonStyle.checked = skin.getDrawable("checked-button");
        button = new TextButton("Button1", textButtonStyle);
        button.setPosition(200, 300);
        stage.addActor(button);*/


        sinButton.setSize(buttonsW,buttonsH);
        sinButton.setPosition(buttonsX,buttonsY);

        cosButton.setSize(buttonsW,buttonsH);
        cosButton.setPosition(buttonsX+buttonsM+buttonsW,buttonsY);

        tanButton.setSize(buttonsW,buttonsH);
        tanButton.setPosition(buttonsX+(2*buttonsM)+(2*buttonsW),buttonsY);

        divButton.setSize(buttonsW,buttonsH);
        divButton.setPosition(buttonsX+(3*buttonsM)+(3*buttonsW),buttonsY);

        //sqrtButton.setStyle(new TextButtonStyle());
        sqrtButton.setSize(buttonsW,buttonsH);
        sqrtButton.setPosition(buttonsX+(4*buttonsM)+(4*buttonsW),buttonsY);

        //row 2
        powerButton.setSize(buttonsW,buttonsH);
        powerButton.setPosition(buttonsX,buttonsY-buttonsM-buttonsH);

        logButton.setSize(buttonsW,buttonsH);
        logButton.setPosition(buttonsX+buttonsM+buttonsW,buttonsY-buttonsM-buttonsH);

        greatButton.setSize(buttonsW,buttonsH);
        greatButton.setPosition(buttonsX+(2*buttonsM)+(2*buttonsW),buttonsY-buttonsM-buttonsH);

        lessButton.setSize(buttonsW,buttonsH);
        lessButton.setPosition(buttonsX+(3*buttonsM)+(3*buttonsW),buttonsY-buttonsM-buttonsH);

        absButton.setSize(buttonsW,buttonsH);
        absButton.setPosition(buttonsX+(4*buttonsM)+(4*buttonsW),buttonsY-buttonsM-buttonsH);

        imgButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("libgdx.png"))));
        //imgButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("switch_on.png"))));
        imgButton.setSize(70, 90);
        imgButton.setPosition(20, 30);

        text1.setPosition(300, 80);
        text1.setSize(50, 40);
        function1.setMessageText("Enter a function...");
        function1.setPosition(350, 80);
        function1.setSize(300, 40);

        function2.setMessageText("Enter a function...");
        function2.setPosition(300, 35);
        function2.setSize(300, 40);

        function3.setMessageText("Enter a function...");
        function3.setPosition(605, 80);
        function3.setSize(300, 40);

        function4.setMessageText("Enter a function...");
        function4.setPosition(605, 35);
        function4.setSize(300, 40);

        /*
        sinButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                sinButton.setText("touchUp");
                sinButton.setName("pressed");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                sinButton.setText("touchDown");
                return true;
            }
        });*/

        shapeDrawer.getBatch().end();
    }
}
