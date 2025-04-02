//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2025.03.08 at 03:19:27 PM CET 
//


package io.foodmenu.gt.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mealtype.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="mealtype"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="vegan"/&gt;
 *     &lt;enumeration value="veggie"/&gt;
 *     &lt;enumeration value="meat"/&gt;
 *     &lt;enumeration value="fish"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "mealtype")
@XmlEnum
public enum Mealtype {

    @XmlEnumValue("vegan")
    VEGAN("vegan"),
    @XmlEnumValue("veggie")
    VEGGIE("veggie"),
    @XmlEnumValue("meat")
    MEAT("meat"),
    @XmlEnumValue("fish")
    FISH("fish");
    private final String value;

    Mealtype(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Mealtype fromValue(String v) {
        for (Mealtype c: Mealtype.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
