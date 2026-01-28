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
package org.primefaces.atlantis.component;

import java.util.Collection;
import java.util.Map;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UINamingContainer;
import jakarta.faces.component.UIOutput;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.component.behavior.ClientBehaviorHolder;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.event.BehaviorEvent;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.FacesEvent;
import jakarta.faces.event.ListenerFor;
import jakarta.faces.event.PostAddToViewEvent;

import org.primefaces.component.api.PrimeClientBehaviorHolder;
import org.primefaces.component.api.UITabPanel;
import org.primefaces.util.Constants;
import org.primefaces.util.MapBuilder;

@ListenerFor(sourceClass = TabMenu.class, systemEventClass = PostAddToViewEvent.class)
public class TabMenu extends UITabPanel implements org.primefaces.component.api.Widget, ClientBehaviorHolder, PrimeClientBehaviorHolder {

    public static final String COMPONENT_TYPE = "org.primefaces.component.AtlantisTabMenu";
    public static final String COMPONENT_FAMILY = "org.primefaces.component";
    public static final String DEFAULT_RENDERER = "org.primefaces.component.AtlantisTabMenuRenderer";

    private static final String[] LEGACY_RESOURCES = new String[]{"primefaces.css", "jquery/jquery.js",
        "jquery/jquery-plugins.js", "primefaces.js"};
    private static final String[] MODERN_RESOURCES = new String[]{"components.css", "jquery/jquery.js",
        "jquery/jquery-plugins.js", "core.js"};

    public enum PropertyKeys {

        widgetVar, activeIndex, stateful;

        String toString;

        PropertyKeys(String toString) {
            this.toString = toString;
        }

        PropertyKeys() {
        }

        public String toString() {
            return ((this.toString != null) ? this.toString : super.toString());
        }
    }

    private static final Map<String, Class<? extends BehaviorEvent>> BEHAVIOR_EVENT_MAPPING = MapBuilder.<String, Class<? extends BehaviorEvent>>builder()
            .put("tabChange", TabChangeEvent.class)
            .put("tabContentLoad", TabContentLoadEvent.class)
            .build();
    private static final Collection<String> EVENT_NAMES = BEHAVIOR_EVENT_MAPPING.keySet();

    @Override
    public Map<String, Class<? extends BehaviorEvent>> getBehaviorEventMapping() {
        return BEHAVIOR_EVENT_MAPPING;
    }

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    private boolean isRequestSource(FacesContext context) {
        return this.getClientId(context).equals(context.getExternalContext().getRequestParameterMap().get(Constants.RequestParams.PARTIAL_SOURCE_PARAM));
    }

    public TabMenu() {
        setRendererType(DEFAULT_RENDERER);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public java.lang.String getWidgetVar() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.widgetVar, null);
    }

    public void setWidgetVar(java.lang.String _widgetVar) {
        getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
    }

    public int getActiveIndex() {
        return (java.lang.Integer) getStateHelper().eval(PropertyKeys.activeIndex, 0);
    }

    public void setActiveIndex(int _activeIndex) {
        getStateHelper().put(PropertyKeys.activeIndex, _activeIndex);
    }

    public boolean isStateful() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.stateful, true);
    }

    public void setStateful(boolean _stateful) {
        getStateHelper().put(PropertyKeys.stateful, _stateful);
    }

    @Override
    public String resolveWidgetVar() {
        String userWidgetVar = getWidgetVar();

        if (userWidgetVar != null) {
            return userWidgetVar;
        } else {
            FacesContext context = getFacesContext();
            String clientId = getClientId(context);
            return "widget_" + clientId.replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
        }
    }

    public Tab findTab(String tabClientId) {
        for (UIComponent component : getChildren()) {
            if (component.getClientId().equals(tabClientId)) {
                return (Tab) component;
            }
        }

        return null;
    }

    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();

        if (isRequestSource(context) && event instanceof AjaxBehaviorEvent) {
            Map<String, String> params = context.getExternalContext().getRequestParameterMap();
            String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
            String clientId = getClientId(context);
            AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;

            if (eventName.equals("tabChange")) {
                String tabClientId = params.get(clientId + "_newTab");
                int tabIndex = Integer.parseInt(params.get(clientId + "_tabindex"));
                TabChangeEvent changeEvent = new TabChangeEvent(this, behaviorEvent.getBehavior(), findTab(tabClientId), tabIndex);

                changeEvent.setPhaseId(behaviorEvent.getPhaseId());

                super.queueEvent(changeEvent);
            } else if (eventName.equals("tabContentLoad")) {
                String tabClientId = params.get(clientId + "_newTab");
                int tabIndex = Integer.parseInt(params.get(clientId + "_tabindex"));
                TabContentLoadEvent contentLoadEvent = new TabContentLoadEvent(this, behaviorEvent.getBehavior(), findTab(tabClientId), tabIndex);

                contentLoadEvent.setPhaseId(behaviorEvent.getPhaseId());

                super.queueEvent(contentLoadEvent);
            }
        } else {
            super.queueEvent(event);
        }
    }

    @Override
    public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
        if (event instanceof PostAddToViewEvent) {
            FacesContext context = getFacesContext();
            UIViewRoot root = context.getViewRoot();

            boolean isPrimeConfig;
            try {
                isPrimeConfig = Class.forName("org.primefaces.config.PrimeConfiguration") != null;
            } catch (ClassNotFoundException e) {
                isPrimeConfig = false;
            }

            String[] resources = (isPrimeConfig) ? MODERN_RESOURCES : LEGACY_RESOURCES;

            for (String res : resources) {
                UIComponent component = context.getApplication().createComponent(UIOutput.COMPONENT_TYPE);
                if (res.endsWith("css")) {
                    component.setRendererType("jakarta.faces.resource.Stylesheet");
                } else if (res.endsWith("js")) {
                    component.setRendererType("jakarta.faces.resource.Script");
                }

                component.getAttributes().put("library", "primefaces");
                component.getAttributes().put("name", res);

                root.addComponentResource(context, component);
            }
        }
    }
}
