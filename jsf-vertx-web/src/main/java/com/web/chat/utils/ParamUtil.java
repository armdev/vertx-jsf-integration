package com.web.chat.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 *
 * @author Armen Arzumanyan
 */
public class ParamUtil {

    static public Double doubleValue(String strValue) {
        Double reValue = null;

        if ((strValue == null) || (strValue.trim().equals(""))) {
            strValue = null;
        } else if (strValue == null) {
            return null;
        }

        DecimalFormatSymbols fs = new DecimalFormatSymbols();

        fs.setGroupingSeparator(',');
        fs.setDecimalSeparator('.');

        try {
            DecimalFormat nf = new DecimalFormat("#,###,###,##0.00", fs);

            nf.setMaximumFractionDigits(3);
            nf.setMaximumIntegerDigits(3);
            reValue = nf.parse(strValue).doubleValue();
        } catch (final ParseException e) {
        }

        return reValue;
    }

    static public Long longValue(String strValue) {
        Long reValue = null;

        if ((strValue == null) || (strValue.trim().equals(""))) {
            strValue = null;
        } else if (strValue == null) {
            return null;
        }

        NumberFormat nf = NumberFormat.getInstance();

        try {
            reValue = nf.parse(strValue).longValue();
        } catch (ParseException ex) {
        }

        return reValue;
    }

    static public Integer integerValue(Object strValue) {
        return integerValue((strValue != null)
                ? strValue.toString()
                : null);
    }

    static public Long longValue(Object strValue) {
        return longValue((strValue != null)
                ? strValue.toString()
                : null);
    }

    static public Integer integerValue(String strValue) {
        Integer reValue = null;

        if ((strValue == null) || (strValue.trim().equals(""))) {
            strValue = null;
        } else if (strValue == null) {
            return null;
        }

        NumberFormat nf = NumberFormat.getInstance();

        try {
            reValue = nf.parse(strValue).intValue();
        } catch (ParseException ex) {
        }

        return reValue;
    }
}
