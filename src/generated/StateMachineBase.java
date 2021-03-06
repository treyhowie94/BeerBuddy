/**
 * This class contains generated code from the Codename One Designer, DO NOT MODIFY!
 * This class is designed for subclassing that way the code generator can overwrite it
 * anytime without erasing your changes which should exist in a subclass!
 * For details about this file and how it works please read this blog post:
 * http://codenameone.blogspot.com/2010/10/ui-builder-class-how-to-actually-use.html
*/
package generated;

import com.codename1.ui.*;
import com.codename1.ui.util.*;
import com.codename1.ui.plaf.*;
import java.util.Hashtable;
import com.codename1.ui.events.*;

public abstract class StateMachineBase extends UIBuilder {
    private Container aboutToShowThisContainer;
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    /**
    * @deprecated use the version that accepts a resource as an argument instead
    
**/
    protected void initVars() {}

    protected void initVars(Resources res) {}

    public StateMachineBase(Resources res, String resPath, boolean loadTheme) {
        startApp(res, resPath, loadTheme);
    }

    public Container startApp(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("ComboBox", com.codename1.ui.ComboBox.class);
        UIBuilder.registerCustomComponent("Tabs", com.codename1.ui.Tabs.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("CheckBox", com.codename1.ui.CheckBox.class);
        UIBuilder.registerCustomComponent("MultiList", com.codename1.ui.list.MultiList.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("SpanLabel", com.codename1.components.SpanLabel.class);
        UIBuilder.registerCustomComponent("TextArea", com.codename1.ui.TextArea.class);
        UIBuilder.registerCustomComponent("Container", com.codename1.ui.Container.class);
        if(loadTheme) {
            if(res == null) {
                try {
                    if(resPath.endsWith(".res")) {
                        res = Resources.open(resPath);
                        System.out.println("Warning: you should construct the state machine without the .res extension to allow theme overlays");
                    } else {
                        res = Resources.openLayered(resPath);
                    }
                } catch(java.io.IOException err) { err.printStackTrace(); }
            }
            initTheme(res);
        }
        if(res != null) {
            setResourceFilePath(resPath);
            setResourceFile(res);
            initVars(res);
            return showForm(getFirstFormName(), null);
        } else {
            Form f = (Form)createContainer(resPath, getFirstFormName());
            initVars(fetchResourceFile());
            beforeShow(f);
            f.show();
            postShow(f);
            return f;
        }
    }

    protected String getFirstFormName() {
        return "Main";
    }

    public Container createWidget(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("ComboBox", com.codename1.ui.ComboBox.class);
        UIBuilder.registerCustomComponent("Tabs", com.codename1.ui.Tabs.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("CheckBox", com.codename1.ui.CheckBox.class);
        UIBuilder.registerCustomComponent("MultiList", com.codename1.ui.list.MultiList.class);
        UIBuilder.registerCustomComponent("Label", com.codename1.ui.Label.class);
        UIBuilder.registerCustomComponent("SpanLabel", com.codename1.components.SpanLabel.class);
        UIBuilder.registerCustomComponent("TextArea", com.codename1.ui.TextArea.class);
        UIBuilder.registerCustomComponent("Container", com.codename1.ui.Container.class);
        if(loadTheme) {
            if(res == null) {
                try {
                    res = Resources.openLayered(resPath);
                } catch(java.io.IOException err) { err.printStackTrace(); }
            }
            initTheme(res);
        }
        return createContainer(resPath, "Main");
    }

    protected void initTheme(Resources res) {
            String[] themes = res.getThemeResourceNames();
            if(themes != null && themes.length > 0) {
                UIManager.getInstance().setThemeProps(res.getTheme(themes[0]));
            }
    }

    public StateMachineBase() {
    }

    public StateMachineBase(String resPath) {
        this(null, resPath, true);
    }

    public StateMachineBase(Resources res) {
        this(res, null, true);
    }

    public StateMachineBase(String resPath, boolean loadTheme) {
        this(null, resPath, loadTheme);
    }

    public StateMachineBase(Resources res, boolean loadTheme) {
        this(res, null, loadTheme);
    }

    public com.codename1.ui.Container findContainer4(Component root) {
        return (com.codename1.ui.Container)findByName("Container4", root);
    }

    public com.codename1.ui.Container findContainer4() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container4", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container4", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer3(Component root) {
        return (com.codename1.ui.Container)findByName("Container3", root);
    }

    public com.codename1.ui.Container findContainer3() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container3", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container3", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer2(Component root) {
        return (com.codename1.ui.Container)findByName("Container2", root);
    }

    public com.codename1.ui.Container findContainer2() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container2", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container2", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findFavoriteButton(Component root) {
        return (com.codename1.ui.Button)findByName("FavoriteButton", root);
    }

    public com.codename1.ui.Button findFavoriteButton() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("FavoriteButton", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("FavoriteButton", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer1(Component root) {
        return (com.codename1.ui.Container)findByName("Container1", root);
    }

    public com.codename1.ui.Container findContainer1() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container1", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container1", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer8(Component root) {
        return (com.codename1.ui.Container)findByName("Container8", root);
    }

    public com.codename1.ui.Container findContainer8() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container8", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container8", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer7(Component root) {
        return (com.codename1.ui.Container)findByName("Container7", root);
    }

    public com.codename1.ui.Container findContainer7() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container7", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container7", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer6(Component root) {
        return (com.codename1.ui.Container)findByName("Container6", root);
    }

    public com.codename1.ui.Container findContainer6() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container6", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container6", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findButton2(Component root) {
        return (com.codename1.ui.Button)findByName("Button2", root);
    }

    public com.codename1.ui.Button findButton2() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Button2", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Button2", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer5(Component root) {
        return (com.codename1.ui.Container)findByName("Container5", root);
    }

    public com.codename1.ui.Container findContainer5() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container5", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container5", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer9(Component root) {
        return (com.codename1.ui.Container)findByName("Container9", root);
    }

    public com.codename1.ui.Container findContainer9() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container9", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container9", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findRecommendBeer(Component root) {
        return (com.codename1.ui.Button)findByName("RecommendBeer", root);
    }

    public com.codename1.ui.Button findRecommendBeer() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("RecommendBeer", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("RecommendBeer", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findButton(Component root) {
        return (com.codename1.ui.Button)findByName("Button", root);
    }

    public com.codename1.ui.Button findButton() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Button", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Button", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findRateButton(Component root) {
        return (com.codename1.ui.Button)findByName("RateButton", root);
    }

    public com.codename1.ui.Button findRateButton() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("RateButton", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("RateButton", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.list.MultiList findRecommendedBeerList(Component root) {
        return (com.codename1.ui.list.MultiList)findByName("RecommendedBeerList", root);
    }

    public com.codename1.ui.list.MultiList findRecommendedBeerList() {
        com.codename1.ui.list.MultiList cmp = (com.codename1.ui.list.MultiList)findByName("RecommendedBeerList", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.list.MultiList)findByName("RecommendedBeerList", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel1(Component root) {
        return (com.codename1.ui.Label)findByName("Label1", root);
    }

    public com.codename1.ui.Label findLabel1() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label1", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label1", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel2(Component root) {
        return (com.codename1.ui.Label)findByName("Label2", root);
    }

    public com.codename1.ui.Label findLabel2() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label2", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label2", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findAddBuddy(Component root) {
        return (com.codename1.ui.Button)findByName("AddBuddy", root);
    }

    public com.codename1.ui.Button findAddBuddy() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("AddBuddy", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("AddBuddy", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextArea findRecommendBuddySearch(Component root) {
        return (com.codename1.ui.TextArea)findByName("RecommendBuddySearch", root);
    }

    public com.codename1.ui.TextArea findRecommendBuddySearch() {
        com.codename1.ui.TextArea cmp = (com.codename1.ui.TextArea)findByName("RecommendBuddySearch", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextArea)findByName("RecommendBuddySearch", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.CheckBox findCheckBox4(Component root) {
        return (com.codename1.ui.CheckBox)findByName("CheckBox4", root);
    }

    public com.codename1.ui.CheckBox findCheckBox4() {
        com.codename1.ui.CheckBox cmp = (com.codename1.ui.CheckBox)findByName("CheckBox4", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.CheckBox)findByName("CheckBox4", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.CheckBox findCheckBox3(Component root) {
        return (com.codename1.ui.CheckBox)findByName("CheckBox3", root);
    }

    public com.codename1.ui.CheckBox findCheckBox3() {
        com.codename1.ui.CheckBox cmp = (com.codename1.ui.CheckBox)findByName("CheckBox3", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.CheckBox)findByName("CheckBox3", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findSearchButton(Component root) {
        return (com.codename1.ui.Button)findByName("SearchButton", root);
    }

    public com.codename1.ui.Button findSearchButton() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("SearchButton", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("SearchButton", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextArea findNameSUField(Component root) {
        return (com.codename1.ui.TextArea)findByName("NameSUField", root);
    }

    public com.codename1.ui.TextArea findNameSUField() {
        com.codename1.ui.TextArea cmp = (com.codename1.ui.TextArea)findByName("NameSUField", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextArea)findByName("NameSUField", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findRecommendButton(Component root) {
        return (com.codename1.ui.Button)findByName("RecommendButton", root);
    }

    public com.codename1.ui.Button findRecommendButton() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("RecommendButton", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("RecommendButton", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.list.MultiList findMyBuddyList(Component root) {
        return (com.codename1.ui.list.MultiList)findByName("MyBuddyList", root);
    }

    public com.codename1.ui.list.MultiList findMyBuddyList() {
        com.codename1.ui.list.MultiList cmp = (com.codename1.ui.list.MultiList)findByName("MyBuddyList", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.list.MultiList)findByName("MyBuddyList", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextArea findUsernameLField(Component root) {
        return (com.codename1.ui.TextArea)findByName("UsernameLField", root);
    }

    public com.codename1.ui.TextArea findUsernameLField() {
        com.codename1.ui.TextArea cmp = (com.codename1.ui.TextArea)findByName("UsernameLField", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextArea)findByName("UsernameLField", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextArea findPasswordSUField(Component root) {
        return (com.codename1.ui.TextArea)findByName("PasswordSUField", root);
    }

    public com.codename1.ui.TextArea findPasswordSUField() {
        com.codename1.ui.TextArea cmp = (com.codename1.ui.TextArea)findByName("PasswordSUField", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextArea)findByName("PasswordSUField", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findRecommendedBeer(Component root) {
        return (com.codename1.ui.Button)findByName("RecommendedBeer", root);
    }

    public com.codename1.ui.Button findRecommendedBeer() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("RecommendedBeer", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("RecommendedBeer", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.list.MultiList findNewBeerList(Component root) {
        return (com.codename1.ui.list.MultiList)findByName("NewBeerList", root);
    }

    public com.codename1.ui.list.MultiList findNewBeerList() {
        com.codename1.ui.list.MultiList cmp = (com.codename1.ui.list.MultiList)findByName("NewBeerList", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.list.MultiList)findByName("NewBeerList", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findButton1(Component root) {
        return (com.codename1.ui.Button)findByName("Button1", root);
    }

    public com.codename1.ui.Button findButton1() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("Button1", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("Button1", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.CheckBox findCheckBox1(Component root) {
        return (com.codename1.ui.CheckBox)findByName("CheckBox1", root);
    }

    public com.codename1.ui.CheckBox findCheckBox1() {
        com.codename1.ui.CheckBox cmp = (com.codename1.ui.CheckBox)findByName("CheckBox1", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.CheckBox)findByName("CheckBox1", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.CheckBox findCheckBox2(Component root) {
        return (com.codename1.ui.CheckBox)findByName("CheckBox2", root);
    }

    public com.codename1.ui.CheckBox findCheckBox2() {
        com.codename1.ui.CheckBox cmp = (com.codename1.ui.CheckBox)findByName("CheckBox2", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.CheckBox)findByName("CheckBox2", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextArea findBuddySearchArea(Component root) {
        return (com.codename1.ui.TextArea)findByName("BuddySearchArea", root);
    }

    public com.codename1.ui.TextArea findBuddySearchArea() {
        com.codename1.ui.TextArea cmp = (com.codename1.ui.TextArea)findByName("BuddySearchArea", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextArea)findByName("BuddySearchArea", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findFindNewBeer(Component root) {
        return (com.codename1.ui.Button)findByName("FindNewBeer", root);
    }

    public com.codename1.ui.Button findFindNewBeer() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("FindNewBeer", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("FindNewBeer", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.list.MultiList findBuddyList(Component root) {
        return (com.codename1.ui.list.MultiList)findByName("BuddyList", root);
    }

    public com.codename1.ui.list.MultiList findBuddyList() {
        com.codename1.ui.list.MultiList cmp = (com.codename1.ui.list.MultiList)findByName("BuddyList", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.list.MultiList)findByName("BuddyList", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextArea findBeerSearchField(Component root) {
        return (com.codename1.ui.TextArea)findByName("BeerSearchField", root);
    }

    public com.codename1.ui.TextArea findBeerSearchField() {
        com.codename1.ui.TextArea cmp = (com.codename1.ui.TextArea)findByName("BeerSearchField", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextArea)findByName("BeerSearchField", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findUsernameLabel(Component root) {
        return (com.codename1.ui.Label)findByName("UsernameLabel", root);
    }

    public com.codename1.ui.Label findUsernameLabel() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("UsernameLabel", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("UsernameLabel", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.CheckBox findCheckBox(Component root) {
        return (com.codename1.ui.CheckBox)findByName("CheckBox", root);
    }

    public com.codename1.ui.CheckBox findCheckBox() {
        com.codename1.ui.CheckBox cmp = (com.codename1.ui.CheckBox)findByName("CheckBox", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.CheckBox)findByName("CheckBox", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.list.MultiList findMyFavoriteList(Component root) {
        return (com.codename1.ui.list.MultiList)findByName("MyFavoriteList", root);
    }

    public com.codename1.ui.list.MultiList findMyFavoriteList() {
        com.codename1.ui.list.MultiList cmp = (com.codename1.ui.list.MultiList)findByName("MyFavoriteList", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.list.MultiList)findByName("MyFavoriteList", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.components.SpanLabel findSpanLabel(Component root) {
        return (com.codename1.components.SpanLabel)findByName("SpanLabel", root);
    }

    public com.codename1.components.SpanLabel findSpanLabel() {
        com.codename1.components.SpanLabel cmp = (com.codename1.components.SpanLabel)findByName("SpanLabel", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.components.SpanLabel)findByName("SpanLabel", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextArea findPasswordLField(Component root) {
        return (com.codename1.ui.TextArea)findByName("PasswordLField", root);
    }

    public com.codename1.ui.TextArea findPasswordLField() {
        com.codename1.ui.TextArea cmp = (com.codename1.ui.TextArea)findByName("PasswordLField", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextArea)findByName("PasswordLField", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer(Component root) {
        return (com.codename1.ui.Container)findByName("Container", root);
    }

    public com.codename1.ui.Container findContainer() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.TextArea findUsernameSUField(Component root) {
        return (com.codename1.ui.TextArea)findByName("UsernameSUField", root);
    }

    public com.codename1.ui.TextArea findUsernameSUField() {
        com.codename1.ui.TextArea cmp = (com.codename1.ui.TextArea)findByName("UsernameSUField", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.TextArea)findByName("UsernameSUField", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Tabs findTabs(Component root) {
        return (com.codename1.ui.Tabs)findByName("Tabs", root);
    }

    public com.codename1.ui.Tabs findTabs() {
        com.codename1.ui.Tabs cmp = (com.codename1.ui.Tabs)findByName("Tabs", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Tabs)findByName("Tabs", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.list.MultiList findRecommendBuddyList(Component root) {
        return (com.codename1.ui.list.MultiList)findByName("RecommendBuddyList", root);
    }

    public com.codename1.ui.list.MultiList findRecommendBuddyList() {
        com.codename1.ui.list.MultiList cmp = (com.codename1.ui.list.MultiList)findByName("RecommendBuddyList", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.list.MultiList)findByName("RecommendBuddyList", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.ComboBox findSearchBy(Component root) {
        return (com.codename1.ui.ComboBox)findByName("SearchBy", root);
    }

    public com.codename1.ui.ComboBox findSearchBy() {
        com.codename1.ui.ComboBox cmp = (com.codename1.ui.ComboBox)findByName("SearchBy", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.ComboBox)findByName("SearchBy", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Label findLabel(Component root) {
        return (com.codename1.ui.Label)findByName("Label", root);
    }

    public com.codename1.ui.Label findLabel() {
        com.codename1.ui.Label cmp = (com.codename1.ui.Label)findByName("Label", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Label)findByName("Label", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findSearchBuddies(Component root) {
        return (com.codename1.ui.Button)findByName("SearchBuddies", root);
    }

    public com.codename1.ui.Button findSearchBuddies() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("SearchBuddies", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("SearchBuddies", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.list.MultiList findBeerList(Component root) {
        return (com.codename1.ui.list.MultiList)findByName("BeerList", root);
    }

    public com.codename1.ui.list.MultiList findBeerList() {
        com.codename1.ui.list.MultiList cmp = (com.codename1.ui.list.MultiList)findByName("BeerList", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.list.MultiList)findByName("BeerList", aboutToShowThisContainer);
        }
        return cmp;
    }

    public static final int COMMAND_HomeMyBuddies = 6;
    public static final int COMMAND_MainSignUp = 3;
    public static final int COMMAND_NewBeerBack = 10;
    public static final int COMMAND_MainAlreadyHaveAnAccountLoginHere = 4;
    public static final int COMMAND_RecommendBeerBack = 8;
    public static final int COMMAND_RecommendedBeerBack = 9;
    public static final int COMMAND_MainForWhenServerIsDown = 7;
    public static final int COMMAND_MyBuddiesBack = 5;

    protected boolean onHomeMyBuddies() {
        return false;
    }

    protected boolean onMainSignUp() {
        return false;
    }

    protected boolean onNewBeerBack() {
        return false;
    }

    protected boolean onMainAlreadyHaveAnAccountLoginHere() {
        return false;
    }

    protected boolean onRecommendBeerBack() {
        return false;
    }

    protected boolean onRecommendedBeerBack() {
        return false;
    }

    protected boolean onMainForWhenServerIsDown() {
        return false;
    }

    protected boolean onMyBuddiesBack() {
        return false;
    }

    protected void processCommand(ActionEvent ev, Command cmd) {
        switch(cmd.getId()) {
            case COMMAND_HomeMyBuddies:
                if(onHomeMyBuddies()) {
                    ev.consume();
                    return;
                }
                break;

            case COMMAND_MainSignUp:
                if(onMainSignUp()) {
                    ev.consume();
                    return;
                }
                break;

            case COMMAND_NewBeerBack:
                if(onNewBeerBack()) {
                    ev.consume();
                    return;
                }
                break;

            case COMMAND_MainAlreadyHaveAnAccountLoginHere:
                if(onMainAlreadyHaveAnAccountLoginHere()) {
                    ev.consume();
                    return;
                }
                break;

            case COMMAND_RecommendBeerBack:
                if(onRecommendBeerBack()) {
                    ev.consume();
                    return;
                }
                break;

            case COMMAND_RecommendedBeerBack:
                if(onRecommendedBeerBack()) {
                    ev.consume();
                    return;
                }
                break;

            case COMMAND_MainForWhenServerIsDown:
                if(onMainForWhenServerIsDown()) {
                    ev.consume();
                    return;
                }
                break;

            case COMMAND_MyBuddiesBack:
                if(onMyBuddiesBack()) {
                    ev.consume();
                    return;
                }
                break;

        }
        if(ev.getComponent() != null) {
            handleComponentAction(ev.getComponent(), ev);
        }
    }

    protected void exitForm(Form f) {
        if("Login".equals(f.getName())) {
            exitLogin(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(f.getName())) {
            exitMain(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Home".equals(f.getName())) {
            exitHome(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommend Beer".equals(f.getName())) {
            exitRecommendBeer(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("New Beer".equals(f.getName())) {
            exitNewBeer(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("My Buddies".equals(f.getName())) {
            exitMyBuddies(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommended Beer".equals(f.getName())) {
            exitRecommendedBeer(f);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void exitLogin(Form f) {
    }


    protected void exitMain(Form f) {
    }


    protected void exitHome(Form f) {
    }


    protected void exitRecommendBeer(Form f) {
    }


    protected void exitNewBeer(Form f) {
    }


    protected void exitMyBuddies(Form f) {
    }


    protected void exitRecommendedBeer(Form f) {
    }

    protected void beforeShow(Form f) {
    aboutToShowThisContainer = f;
        if("Login".equals(f.getName())) {
            beforeLogin(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(f.getName())) {
            beforeMain(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Home".equals(f.getName())) {
            beforeHome(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommend Beer".equals(f.getName())) {
            beforeRecommendBeer(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("New Beer".equals(f.getName())) {
            beforeNewBeer(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("My Buddies".equals(f.getName())) {
            beforeMyBuddies(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommended Beer".equals(f.getName())) {
            beforeRecommendedBeer(f);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void beforeLogin(Form f) {
    }


    protected void beforeMain(Form f) {
    }


    protected void beforeHome(Form f) {
    }


    protected void beforeRecommendBeer(Form f) {
    }


    protected void beforeNewBeer(Form f) {
    }


    protected void beforeMyBuddies(Form f) {
    }


    protected void beforeRecommendedBeer(Form f) {
    }

    protected void beforeShowContainer(Container c) {
        aboutToShowThisContainer = c;
        if("Login".equals(c.getName())) {
            beforeContainerLogin(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(c.getName())) {
            beforeContainerMain(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Home".equals(c.getName())) {
            beforeContainerHome(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommend Beer".equals(c.getName())) {
            beforeContainerRecommendBeer(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("New Beer".equals(c.getName())) {
            beforeContainerNewBeer(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("My Buddies".equals(c.getName())) {
            beforeContainerMyBuddies(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommended Beer".equals(c.getName())) {
            beforeContainerRecommendedBeer(c);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void beforeContainerLogin(Container c) {
    }


    protected void beforeContainerMain(Container c) {
    }


    protected void beforeContainerHome(Container c) {
    }


    protected void beforeContainerRecommendBeer(Container c) {
    }


    protected void beforeContainerNewBeer(Container c) {
    }


    protected void beforeContainerMyBuddies(Container c) {
    }


    protected void beforeContainerRecommendedBeer(Container c) {
    }

    protected void postShow(Form f) {
        if("Login".equals(f.getName())) {
            postLogin(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(f.getName())) {
            postMain(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Home".equals(f.getName())) {
            postHome(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommend Beer".equals(f.getName())) {
            postRecommendBeer(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("New Beer".equals(f.getName())) {
            postNewBeer(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("My Buddies".equals(f.getName())) {
            postMyBuddies(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommended Beer".equals(f.getName())) {
            postRecommendedBeer(f);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void postLogin(Form f) {
    }


    protected void postMain(Form f) {
    }


    protected void postHome(Form f) {
    }


    protected void postRecommendBeer(Form f) {
    }


    protected void postNewBeer(Form f) {
    }


    protected void postMyBuddies(Form f) {
    }


    protected void postRecommendedBeer(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("Login".equals(c.getName())) {
            postContainerLogin(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(c.getName())) {
            postContainerMain(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Home".equals(c.getName())) {
            postContainerHome(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommend Beer".equals(c.getName())) {
            postContainerRecommendBeer(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("New Beer".equals(c.getName())) {
            postContainerNewBeer(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("My Buddies".equals(c.getName())) {
            postContainerMyBuddies(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommended Beer".equals(c.getName())) {
            postContainerRecommendedBeer(c);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void postContainerLogin(Container c) {
    }


    protected void postContainerMain(Container c) {
    }


    protected void postContainerHome(Container c) {
    }


    protected void postContainerRecommendBeer(Container c) {
    }


    protected void postContainerNewBeer(Container c) {
    }


    protected void postContainerMyBuddies(Container c) {
    }


    protected void postContainerRecommendedBeer(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("Login".equals(rootName)) {
            onCreateLogin();
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(rootName)) {
            onCreateMain();
            aboutToShowThisContainer = null;
            return;
        }

        if("Home".equals(rootName)) {
            onCreateHome();
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommend Beer".equals(rootName)) {
            onCreateRecommendBeer();
            aboutToShowThisContainer = null;
            return;
        }

        if("New Beer".equals(rootName)) {
            onCreateNewBeer();
            aboutToShowThisContainer = null;
            return;
        }

        if("My Buddies".equals(rootName)) {
            onCreateMyBuddies();
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommended Beer".equals(rootName)) {
            onCreateRecommendedBeer();
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void onCreateLogin() {
    }


    protected void onCreateMain() {
    }


    protected void onCreateHome() {
    }


    protected void onCreateRecommendBeer() {
    }


    protected void onCreateNewBeer() {
    }


    protected void onCreateMyBuddies() {
    }


    protected void onCreateRecommendedBeer() {
    }

    protected Hashtable getFormState(Form f) {
        Hashtable h = super.getFormState(f);
        if("Login".equals(f.getName())) {
            getStateLogin(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("Main".equals(f.getName())) {
            getStateMain(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("Home".equals(f.getName())) {
            getStateHome(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("Recommend Beer".equals(f.getName())) {
            getStateRecommendBeer(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("New Beer".equals(f.getName())) {
            getStateNewBeer(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("My Buddies".equals(f.getName())) {
            getStateMyBuddies(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("Recommended Beer".equals(f.getName())) {
            getStateRecommendedBeer(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

            return h;
    }


    protected void getStateLogin(Form f, Hashtable h) {
    }


    protected void getStateMain(Form f, Hashtable h) {
    }


    protected void getStateHome(Form f, Hashtable h) {
    }


    protected void getStateRecommendBeer(Form f, Hashtable h) {
    }


    protected void getStateNewBeer(Form f, Hashtable h) {
    }


    protected void getStateMyBuddies(Form f, Hashtable h) {
    }


    protected void getStateRecommendedBeer(Form f, Hashtable h) {
    }

    protected void setFormState(Form f, Hashtable state) {
        super.setFormState(f, state);
        if("Login".equals(f.getName())) {
            setStateLogin(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(f.getName())) {
            setStateMain(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("Home".equals(f.getName())) {
            setStateHome(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommend Beer".equals(f.getName())) {
            setStateRecommendBeer(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("New Beer".equals(f.getName())) {
            setStateNewBeer(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("My Buddies".equals(f.getName())) {
            setStateMyBuddies(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("Recommended Beer".equals(f.getName())) {
            setStateRecommendedBeer(f, state);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void setStateLogin(Form f, Hashtable state) {
    }


    protected void setStateMain(Form f, Hashtable state) {
    }


    protected void setStateHome(Form f, Hashtable state) {
    }


    protected void setStateRecommendBeer(Form f, Hashtable state) {
    }


    protected void setStateNewBeer(Form f, Hashtable state) {
    }


    protected void setStateMyBuddies(Form f, Hashtable state) {
    }


    protected void setStateRecommendedBeer(Form f, Hashtable state) {
    }

    protected boolean setListModel(List cmp) {
        String listName = cmp.getName();
        if("RecommendedBeerList".equals(listName)) {
            return initListModelRecommendedBeerList(cmp);
        }
        if("MyBuddyList".equals(listName)) {
            return initListModelMyBuddyList(cmp);
        }
        if("NewBeerList".equals(listName)) {
            return initListModelNewBeerList(cmp);
        }
        if("BuddyList".equals(listName)) {
            return initListModelBuddyList(cmp);
        }
        if("MyFavoriteList".equals(listName)) {
            return initListModelMyFavoriteList(cmp);
        }
        if("RecommendBuddyList".equals(listName)) {
            return initListModelRecommendBuddyList(cmp);
        }
        if("SearchBy".equals(listName)) {
            return initListModelSearchBy(cmp);
        }
        if("BeerList".equals(listName)) {
            return initListModelBeerList(cmp);
        }
        return super.setListModel(cmp);
    }

    protected boolean initListModelRecommendedBeerList(List cmp) {
        return false;
    }

    protected boolean initListModelMyBuddyList(List cmp) {
        return false;
    }

    protected boolean initListModelNewBeerList(List cmp) {
        return false;
    }

    protected boolean initListModelBuddyList(List cmp) {
        return false;
    }

    protected boolean initListModelMyFavoriteList(List cmp) {
        return false;
    }

    protected boolean initListModelRecommendBuddyList(List cmp) {
        return false;
    }

    protected boolean initListModelSearchBy(List cmp) {
        return false;
    }

    protected boolean initListModelBeerList(List cmp) {
        return false;
    }

    protected void handleComponentAction(Component c, ActionEvent event) {
        Container rootContainerAncestor = getRootAncestor(c);
        if(rootContainerAncestor == null) return;
        String rootContainerName = rootContainerAncestor.getName();
        Container leadParentContainer = c.getParent().getLeadParent();
        if(leadParentContainer != null && leadParentContainer.getClass() != Container.class) {
            c = c.getParent().getLeadParent();
        }
        if(rootContainerName == null) return;
        if(rootContainerName.equals("Login")) {
            if("UsernameLField".equals(c.getName())) {
                onLogin_UsernameLFieldAction(c, event);
                return;
            }
            if("PasswordLField".equals(c.getName())) {
                onLogin_PasswordLFieldAction(c, event);
                return;
            }
            if("Button".equals(c.getName())) {
                onLogin_ButtonAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Main")) {
            if("NameSUField".equals(c.getName())) {
                onMain_NameSUFieldAction(c, event);
                return;
            }
            if("UsernameSUField".equals(c.getName())) {
                onMain_UsernameSUFieldAction(c, event);
                return;
            }
            if("PasswordSUField".equals(c.getName())) {
                onMain_PasswordSUFieldAction(c, event);
                return;
            }
            if("Button1".equals(c.getName())) {
                onMain_Button1Action(c, event);
                return;
            }
            if("Button".equals(c.getName())) {
                onMain_ButtonAction(c, event);
                return;
            }
            if("Button2".equals(c.getName())) {
                onMain_Button2Action(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Home")) {
            if("SearchBy".equals(c.getName())) {
                onHome_SearchByAction(c, event);
                return;
            }
            if("Button".equals(c.getName())) {
                onHome_ButtonAction(c, event);
                return;
            }
            if("BeerSearchField".equals(c.getName())) {
                onHome_BeerSearchFieldAction(c, event);
                return;
            }
            if("BeerList".equals(c.getName())) {
                onHome_BeerListAction(c, event);
                return;
            }
            if("RecommendButton".equals(c.getName())) {
                onHome_RecommendButtonAction(c, event);
                return;
            }
            if("FavoriteButton".equals(c.getName())) {
                onHome_FavoriteButtonAction(c, event);
                return;
            }
            if("SearchBuddies".equals(c.getName())) {
                onHome_SearchBuddiesAction(c, event);
                return;
            }
            if("BuddySearchArea".equals(c.getName())) {
                onHome_BuddySearchAreaAction(c, event);
                return;
            }
            if("AddBuddy".equals(c.getName())) {
                onHome_AddBuddyAction(c, event);
                return;
            }
            if("BuddyList".equals(c.getName())) {
                onHome_BuddyListAction(c, event);
                return;
            }
            if("RateButton".equals(c.getName())) {
                onHome_RateButtonAction(c, event);
                return;
            }
            if("CheckBox".equals(c.getName())) {
                onHome_CheckBoxAction(c, event);
                return;
            }
            if("CheckBox1".equals(c.getName())) {
                onHome_CheckBox1Action(c, event);
                return;
            }
            if("CheckBox2".equals(c.getName())) {
                onHome_CheckBox2Action(c, event);
                return;
            }
            if("CheckBox3".equals(c.getName())) {
                onHome_CheckBox3Action(c, event);
                return;
            }
            if("CheckBox4".equals(c.getName())) {
                onHome_CheckBox4Action(c, event);
                return;
            }
            if("MyFavoriteList".equals(c.getName())) {
                onHome_MyFavoriteListAction(c, event);
                return;
            }
            if("Button2".equals(c.getName())) {
                onHome_Button2Action(c, event);
                return;
            }
            if("RecommendedBeer".equals(c.getName())) {
                onHome_RecommendedBeerAction(c, event);
                return;
            }
            if("FindNewBeer".equals(c.getName())) {
                onHome_FindNewBeerAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Recommend Beer")) {
            if("Button".equals(c.getName())) {
                onRecommendBeer_ButtonAction(c, event);
                return;
            }
            if("SearchButton".equals(c.getName())) {
                onRecommendBeer_SearchButtonAction(c, event);
                return;
            }
            if("RecommendBuddySearch".equals(c.getName())) {
                onRecommendBeer_RecommendBuddySearchAction(c, event);
                return;
            }
            if("RecommendBeer".equals(c.getName())) {
                onRecommendBeer_RecommendBeerAction(c, event);
                return;
            }
            if("RecommendBuddyList".equals(c.getName())) {
                onRecommendBeer_RecommendBuddyListAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("New Beer")) {
            if("Button".equals(c.getName())) {
                onNewBeer_ButtonAction(c, event);
                return;
            }
            if("NewBeerList".equals(c.getName())) {
                onNewBeer_NewBeerListAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("My Buddies")) {
            if("MyBuddyList".equals(c.getName())) {
                onMyBuddies_MyBuddyListAction(c, event);
                return;
            }
            if("Button".equals(c.getName())) {
                onMyBuddies_ButtonAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Recommended Beer")) {
            if("Button".equals(c.getName())) {
                onRecommendedBeer_ButtonAction(c, event);
                return;
            }
            if("RecommendedBeerList".equals(c.getName())) {
                onRecommendedBeer_RecommendedBeerListAction(c, event);
                return;
            }
        }
    }

      protected void onLogin_UsernameLFieldAction(Component c, ActionEvent event) {
      }

      protected void onLogin_PasswordLFieldAction(Component c, ActionEvent event) {
      }

      protected void onLogin_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onMain_NameSUFieldAction(Component c, ActionEvent event) {
      }

      protected void onMain_UsernameSUFieldAction(Component c, ActionEvent event) {
      }

      protected void onMain_PasswordSUFieldAction(Component c, ActionEvent event) {
      }

      protected void onMain_Button1Action(Component c, ActionEvent event) {
      }

      protected void onMain_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onMain_Button2Action(Component c, ActionEvent event) {
      }

      protected void onHome_SearchByAction(Component c, ActionEvent event) {
      }

      protected void onHome_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onHome_BeerSearchFieldAction(Component c, ActionEvent event) {
      }

      protected void onHome_BeerListAction(Component c, ActionEvent event) {
      }

      protected void onHome_RecommendButtonAction(Component c, ActionEvent event) {
      }

      protected void onHome_FavoriteButtonAction(Component c, ActionEvent event) {
      }

      protected void onHome_SearchBuddiesAction(Component c, ActionEvent event) {
      }

      protected void onHome_BuddySearchAreaAction(Component c, ActionEvent event) {
      }

      protected void onHome_AddBuddyAction(Component c, ActionEvent event) {
      }

      protected void onHome_BuddyListAction(Component c, ActionEvent event) {
      }

      protected void onHome_RateButtonAction(Component c, ActionEvent event) {
      }

      protected void onHome_CheckBoxAction(Component c, ActionEvent event) {
      }

      protected void onHome_CheckBox1Action(Component c, ActionEvent event) {
      }

      protected void onHome_CheckBox2Action(Component c, ActionEvent event) {
      }

      protected void onHome_CheckBox3Action(Component c, ActionEvent event) {
      }

      protected void onHome_CheckBox4Action(Component c, ActionEvent event) {
      }

      protected void onHome_MyFavoriteListAction(Component c, ActionEvent event) {
      }

      protected void onHome_Button2Action(Component c, ActionEvent event) {
      }

      protected void onHome_RecommendedBeerAction(Component c, ActionEvent event) {
      }

      protected void onHome_FindNewBeerAction(Component c, ActionEvent event) {
      }

      protected void onRecommendBeer_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onRecommendBeer_SearchButtonAction(Component c, ActionEvent event) {
      }

      protected void onRecommendBeer_RecommendBuddySearchAction(Component c, ActionEvent event) {
      }

      protected void onRecommendBeer_RecommendBeerAction(Component c, ActionEvent event) {
      }

      protected void onRecommendBeer_RecommendBuddyListAction(Component c, ActionEvent event) {
      }

      protected void onNewBeer_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onNewBeer_NewBeerListAction(Component c, ActionEvent event) {
      }

      protected void onMyBuddies_MyBuddyListAction(Component c, ActionEvent event) {
      }

      protected void onMyBuddies_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onRecommendedBeer_ButtonAction(Component c, ActionEvent event) {
      }

      protected void onRecommendedBeer_RecommendedBeerListAction(Component c, ActionEvent event) {
      }

}
