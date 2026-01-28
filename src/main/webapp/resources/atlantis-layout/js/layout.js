/** 
 * PrimeFaces Atlantis Layout
 */
PrimeFaces.widget.Atlantis = PrimeFaces.widget.BaseWidget.extend({
    
    init: function(cfg) {
        this._super(cfg);
        this.wrapper = $(document.body).children('.layout-wrapper');
        this.sidebar = this.wrapper.children('.layout-sidebar');
        this.tabMenu = this.jq;
        this.tabMenuNav = this.tabMenu.children('.layout-tabmenu-nav');
        this.tabMenuNavItems = this.tabMenuNav.children('li');
        this.tabMenuNavLinks = this.tabMenuNav.find('a');
        this.topbar = this.wrapper.children('.topbar');
        this.topbarMenu = this.topbar.children('.topbar-menu');
        this.topbarItems = this.topbarMenu.children('li');
        this.topbarLinks = this.topbarItems.children('a');
        this.topbarMenuButton = $('#topbar-menu-button');
        this.configButton = $('#layout-config-button');
        this.configurator = this.wrapper.children('.layout-config');
        this.configClicked = false;
        this.menuActive = false;
        this.sidebarClick = false;
        this.topbarLinkClick = false;
        this.topbarMenuClick = false;

        this._bindEvents();

        if(this.cfg.stateful) {
            this.restoreTabState();
        }
        else if(this.cfg.activeIndex) {
            var active = this.tabMenuNavItems.eq(parseInt(this.cfg.activeIndex));
            this.openMenu(active.children('a'));
        }
    },
    
    _bindEvents: function() {
        var $this = this;

        this.tabMenu.find('.menu-button').off('click.menu').on('click.menu', function(e) {
            $this.wrapper.removeClass('layout-wrapper-menu-active');
            if(!$this.isOverlayMenu()) {
                $(window).trigger('resize');
            }

            e.preventDefault();
        });
        
        this.tabMenuNavLinks.off('click.menu').on('click.menu', function(e) {
            var navlink = $(this).parent();
            if(navlink.hasClass('active-item')) {
                navlink.removeClass('active-item')
                $this.hideMenu();
            }
            else {
                var link = $(this);
                link.parent().siblings('.active-item').removeClass('active-item');

                $this.fireTabContentLoadEvent(link.parent());

                $this.openMenu(link);

                $this.fireTabChangeEvent(link.parent());
            }

            if(!$this.isOverlayMenu() && $this.isDesktop()) {
                $(window).trigger('resize');
            }
            $(this).children('.layout-tabmenu-tooltip').hide();

            e.preventDefault();
        });
        
        if(!this.isIOS()) {
            this.tabMenuNavLinks.off('mouseenter.menu mouseleave.menu').on('mouseenter.menu', function(e) {
                var link = $(this);
                if(!link.parent().hasClass('active-item')||!$this.wrapper.hasClass('layout-wrapper-menu-active')) {
                    link.children('.layout-tabmenu-tooltip').show();
                }
            })
            .on('mouseleave.menu', function(e) {
                $(this).children('.layout-tabmenu-tooltip').hide();
            });

            this.tabMenuNavLinks.find('.layout-tabmenu-tooltip').off('mouseenter.menu').on('mouseenter.menu', function(e) {
                $(this).hide();
            });
        }

        this.topbarMenuButton.off('click.topbar').on('click.topbar', function(e) {
            $this.topbarMenuClick = true;
            $this.topbarMenu.find('ul').removeClass('fadeInDown fadeOutUp');

            if($this.topbarMenu.hasClass('topbar-menu-visible')) {
                $this.topbarMenu.addClass('fadeOutUp');
                
                setTimeout(function() {
                    $this.topbarMenu.removeClass('fadeOutUp topbar-menu-visible');
                },300);
            }
            else {
                $this.topbarMenu.addClass('topbar-menu-visible fadeInDown');
            }
                        
            e.preventDefault();
        });
                
        this.topbarLinks.off('click.topbar').on('click.topbar', function(e) {
            var link = $(this),
            item = link.parent(),
            submenu = link.next();
            
            $this.topbarLinkClick = true;

            item.siblings('.active-topmenuitem').removeClass('active-topmenuitem');

            if($this.isDesktop()) {
                if(submenu.length) {
                    if(item.hasClass('active-topmenuitem')) {
                        submenu.addClass('fadeOutUp');
                        
                        setTimeout(function() {
                            item.removeClass('active-topmenuitem'),
                            submenu.removeClass('fadeOutUp');
                        },500);
                    }
                    else {
                        item.addClass('active-topmenuitem');
                        submenu.addClass('fadeInDown');
                    }
                }
            }
            else {
                item.children('ul').removeClass('fadeInDown fadeOutUp');
                item.toggleClass('active-topmenuitem');
            }   
                        
            e.preventDefault();   
        });
        
        this.topbarMenu.children('.search-item').off('click.topbar').on('click.topbar', function(e) {
            $this.topbarLinkClick = true;
        });
        
        this.sidebar.off('click.sidebar').on('click.sidebar', function(e) {
            $this.sidebarClick = true;
        });

        this.configButton.off('click.configbutton').on('click.configbutton', function(e) {
            $this.configurator.toggleClass('layout-config-active');
            $this.configClicked = true;
        });

        this.configurator.off('click.config').on('click.config', function() {
            $this.configClicked = true;
        });

        $(document.body).off('click.layoutBody').on('click.layoutBody', function() {
            if(!$this.topbarMenuClick && !$this.topbarLinkClick) {
                $this.topbarItems.filter('.active-topmenuitem').removeClass('active-topmenuitem');
                $this.topbarMenu.removeClass('topbar-menu-visible');
            }
            
            if(!$this.sidebarClick && ($this.isOverlayMenu() || !$this.isDesktop())) {
                $this.wrapper.removeClass('layout-wrapper-menu-active layout-wrapper-menu-active-restore');
            }

            if (!$this.configClicked && $this.configurator.hasClass('layout-config-active')) {
                $this.configurator.removeClass('layout-config-active');
            }

            $this.topbarLinkClick = false;
            $this.topbarMenuClick = false;
            $this.sidebarClick = false;
            $this.configClicked = false;
        });
    },
    
    openMenu: function(link, restore) {
        this.sidebar.css('overflow','hidden');
        var parent = link.parent();
        parent.addClass('active-item');

        this.wrapper.addClass('layout-wrapper-menu-active');
        if(restore) {
            this.wrapper.addClass('layout-wrapper-menu-active-restore');
        }

        this.tabMenu.find('.layout-tabmenu-content').removeClass('layout-tabmenu-content-active').
                eq(parent.index()).addClass('layout-tabmenu-content-active');

        if(this.cfg.stateful) {
            this.saveTabState(parent.attr('id'));
        }
    },

    hideMenu: function() {
        this.sidebar.css('overflow','visible');
        this.wrapper.removeClass('layout-wrapper-menu-active layout-wrapper-menu-active-restore');
        if(!this.isOverlayMenu() && this.isDesktop()) {
            $(window).trigger('resize');
        }
    },

    fireTabChangeEvent: function(tab) {
        if (this.cfg.behaviors && this.cfg.behaviors['tabChange']) {
            var ext = {
                params: [
                    {name: this.id + '_newTab', value: tab.attr('id')},
                    {name: this.id + '_tabindex', value: tab.index()}
                ]
            };

            this.cfg.behaviors['tabChange'].call(this, ext);
        }
    },

    fireTabContentLoadEvent: function(tab) {
        if (this.cfg.behaviors && this.cfg.behaviors['tabContentLoad']) {
            var ext = {
                params: [
                    {name: this.id + '_newTab', value: tab.attr('id')},
                    {name: this.id + '_tabindex', value: tab.index()}
                ]
            };

            this.cfg.behaviors['tabContentLoad'].call(this, ext);
        }
    },

    isTablet: function() {
        var width = window.innerWidth;
        return width <= 1024 && width > 640;
    },

    isDesktop: function() {
        return window.innerWidth > 1024;
    },

    isMobile: function() {
        return window.innerWidth <= 640;
    },
    
    isOverlayMenu: function() {
        return this.wrapper.hasClass('layout-overlay-menu');
    },
    
    isIOS: function(e) {
        return ((navigator.userAgent.match(/iPhone/i)) || (navigator.userAgent.match(/iPod/i)) || (navigator.userAgent.match(/iPad/i)));
    },
    
    saveTabState: function(id) {
        $.cookie('atlantis_expandedtab', id, {path: '/'});
    },
    
    clearTabState: function() {
        $.removeCookie('atlantis_expandedtab', {path: '/'});
    },
    
    restoreTabState: function() {
        this.expandedTab = $.cookie('atlantis_expandedtab');
        if (this.expandedTab) {
            var tab = $("#" + this.expandedTab.replace(/:/g, "\\:"));
            this.fireTabContentLoadEvent(tab);
            this.openMenu(tab.children('a'), true);
        }
        else if(this.cfg.activeIndex) {
            var active = this.tabMenuNavItems.eq(parseInt(this.cfg.activeIndex));
            this.fireTabContentLoadEvent(active);
            this.openMenu(active.children('a'), true);
        }
    }
    
});

/** 
 * PrimeFaces AtlantisMenu Component
 */
PrimeFaces.widget.AtlantisMenu = PrimeFaces.widget.BaseWidget.extend({
    
    init: function(cfg) {
        this._super(cfg);
        this.menulinks = this.jq.find('a');
        this.expandedMenuitems = this.expandedMenuitems || [];     
        this.menuActive = false;
        this.topbarLinkClick = false;
        this.topbarMenuClick = false;

        this._bindEvents();
        this.restoreMenuState();
    },
    
    _bindEvents: function() {
        var $this = this;
        
        this.menulinks.off('click').on('click', function(e) {
            var link = $(this),
            item = link.parent(),
            submenu = item.children('ul');
                                     
            if(item.hasClass('active-menuitem')) {
                if(submenu.length) {
                    $this.removeMenuitem(item.attr('id'));
                    item.removeClass('active-menuitem');
                    submenu.slideUp();
                }
            }
            else {
                $this.addMenuitem(item.attr('id'));
                $this.deactivateItems(item.siblings(), true);
                $this.activate(item);
            }
                                    
            if(submenu.length) {
                e.preventDefault();
            }
        });
    },
         
    activate: function(item) {
        var submenu = item.children('ul');
        item.addClass('active-menuitem');

        if(submenu.length) {
            submenu.slideDown();
        }
    },
    
    deactivate: function(item) {
        var submenu = item.children('ul');
        item.removeClass('active-menuitem');
        
        if(submenu.length) {
            submenu.hide();
        }
    },
        
    deactivateItems: function(items, animate) {
        var $this = this;
        
        for(var i = 0; i < items.length; i++) {
            var item = items.eq(i),
            submenu = item.children('ul');
            
            if(submenu.length) {
                if(item.hasClass('active-menuitem')) {
                    var activeSubItems = item.find('.active-menuitem');
                    item.removeClass('active-menuitem');
                    item.find('.ink').remove();
                    
                    if(animate) {
                        submenu.slideUp('normal', function() {
                            $(this).parent().find('.active-menuitem').each(function() {
                                $this.deactivate($(this));
                            });
                        });
                    }
                    else {
                        submenu.hide();
                        item.find('.active-menuitem').each(function() {
                            $this.deactivate($(this));
                        });
                    }
                    
                    $this.removeMenuitem(item.attr('id'));
                    activeSubItems.each(function() {
                        $this.removeMenuitem($(this).attr('id'));
                    });
                }
                else {
                    item.find('.active-menuitem').each(function() {
                        var subItem = $(this);
                        $this.deactivate(subItem);
                        $this.removeMenuitem(subItem.attr('id'));
                    });
                }
            }
            else if(item.hasClass('active-menuitem')) {
                $this.deactivate(item);
                $this.removeMenuitem(item.attr('id'));
            }
        }
    },
    
    clearActiveItems: function() {
        var activeItems = this.jq.find('li.active-menuitem'),
        subContainers = activeItems.children('ul');

        activeItems.removeClass('active-menuitem');
        subContainers.hide();
    },
            
    removeMenuitem: function (id) {
        this.expandedMenuitems = $.grep(this.expandedMenuitems, function (value) {
            return value !== id;
        });
        this.saveMenuState();
    },
    
    addMenuitem: function (id) {
        if ($.inArray(id, this.expandedMenuitems) === -1) {
            this.expandedMenuitems.push(id);
        }
        this.saveMenuState();
    },
    
    saveMenuState: function() {
        $.cookie('atlantis_expandeditems', this.expandedMenuitems.join(','), {path: '/'});
    },
    
    clearMenuState: function() {
        $.removeCookie('atlantis_expandeditems', {path: '/'});
    },
        
    restoreMenuState: function() {
        var menucookie = $.cookie('atlantis_expandeditems');
        if (menucookie) {
            this.expandedMenuitems = menucookie.split(',');
            for (var i = 0; i < this.expandedMenuitems.length; i++) {
                var id = this.expandedMenuitems[i];
                if (id) {
                    var menuitem = $("#" + this.expandedMenuitems[i].replace(/:/g, "\\:"));
                    menuitem.addClass('active-menuitem');
                    
                    var submenu = menuitem.children('ul');
                    if(submenu.length) {
                        submenu.show();
                    }
                }
            }
        }
        
        var inlineProfileCookie = $.cookie('atlantis_inlineprofile_expanded');
        if (inlineProfileCookie) {
            this.profileMenu.show().prev('.profile').addClass('profile-expanded');
        }
    }
    
});

PrimeFaces.AtlantisConfigurator = {

    changeMenuMode: function(mode) {
        var wrapper = $(document.body).children('.layout-wrapper');

        if (mode === 'layout-overlay-menu')
            wrapper.addClass('layout-overlay-menu');
        else
            wrapper.removeClass('layout-overlay-menu');
    },

    changeMenuColor: function(color) {
        var wrapper = $(document.body).children('.layout-wrapper');
        var sidebar = wrapper.children('.layout-sidebar');

        if (color === 'layout-sidebar-dark')
            sidebar.addClass('layout-sidebar-dark');
        else
            sidebar.removeClass('layout-sidebar-dark');
    },

    changeLayout: function(layoutTheme) {
        var linkElement = $('link[href*="layout-"]');
        var href = linkElement.attr('href');
        var startIndexOf = href.indexOf('layout-') + 7;
        var endIndexOf = href.indexOf('.css');
        var currentColor = href.substring(startIndexOf, endIndexOf);

        this.replaceLink(linkElement, href.replace(currentColor, layoutTheme));
    },

    changeScheme: function(theme) {
        var library = 'primefaces-atlantis';
        var linkElement = $('link[href*="theme.css"]');
        var href = linkElement.attr('href');
        var index = href.indexOf(library) + 1;
        var currentTheme = href.substring(index + library.length);

        this.replaceLink(linkElement, href.replace(currentTheme, theme));
    },

    changeMenuToRTL: function() {
        var wrapper = $('.layout-wrapper');
        wrapper.toggleClass('layout-rtl');
    },

    beforeResourceChange: function() {
        PrimeFaces.ajax.RESOURCE = null;    //prevent resource append
    },

    replaceLink: function(linkElement, href) {
        PrimeFaces.ajax.RESOURCE = 'jakarta.faces.Resource';

        var isIE = this.isIE();

        if (isIE) {
            linkElement.attr('href', href);
        }
        else {
            var cloneLinkElement = linkElement.clone(false);

            cloneLinkElement.attr('href', href);
            linkElement.after(cloneLinkElement);

            cloneLinkElement.off('load').on('load', function() {
                linkElement.remove();
            });
        }
    },

    clearLayoutState: function() {
        var menu = PF('AtlantisMenuWidget');

        if (menu) {
            menu.clearLayoutState();
        }
    },

    isIE: function() {
        return /(MSIE|Trident\/|Edge\/)/i.test(navigator.userAgent);
    },

    updateInputStyle: function(value) {
        if (value === 'filled')
            $(document.body).addClass('ui-input-filled');
        else
            $(document.body).removeClass('ui-input-filled');
    }
};

/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2006, 2014 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD (Register as an anonymous module)
		define(['jquery'], factory);
	} else if (typeof exports === 'object') {
		// Node/CommonJS
		module.exports = factory(require('jquery'));
	} else {
		// Browser globals
		factory(jQuery);
	}
}(function ($) {

	var pluses = /\+/g;

	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}

	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}

	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}

	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			// If we can't parse the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}

	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}

	var config = $.cookie = function (key, value, options) {

		// Write

		if (arguments.length > 1 && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setMilliseconds(t.getMilliseconds() + days * 864e+5);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// Read

		var result = key ? undefined : {},
			// To prevent the for loop in the first place assign an empty array
			// in case there are no cookies at all. Also prevents odd result when
			// calling $.cookie().
			cookies = document.cookie ? document.cookie.split('; ') : [],
			i = 0,
			l = cookies.length;

		for (; i < l; i++) {
			var parts = cookies[i].split('='),
				name = decode(parts.shift()),
				cookie = parts.join('=');

			if (key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}

			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}

		return result;
	};

	config.defaults = {};

	$.removeCookie = function (key, options) {
		// Must not alter options, thus extending a fresh object...
		$.cookie(key, '', $.extend({}, options, { expires: -1 }));
		return !$.cookie(key);
	};

}));

if (PrimeFaces.widget.InputSwitch) {
    PrimeFaces.widget.InputSwitch = PrimeFaces.widget.InputSwitch.extend({

        init: function (cfg) {
            this._super(cfg);

            if (this.input.prop('checked')) {
                this.jq.addClass('ui-inputswitch-checked');
            }
        },

        check: function () {
            var $this = this;

            this.input.prop('checked', true).trigger('change');
            setTimeout(function () {
                $this.jq.addClass('ui-inputswitch-checked');
            }, 100);
        },

        uncheck: function () {
            var $this = this;

            this.input.prop('checked', false).trigger('change');
            setTimeout(function () {
                $this.jq.removeClass('ui-inputswitch-checked');
            }, 100);
        }
    });
}

if (PrimeFaces.widget.AccordionPanel) {
    PrimeFaces.widget.AccordionPanel = PrimeFaces.widget.AccordionPanel.extend({

        init: function (cfg) {
            this._super(cfg);

            this.headers.last().addClass('ui-accordion-header-last');
        }
    });
}

/* Issue #924 is fixed for 5.3+ and 6.0. (compatibility with 5.3) */
if(window['PrimeFaces'] && window['PrimeFaces'].widget.Dialog) {
    PrimeFaces.widget.Dialog = PrimeFaces.widget.Dialog.extend({

        enableModality: function() {
            this._super();
            $(document.body).children(this.jqId + '_modal').addClass('ui-dialog-mask');
        },

        syncWindowResize: function() {}
    });
}

if (PrimeFaces.widget.SelectOneMenu) {
    PrimeFaces.widget.SelectOneMenu = PrimeFaces.widget.SelectOneMenu.extend({
        init: function (cfg) {
            this._super(cfg);

            var $this = this;
            if (this.jq.parent().hasClass('ui-float-label')) {
                this.m_panel = $(this.jqId + '_panel');
                this.m_focusInput = $(this.jqId + '_focus');

                this.m_panel.addClass('ui-input-overlay-panel');
                this.jq.addClass('ui-inputwrapper');

                if (this.input.val() != '') {
                    this.jq.addClass('ui-inputwrapper-filled');
                }

                this.input.off('change').on('change', function () {
                    $this.inputValueControl($(this));
                });

                this.m_focusInput.on('focus.ui-selectonemenu', function () {
                    $this.jq.addClass('ui-inputwrapper-focus');
                })
                    .on('blur.ui-selectonemenu', function () {
                        $this.jq.removeClass('ui-inputwrapper-focus');
                    });

                if (this.cfg.editable) {
                    this.label.on('input', function (e) {
                        $this.inputValueControl($(this));
                    }).on('focus', function () {
                        $this.jq.addClass('ui-inputwrapper-focus');
                    }).on('blur', function () {
                        $this.jq.removeClass('ui-inputwrapper-focus');
                        $this.inputValueControl($(this));
                    });
                }
            }
        },

        inputValueControl: function (input) {
            if (input.val() != '')
                this.jq.addClass('ui-inputwrapper-filled');
            else
                this.jq.removeClass('ui-inputwrapper-filled');
        }
    });
}

if (PrimeFaces.widget.Chips) {
    PrimeFaces.widget.Chips = PrimeFaces.widget.Chips.extend({
        init: function (cfg) {
            this._super(cfg);

            var $this = this;
            if (this.jq.parent().hasClass('ui-float-label')) {
                this.jq.addClass('ui-inputwrapper');

                if ($this.jq.find('.ui-chips-token').length !== 0) {
                    this.jq.addClass('ui-inputwrapper-filled');
                }

                this.input.on('focus.ui-chips', function () {
                    $this.jq.addClass('ui-inputwrapper-focus');
                }).on('input.ui-chips', function () {
                    $this.inputValueControl();
                }).on('blur.ui-chips', function () {
                    $this.jq.removeClass('ui-inputwrapper-focus');
                    $this.inputValueControl();
                });

            }
        },

        inputValueControl: function () {
            if (this.jq.find('.ui-chips-token').length !== 0 || this.input.val() != '')
                this.jq.addClass('ui-inputwrapper-filled');
            else
                this.jq.removeClass('ui-inputwrapper-filled');
        }
    });
}

if (PrimeFaces.widget.DatePicker) {
    PrimeFaces.widget.DatePicker = PrimeFaces.widget.DatePicker.extend({
        init: function (cfg) {
            this._super(cfg);

            var $this = this;
            if (this.jq.parent().hasClass('ui-float-label') && !this.cfg.inline) {
                if (this.input.val() != '') {
                    this.jq.addClass('ui-inputwrapper-filled');
                }

                this.jqEl.off('focus.ui-datepicker blur.ui-datepicker change.ui-datepicker')
                    .on('focus.ui-datepicker', function () {
                        $this.jq.addClass('ui-inputwrapper-focus');
                    })
                    .on('blur.ui-datepicker', function () {
                        $this.jq.removeClass('ui-inputwrapper-focus');
                    })
                    .on('change.ui-datepicker', function () {
                        $this.inputValueControl($(this));
                    });
            }
        },

        inputValueControl: function (input) {
            if (input.val() != '')
                this.jq.addClass('ui-inputwrapper-filled');
            else
                this.jq.removeClass('ui-inputwrapper-filled');
        }
    });
}