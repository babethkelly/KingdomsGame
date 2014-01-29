/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.core;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Helper class for serialize to / de-serialize from XML for GameState
 */
public class Serializer {

    private static Serializer instance = null;
    private XStream xstream = null;

    /**
     * Private constructor
     */
    private Serializer() {
        xstream = new XStream(new StaxDriver());
        xstream.alias("Board", Board.class);
        xstream.alias("Castle", Castle.class);
        xstream.alias("GameState", GameState.class);
        xstream.alias("Placeable", Placeable.class);
        xstream.alias("Player", Player.class);
        xstream.alias("Tile", Tile.class);
    }

    /**
     * Obtain instance of Serializer
     *
     * @return instance of Serializer
     */
    public static Serializer getInstance() {
        if (instance == null) {
            instance = new Serializer();
        }
        return instance;
    }

    /**
     * De-serialize GameState from XML string
     *
     * @param xml XML encoded GameState
     * @return GameState object
     */
    public GameState fromXML(String xml) {
        return (GameState) xstream.fromXML(xml);
    }

    /**
     * De-serialize generic object from XML string
     *
     * @param xml XML encoded object
     * @return generic object
     */
    public Object objectFromXML(String xml) {
        return xstream.fromXML(xml);
    }

    /**
     * Serialize GameState into XML string
     *
     * @param state GameState object
     * @return XML encoded GameState
     */
    public String toXML(GameState state) {
        String xml = xstream.toXML(state);
        return prettyFormat(xml, 2);
    }

    private static String prettyFormat(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
