package com.callumrodgers.tabela;

import com.callumrodgers.tabela.xml.XMLParser;
import com.callumrodgers.tabela.xml.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * The <code>Element</code> class wraps many attributes and characteristics of a chemical element, or
 * an <i>atom</i>. It contains the following fields:
 * <ul>
 *     <li>Atomic number</li>
 *     <li>Name (in Portuguese in this project)</li>
 *     <li>Latin name</li>
 *     <li>Symbol</li>
 *     <li>Amount of protons</li>
 *     <li>Amount of neutrons</li>
 *     <li>Amount of electrons</li>
 *     <li>Atomic mass</li>
 *     <li>Density</li>
 *     <li>Fusion point</li>
 *     <li>Boiling point</li>
 *     <li>Electron shells</li>
 *     <li>Electronegativity</li>
 *     <li>Ionisation potential</li>
 *     <li>Common ion charges</li>
 * </ul>
 * All these values are parsed from <code>XML</code> files in the <code>resources</code> directory, then
 * they are stored into 118 <code>Element</code> objects. That means that any Element used throughout
 * the application is the same object as the one stored in the 118-length <code>LinkedList</code>.
 * <p>This class also has boolean methods of identifying the Element's family, such as <code>isNobleGas()</code>,
 * <code>isAlkalineMetal()</code>, <code>isActinide()</code>, etc.
 */
public class Element {

    /**
     * Amount of elements currently implemented.
     */
    private static final int AMOUNT_OF_ELEMENTS = 118;

    /**
     * <code>List</code> containing all the original 118 <code>Element</code> Objects. They have their attributes
     * assigned from the parsed XML files, and now all values are stored in RAM, allowing for much quicker access.
     * This means that each <code>Element</code> Object further created in the application is based on this list's
     * counterpart.
     */
    private static final List<Element> elements = new LinkedList<>();

    /**
     * The element's atomic number, equals to the amount of protons.
     */
    private int atomicNumber;

    /**
     * The name of the element.
     */
    private String name;

    /**
     * The latin name of the element.
     */
    private String latinName;

    /**
     * The symbol of the element in the Periodic Table.
     */
    private String symbol;

    /**
     * Amount of protons.
     */
    private int protons;

    /**
     * Amount of neutrons.
     */
    private int neutrons;

    /**
     * Amount of electrons.
     */
    private int electrons;

    /**
     * Precise mass of the atom in atomic units.
     */
    private double atomicMass;

    /**
     * The density of the element, in grams per cubic centimetre (g/cm³).
     */
    private double density;

    /**
     * The point of temperature when the element starts to liquefy.
     */
    private double fusionPoint;

    /**
     * The point of temperature when the element starts to evaporate.
     */
    private double boilingPoint;

    /**
     * A representation of the disposition of electrons in the shells, such as:
     * <p><code>K2 L8 M0 N0 O0 P0 Q0</code>
     * <p>or
     * <p><code>K2 L8 M18 N32 O32 P18 Q8</code>
     */
    private String electronShells;

    /**
     * The electronegativity of the element. It measures the magnitude of an Element's bond energy,
     * ranging from 0.79 to 3.98.
     */
    private double electronegativity;

    /**
     * The ionisation potential of the element. It measures how much energy it takes to remove an electron
     * from an isolated atom.
     */
    private double ionisationPotential;

    /**
     * Common ion charges of this Element.
     */
    private String ions;

    /**
     * Loads the original 118 objects from the XML files, adding the results to the <code>elements</code> List.
     */
    public static void loadElements() {
        Document e1to20, e21to40, e41to60, e61to80, e81to100, e101to118; // Ugly, but each file needs a Document obj I assume.
        try {
            e1to20 = XMLParser.parse("/elements/elements 1-20.xml");
            e21to40 = XMLParser.parse("/elements/elements 21-40.xml");
            e41to60 = XMLParser.parse("/elements/elements 41-60.xml");
            e61to80 = XMLParser.parse("/elements/elements 61-80.xml");
            e81to100 = XMLParser.parse("/elements/elements 81-100.xml");
            e101to118 = XMLParser.parse("/elements/elements 101-118.xml");
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Exceção ao carregar arquivos XML", "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // No reason to keep running if the table will be incomplete or wrong.
            return; // Unreachable, only here so IDE stops complaining about nullity.
        }

        // "n" means/is used as the atomic number of the Element.
        int n = 1;

        // While the current atomic number is still part of our table.
        while (n <= AMOUNT_OF_ELEMENTS) {
            // Creating a new Element object in memory.
            Element element = new Element();

            // List of attributes. Note that "attributes" here refer to the ELEMENT'S attributes
            // (i.e. name, mass, density, etc.), and NOT XML attributes.
            List<Node> attributes;

            // We need to pass in the correct Document, and the only way to know which is to
            // see the Element's atomic number.
            if (n < 21) {
                attributes = getElementAttributeList(e1to20, n);
            } else if (n < 41) {
                attributes = getElementAttributeList(e21to40, n);
            } else if (n < 61) {
                attributes = getElementAttributeList(e41to60, n);
            } else if (n < 81) {
                attributes = getElementAttributeList(e61to80, n);
            } else if (n < 101) {
                attributes = getElementAttributeList(e81to100, n);
            } else {
                attributes = getElementAttributeList(e101to118, n);
            }

            // Looping through the attributes.
            for (int i = 0; i < attributes.size(); i++) {
                Node attb = attributes.get(i);

                String val = attb.getTextContent(); // Getting the value inside the node.

                // Depending on the tag, we assign the value to the respective field, doing necessary
                // conversions and checking.
                switch (attb.getNodeName()) {
                    case Keys.ATOMIC_NUMBER        -> element.atomicNumber         = Integer.parseInt(val);
                    case Keys.NAME                 -> element.name                 = val;
                    case Keys.LATIN_NAME           -> element.latinName            = val;
                    case Keys.SYMBOL               -> element.symbol               = val;
                    case Keys.PROTONS              -> element.protons              = Integer.parseInt(val);
                    case Keys.NEUTRONS             -> element.neutrons             = Integer.parseInt(val);
                    case Keys.ELECTRONS            -> element.electrons            = Integer.parseInt(val);
                    case Keys.ATOMIC_MASS          -> element.atomicMass           = Double.parseDouble(val);
                    case Keys.DENSITY              -> element.density              = val.equals("---") ? Double.NaN : Double.parseDouble(val);
                    case Keys.ELECTRONEGATIVITY    -> element.electronegativity    = val.equals("---") ? Double.NaN : Double.parseDouble(val);
                    case Keys.FUSION_POINT         -> element.fusionPoint          = val.equals("---") ? Double.NaN : Double.parseDouble(val);
                    case Keys.BOILING_POINT        -> element.boilingPoint         = val.equals("---") ? Double.NaN : Double.parseDouble(val);
                    case Keys.ELECTRON_SHELLS      -> element.electronShells       = val;
                    case Keys.IONS                 -> element.ions                 = val;
                    case Keys.IONISATION_POTENTIAL -> element.ionisationPotential  = val.equals("---") ? Double.NaN : Double.parseDouble(val);
                }
            }

            elements.add((n - 1), element); // Adding the initialised element to the List, maintaining their correct order.
            n++;
        }
    }

    /**
     * Gets the Attribute list of the Element in the specified Document and atomic number.
     * <p>Note that "attributes" <i>does NOT</i> refer to the <code>XML</code> attributes, but the
     * <code>Element</code>'s ones, such as the name, mass, density, etc.
     *
     * @param d      the document
     * @param number the atomic number
     * @return the NodeList of the attributes of the Element.
     */
    private static List<Node> getElementAttributeList(Document d, int number) {
        if (number > 118 || number < 1) throw new IllegalArgumentException("Invalid number: " + number);
        // Gets all "element"s of the Document into a NodeList.
        NodeList elements = d.getElementsByTagName("element");

        // Filtering out all the other kinds of Nodes that we don't want.
        List<Node> nodes = XMLUtils.getElementNodesFromList(elements);

        // First assigning the index to the atomic number minus one (because of how arrays index).
        int indexInXML = (number - 1);

        // Since there are only 20 elements per file, we should reduce this index until we reach
        // this Element's index inside the XML.
        int elemPerFile = 20;
        while (indexInXML >= elemPerFile) {
            indexInXML -= elemPerFile;
        }

        return XMLUtils.getElementNodesFromList(nodes.get(indexInXML).getChildNodes());
    }

    private Element() {}

    /**
     * Retrieves the <code>Element</code> object associated with the given atomic number.
     * @param atomicNumber the atomic number, or the number of protons in the atom being created.
     */
    public static Element get(int atomicNumber) {
        if (atomicNumber < 1 || atomicNumber > 118) {
            String message = atomicNumber + " is an invalid atomic number." +
                    " Currently this class extends up to Oganesson-118, being subject to updates later.";
            throw new IllegalArgumentException(message);
        }
        return elements.get(atomicNumber - 1);
    }

    /**
     * Returns the same as the <code>getProtons()</code> method. Atomic numbers are based on the amount
     * of protons of an atom, but for readability's sake, this method is preferable when wanting the
     * "element index" in the table more than the actual proton amount.
     * @return the atomic number of the Element.
     */
    public int getAtomicNumber() {
        return atomicNumber;
    }

    /**
     * Gets the name of the initialized element. Throws a <code>NullPointerException()</code>
     * if the atom has not been yet created.
     * @return the Name of the Element.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the latin name of the initialized element. Throws a <code>NullPointerException()</code>
     * if the atom has not been yet created.
     * Latin names are not exactly <i>useful</i> in most situations, but they show from where the symbol
     * of an element came from. For example, the symbol of "Gold" is "Au", because it comes from the
     * Latin "Aurum".
     * @return the Latin name of the Element.
     */
    public String getLatinName() {
        return latinName;
    }

    /**
     * Gets the symbol of the initialized element. For instance, if the element created was an Oxygen atom,
     * it would return the symbol "O", and Lead would return "Pb".
     * @return the Symbol of the Element.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the amount of protons of the atom.
     * @return the amount of protons, or atomic number of the atom.
     */
    public int getProtons() {
        return protons;
    }

    /**
     * Gets the amount of neutrons of the created atom.
     * @return amount of neutrons of the atom.
     */
    public int getNeutrons() {
        return neutrons;
    }

    /**
     * Gets the amount of electrons of the created atom.
     * @return amount of electrons of the atom.
     */
    public int getElectrons() {
        return electrons;
    }

    /**
     * Gets the atomic mass of the element, in atomic units.
     * @return atomic mass.
     */
    public double getAtomicMass() {
        return atomicMass;
    }

    /**
     * Gets the <i>density</i> of the <code>Element</code>.
     * @return the density, in double value.
     */
    public double getDensity() {
        return density;
    }

    /**
     * Gets the <i>electronegativity</i> of the <code>Element</code>.
     * @return the electronegativity, in double value.
     */
    public double getElectronegativity() {
        return electronegativity;
    }

    /**
     * Gets the <i>ionisation potential</i> of the <code>Element</code>.
     * @return the potential, in double value.
     */
    public double getIonisationPotential() {
        return ionisationPotential;
    }

    /**
     * Gets the <i>shells</i> of the <code>Element</code>.
     * @return the structure of the atom in electron shells.
     */
    public String getElectronShells() {
        return electronShells;
    }

    /**
     * Gets the <i>common ions</i> of the <code>Element</code>.
     * @return the most common charges of the element.
     */
    public String getIons() {
        return ions;
    }

    /**
     * Returns the fusion point of the element--the temperature where it changes from its solid state to liquid--
     * in the specified unit and decimal scale precision.
     * <p>
     * This method may return <code>Double.NaN</code> if the element's fusion point is unknown.
     *
     * @param unit      The unit for the value to be returned in (Kelvin, Celsius, Fahrenheit).
     * @param precision The precision of the decimal scale
     * @return the fusion point in the determined unit and scale.
     */
    public double getFusionPoint(Units unit, int precision) {
        if (Double.isNaN(fusionPoint)) return Double.NaN; // If the temperature is unknown there's nothing to convert.
        return convertFromKelvinToUnit(fusionPoint, unit, precision);
    }

    /**
     * Returns the boiling point of the element--the temperature where it changes from its liquid state to gas--
     * in the specified unit and decimal scale precision.
     * <p>
     * This method may return <code>Double.NaN</code> if the element's boiling point is unknown.
     *
     * @param unit      The unit for the value to be returned in (Kelvin, Celsius, Fahrenheit).
     * @param precision The precision of the decimal scale
     * @return the boiling point in the determined unit and scale.
     */
    public double getBoilingPoint(Units unit, int precision) {
        if (Double.isNaN(boilingPoint)) return Double.NaN; // If the temperature is unknown there's nothing to convert.
        return convertFromKelvinToUnit(boilingPoint, unit, precision);
    }

    /**
     * Converts the temperature value (in Kelvin) to the specified unit, with the also specified
     * decimal precision.
     * @param value the temperature, or value itself.
     * @param unit the unit to be converted to.
     * @param precision the precision of the result
     * @return the result of the conversion, in double value.
     */
    private double convertFromKelvinToUnit(double value, Units unit, int precision) {
        if (unit == Units.KELVIN) return value; // Default value is already in Kelvin.
        // Creates an exact conversion from Kelvin to Celsius.
        // 273.15 is subtracted from the melting point.
        // Calculations are done in String literals to avoid unpredictability.
        BigDecimal celsius = new BigDecimal(String.valueOf(
                (BigDecimal.valueOf(value)).subtract(BigDecimal.valueOf(273.15))));
        switch (unit) {
            case CELSIUS -> {
                return celsius.setScale(precision, RoundingMode.HALF_UP).doubleValue();
            }
            case FAHRENHEIT -> {
                /*
                    Following the formula: x°F = (y°C * 5/9) + 32
                    Where "x" is the Fahrenheit temp. and "y" is the Celsius'.
                    Since -> 5/9 = 1.8, we can just multiply the Celsius value by 1.8
                 */
                BigDecimal fahrenheit = celsius.multiply(BigDecimal.valueOf(1.8)).add(BigDecimal.valueOf(32.0));
                return fahrenheit.setScale(precision, RoundingMode.HALF_UP).doubleValue();
            }
            default -> throw new IllegalArgumentException("This unit is probably not implemented yet.");
        }
    }

    /**
     * Gets an array of all the <code>Element</code>s.
     * @return the array.
     */
    public static Element[] getAllElements() {
        return elements.toArray(Element[]::new);
    }

    /**
     * Gets the atomic number of an element by searching its name.
     * @param elementName the name of the <code>Element</code>
     * @return
     */
    public static int getAtomicNumberByName(String elementName) {
        for (int i = 0; i < 118; i++) {
            Element e = elements.get(i + 1);
            if (e.getName().equalsIgnoreCase(elementName)) {
                return e.getAtomicNumber();
            }
        }
        throw new IllegalArgumentException("Name '" + elementName + "' does not exist.");
    }

    public static int getAtomicNumberBySymbol(String elementSymbol) {
        for (int i = 0; i < 118; i++) {
            Element e = elements.get(i + 1);
            if (e.getSymbol().equalsIgnoreCase(elementSymbol)) {
                return e.getAtomicNumber();
            }
        }
        throw new IllegalArgumentException("Symbol '" + elementSymbol + "' does not exist.");
    }

    /**
     * Checks whether this <code>Element</code> is part of any metal family.
     * @return true if it is, false if not.
     */
    public boolean isMetal() {
        return isAlkalineMetal() || isAlkalineEarthMetal() || isTransitionMetal() ||
                isPostTransitionMetal() || isLanthanide() || isActinide();
    }

    /**
     * Checks whether this <code>Element</code> is part of the <i>Alkaline Metals</i> family.
     * @return true if it is, false if not.
     */
    public boolean isAlkalineMetal() {
        return switch (atomicNumber) {
            default -> false;
            case 3, 11, 19, 37, 55, 87 -> true;
        };
    }

    /**
     * Checks whether this <code>Element</code> is part of the <i>Alkaline-earth Metals</i> family.
     * @return true if it is, false if not.
     */
    public boolean isAlkalineEarthMetal() {
        return switch (atomicNumber) {
            default -> false;
            case 4, 12, 20, 38, 56, 88 -> true;
        };
    }

    /**
     * Checks whether this <code>Element</code> is part of the <i>Transition Metals</i> family.
     * @return true if it is, false if not.
     */
    public boolean isTransitionMetal() {
        return switch (atomicNumber) {
            default -> false;
            case 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 -> true;
            case 39, 40, 41, 42, 43, 44, 45, 46, 47, 48 -> true;
            case 72, 73, 74, 75, 76, 77, 78, 79, 80 -> true;
            case 104, 105, 106, 107, 108, 109, 110, 111, 112 -> true;
        };
    }

    /**
     * Checks whether this <code>Element</code> is part of the <i>Lanthanides</i> family.
     * @return true if it is, false if not.
     */
    public boolean isLanthanide() {
        return switch (atomicNumber) {
            default -> false;
            case 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71 -> true;
        };
    }

    /**
     * Checks whether this <code>Element</code> is part of the <i>Actinides</i> family.
     * @return true if it is, false if not.
     */
    public boolean isActinide() {
        return switch (atomicNumber) {
            default -> false;
            case 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103 -> true;
        };
    }

    /**
     * Checks whether this <code>Element</code> is part of the <i>Metalloids</i> family.
     * @return true if it is, false if not.
     */
    public boolean isMetalloid() {
        return switch (atomicNumber) {
            default -> false;
            case 5 -> true;
            case 14 -> true;
            case 32, 33 -> true;
            case 51, 52 -> true;
            case 84 -> true;
        };
    }

    /**
     * Checks whether this <code>Element</code> is part of the <i>Post-transition Metals</i> family.
     * @return true if it is, false if not.
     */
    public boolean isPostTransitionMetal() {
        return switch (atomicNumber) {
            default -> false;
            case 13, 31, 49, 50, 81, 82, 83, 113, 114, 115, 116 -> true;
        };
    }

    /**
     * Checks whether this <code>Element</code> is part of the <i>Non-metals</i> family.
     * @return true if it is, false if not.
     */
    public boolean isNonMetal() {
        return switch (atomicNumber) {
            default -> false;
            case 1, 6, 7, 8 -> true;
            case 15, 16 -> true;
            case 34 -> true;
        };
    }

    /**
     * Checks whether this <code>Element</code> is part of the <i>Halogens</i> family.
     * @return true if it is, false if not.
     */
    public boolean isHalogen() {
        return switch (atomicNumber) {
            default -> false;
            case 9, 17, 35, 53, 85, 117 -> true;
        };
    }

    /**
     * Checks whether this <code>Element</code> is part of the <i>Noble Gases</i> family.
     * @return true if it is, false if not.
     */
    public boolean isNobleGas() {
        return switch (atomicNumber) {
            default -> false;
            case 2, 10, 18, 36, 54, 86, 118 -> true;
        };
    }

    /**
     * Converts this <code>Element</code> object into a <code>String</code>,
     * containing all the element's properties.
     * @return a String representation of the Element.
     */
    @Override
    public String toString() {
        return "com.callumrodgers.tabela.Element {" + "name = '" + name + '\'' +
                ", latinName = '" + latinName + '\'' +
                ", symbol = '" + symbol + '\'' +
                ", protonAmount = " + protons +
                ", electronAmount = " + electrons +
                ", neutronAmount = " + neutrons +
                ", atomicMass = " + atomicMass +
                ", density = " + density +
                ", electronegativity = " + electronegativity +
                ", ionisationPotential = " + ionisationPotential +
                ", ions = " + ions +
                ", shells = " + electronShells +
                ", meltingPoint = " + fusionPoint +
                ", boilingPoint = " + boilingPoint +
                '}';
    }

    /**
     * Two <code>Element</code> objects are equal when their atomic number is the same.
     * @param o the object to compare
     * @return whether two <code>Element</code> objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof Element element)) return false;
        return this.atomicNumber == element.atomicNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(atomicNumber);
    }

    /**
     * Inner-class containing the String keys to the XML tags.
     */
    private static class Keys {
        public static final String ATOMIC_NUMBER        = "number",
                                   NAME                 = "name",
                                   LATIN_NAME           = "latin-name",
                                   SYMBOL               = "symbol",
                                   PROTONS              = "protons",
                                   NEUTRONS             = "neutrons",
                                   ELECTRONS            = "electrons",
                                   ATOMIC_MASS          = "atomic-mass",
                                   DENSITY              = "density",
                                   ELECTRONEGATIVITY    = "electronegativity",
                                   ELECTRON_SHELLS      = "electron-shells",
                                   IONISATION_POTENTIAL = "ionisation-potential",
                                   IONS                 = "ions",
                                   FUSION_POINT         = "fusion-point",
                                   BOILING_POINT        = "boiling-point";
    }
}
