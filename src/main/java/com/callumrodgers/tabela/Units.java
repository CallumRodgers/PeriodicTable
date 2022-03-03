package com.callumrodgers.tabela;

public enum Units {
    /**
     * The <i>Kelvin</i> temperature unit. The default double values on the <code>Element</code> class
     * are on Kelvin.
     */
    KELVIN,

    /**
     * The <i>Celsius</I> temperature unit. The default Kelvin value is converted into Celsius
     * by subtracting exactly <b>273.15</b> degrees.
     */
    CELSIUS,

    /**
     * <p>The <i>Fahrenheit</i> temperature unit. The default Kelvin value is first
     * converted into Celsius by subtracting <b>273.15</b> degrees, then multiplied
     * by <b>9/5</b>, to be finally added to <b>32</b>, following the formula:</p>
     * <p><b>X°F = (YK - 273.15) * 9/5 + 32</b></p>
     * <p>Where "X" is the final Fahrenheit degree, and "Y" is the initial
     * Kelvin value.</p>
     */
    FAHRENHEIT
}
