/*
   Copyright 2009-2022 PrimeTek.

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.primefaces.atlantis.view;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;

@Named
@SessionScoped
public class GuestPreferences implements Serializable {

    private String theme = "steel";
        
    private String layout = "steel";

    private String inputStyle = "outlined";

    private String menuMode = "";

    private String menuColor = "";
    
    private boolean orientationRTL;

    private List<Layout> layouts;

    private List<Theme> themes;

    @PostConstruct
    public void init() {
        themes = new ArrayList<>();
        themes.add(new Theme("Blue", "blue", "#3192e1"));
        themes.add(new Theme("Cyan", "cyan", "#5e91a9"));
        themes.add(new Theme("Green", "green", "#5ea980"));
        themes.add(new Theme("Orange", "orange", "#ff9c59"));
        themes.add(new Theme("Pink", "pink", "#e42a7b"));
        themes.add(new Theme("Purple", "purple", "#985edb"));
        themes.add(new Theme("Steel", "steel", "#58799f"));
        themes.add(new Theme("Turquoise", "turquoise", "#47c5d4"));

        layouts = new ArrayList<>();
        layouts.add(new Layout("Blue", "blue", "#3192e1"));
        layouts.add(new Layout("Cyan", "cyan", "#5e91a9"));
        layouts.add(new Layout("Dark", "dark", "#545b61"));
        layouts.add(new Layout("Green", "green", "#5ea980"));
        layouts.add(new Layout("Orange", "orange", "#ff9c59"));
        layouts.add(new Layout("Pink", "pink", "#e42a7b"));
        layouts.add(new Layout("Purple", "purple", "#985edb"));
        layouts.add(new Layout("Steel", "steel", "#58799f"));
        layouts.add(new Layout("Turquoise", "turquoise", "#47c5d4"));
    }
    
	public String getTheme() {		
		return theme;
	}
    
	public void setTheme(String theme) {
		this.theme = theme;
	}
    
    public String getLayout() {
        return this.layout;
    }
    
    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getMenuMode() {
        return menuMode;
    }

    public void setMenuMode(String menuMode) {
        this.menuMode = menuMode;
    }

    public String getMenuColor() {
        return menuColor;
    }

    public void setMenuColor(String menuColor) {
        this.menuColor = menuColor;
    }

    public boolean isOrientationRTL() {
        return orientationRTL;
    }

    public void setOrientationRTL(boolean orientationRTL) {
        this.orientationRTL = orientationRTL;
    }

    public String getInputStyle() {
        return inputStyle;
    }

    public void setInputStyle(String inputStyle) {
        this.inputStyle = inputStyle;
    }

    public String getInputStyleClass() {
        return this.inputStyle.equals("filled") ? "ui-input-filled" : "";
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public class Theme {
        String name;
        String file;
        String color;

        public Theme(String name, String file, String color) {
            this.name = name;
            this.file = file;
            this.color = color;
        }

        public String getName() {
            return this.name;
        }

        public String getFile() {
            return this.file;
        }

        public String getColor() {
            return this.color;
        }
    }

    public class Layout {
        String name;
        String file;
        String color;

        public Layout(String name, String file, String color) {
            this.name = name;
            this.file = file;
            this.color = color;
        }

        public String getName() {
            return this.name;
        }

        public String getFile() {
            return this.file;
        }

        public String getColor() {
            return this.color;
        }
    }
}
