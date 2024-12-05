package io.github.pltwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import space.earlygrey.shapedrawer.ShapeDrawer;
import com.badlogic.gdx.scenes.scene2d.Stage;

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
    TextButton showBar;
    ImageButton imgButton;
    TextField function1;
    TextField function2;
    TextField function3;
    TextField function4;
    //Button showBar;
    Label text1;
    Label text2;
    Label text3;
    Label text4;
    //button
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    float buttonsX;
    float buttonsY;
    float buttonsW;
    float buttonsH;
    float buttonsM;
    Boolean shown;
    public Taskbar(ShapeDrawer shapeDrawer, Stage stage) {
        this.shapeDrawer = shapeDrawer;
        outline = 5;
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
        showBar = new TextButton("^", skin);
        imgButton = new ImageButton(skin);
        function1 = new TextField("", skin);
        function2 = new TextField("", skin);
        function3 = new TextField("", skin);
        function4 = new TextField("", skin);
        text1 = new Label("y1 = ", skin);
        text2 = new Label("y2 = ", skin);
        text3 = new Label("y3 = ", skin);
        text4 = new Label("y4 = ", skin);
        shown = false;
        //showBar = new Button(skin, );
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
        stage.addActor(text2);
        stage.addActor(text3);
        stage.addActor(text4);
        stage.addActor(showBar);
        /*
        showBar.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //stage.
                return true;
            }
        }
        //buttonsX = 950;
        /*
        buttonsY = 83;
        buttonsW = 50;
        buttonsH = 40;
        buttonsM = 10;*/
    }
    public void draw() {
        float screenWidth = stage.getWidth();
        float screenHeight = stage.getHeight();

        shapeDrawer.getBatch().begin();
        //shapeDrawer.setColor(new Color(0,0,0,0.5f));
        /*
        float rectHeight = (float)(screenHeight/5);
        float rectWidth = (float)(screenWidth*2);
        Gdx.app.debug("Screen height: "+screenHeight , "Rect height: "+rectHeight);
        Gdx.app.debug("Screen width: "+screenWidth , "Rect width: "+rectWidth);
*/
        shapeDrawer.filledRectangle(0,0,1280, 160, new Color(1,1,1,1));
        //shapeDrawer.filledRectangle(0,100, 640, 30);
        shapeDrawer.rectangle(0,0,1280, 160, new Color(0.5f,0.5f,0.5f,1), outline);
        buttonsX = (14*screenWidth/20);
        //Gdx.app.debug(""+ screenWidth, ""+screenWidth);
        buttonsY = (screenHeight/7);
       // Gdx.app.debug(""+ screenHeight, ""+screenHeight);
        buttonsW = (screenWidth/25);
        buttonsH = (screenHeight/21);
        buttonsM = (screenWidth/45);


        /*
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.up = skin.getDrawable("up-button");
        textButtonStyle.down = skin.getDrawable("down-button");
        textButtonStyle.checked = skin.getDrawable("checked-button");
        button = new TextButton("Button1", textButtonStyle);
        button.setPosition(200, 300);
        stage.addActor(button);*/

        //shapeDrawer.setColor(Color.WHITE);

        sinButton.setSize(buttonsW,buttonsH);
        sinButton.setPosition(buttonsX,buttonsY);

        /*
        sinButton.setSize(50,40);
        sinButton.setPosition(83,40);
        */

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


        showBar.setSize(30, 20);
        showBar.setPosition((float)(screenWidth/2)-15, (float)(screenHeight*0.2222));


        imgButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("libgdx.png"))));
        //imgButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("switch_on.png"))));
        imgButton.setSize(70, 90);
        imgButton.setPosition(20, 30);

        float textX = (7*screenWidth/25);
        //Gdx.app.debug(""+ screenWidth, ""+screenWidth);
        float textY = (screenHeight/8);
        // Gdx.app.debug(""+ screenHeight, ""+screenHeight);
        float inputW = (screenWidth/7);
        float inputH = (screenHeight/23);
        float inputM = (screenWidth/37);
        //float textM = (screenWidth/50);
        float textM = 20;

        float textW = (screenWidth/47);
        float textH = (screenHeight/48);
        /*
        space between label and input: 40px
        input width: 200
        input height: 40
        y margin: 5
        x margin: 10

         */

        text1.setPosition(textX, textY);
        text1.setSize(textW, textH);
        text1.setColor(Color.BLACK);
        function1.setMessageText("Enter a function...");
        function1.setPosition(textX+textW+textM, textY);
        function1.setSize(inputW, inputH);

        text2.setPosition(textX, textY-textH-inputM);
        text2.setSize(textW, textH);
        text2.setColor(Color.BLACK);
        function2.setMessageText("Enter a function...");
        function2.setPosition(textX+textW+textM, textY-textH-inputM);
        function2.setSize(inputW, inputH);

        text3.setPosition(textX+textW+textM+inputW+inputM, textY);
        text3.setSize(textW, textH);
        text3.setColor(Color.BLACK);
        function3.setMessageText("Enter a function...");
        function3.setPosition(textX+textW+inputW+inputM+textM+textM+textW, textY);
        function3.setSize(inputW, inputH);

        text4.setPosition(textX+textW+textM+inputW+inputM, textY-textH-inputM);
        text4.setSize(textW, textH);
        text4.setColor(Color.BLACK);
        function4.setMessageText("Enter a function...");
        function4.setPosition(textX+textW+inputW+inputM+textM+textM+textW, textY-textH-inputM);
        function4.setSize(inputW, inputH);

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
        //stage.clear();
    }
}
