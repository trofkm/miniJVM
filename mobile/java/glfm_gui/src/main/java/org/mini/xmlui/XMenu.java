package org.mini.xmlui;

import org.mini.gui.*;
import org.mini.gui.event.GActionListener;
import org.mini.xmlui.xmlpull.KXmlParser;
import org.mini.xmlui.xmlpull.XmlPullParser;

import java.util.Vector;

public class XMenu extends XObject implements GActionListener {
    static public final String XML_NAME = "menu";

    static class MenuItem {
        static public final String XML_NAME = "mi";
        String name;
        String text;
        String pic;
    }

    Vector items = new Vector();

    GMenu menu;
    boolean contextMenu = false;

    public XMenu(XContainer xc) {
        super(xc);
    }

    @Override
    String getXmlTag() {
        return XML_NAME;
    }

    void parseMoreAttribute(String attName, String attValue) {
        super.parseMoreAttribute(attName, attValue);
        if (attName.equals("contextmenu")) {
            if (attValue != null) {
                int v = 0;
                try {
                    v = Integer.parseInt(attValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                contextMenu = v == 0 ? false : true;
            }
        }
    }


    /**
     * 解析
     *
     * @param parser KXmlParser
     * @throws Exception
     */
    public void parse(KXmlParser parser) throws Exception {
        super.parse(parser);
        int depth = parser.getDepth();

        //得到域
        do {
            parser.next();
            String tagName = parser.getName();
            if (parser.getEventType() == XmlPullParser.START_TAG) {

                if (tagName.equals(MenuItem.XML_NAME)) {
                    MenuItem item = new MenuItem();

                    item.name = parser.getAttributeValue(null, "name");
                    item.pic = parser.getAttributeValue(null, "pic");
                    String tmp = parser.nextText();
                    item.text = tmp == null ? "" : tmp;
                    items.add(item);
                }
                toEndTag(parser, MenuItem.XML_NAME);
                parser.require(XmlPullParser.END_TAG, null, tagName);
            }
        }
        while (!(parser.getEventType() == XmlPullParser.END_TAG && parser.getName().equals(XML_NAME) && depth == parser.getDepth()));

    }

    void preAlignVertical() {
        if (height == XDef.NODEF) {
            if (raw_heightPercent != XDef.NODEF && parent.viewH != XDef.NODEF) {
                viewH = height = raw_heightPercent * parent.viewH / 100;
            } else {
                viewH = height = XDef.DEFAULT_COMPONENT_HEIGHT;
            }
        }
    }

    void preAlignHorizontal() {
        if (width == XDef.NODEF) {
            if (raw_widthPercent == XDef.NODEF) {
                viewW = width = parent.viewW;
            } else {
                viewW = width = raw_widthPercent * parent.viewW / 100;
            }
        }
    }

    public GObject getGui() {
        return menu;
    }

    void createGui() {
        if (menu == null) {
            menu = new GMenu(x, y, width, height);
            menu.setName(name);
            menu.setAttachment(this);
            for (int i = 0; i < items.size(); i++) {
                MenuItem item = (MenuItem) items.elementAt(i);
                GImage img = null;
                if (item.pic != null) {
                    img = GImage.createImageFromJar(item.pic);
                }
                GMenuItem gli = menu.addItem(item.text, img);
                gli.setActionListener(this);
                menu.setContextMenu(contextMenu);
            }
        } else {
            menu.setLocation(x, y);
            menu.setSize(width, height);
        }
    }


    @Override
    public void action(GObject gobj) {
        getRoot().getEventHandler().action(gobj, null);
    }

}
