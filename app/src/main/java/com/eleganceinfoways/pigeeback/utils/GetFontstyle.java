package com.eleganceinfoways.pigeeback.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by drindia19 on 7/5/2016.
 */
public class GetFontstyle
{
    private static Context context;

    private static Typeface CoreSansBold = null;

    private static Typeface CoreSansBoldItalic = null;

    private static Typeface CoreSansExtraBold = null;

    private static Typeface CoreSansExtraBoldItalic = null;

    private static Typeface CoreSansMedium = null;

    private static Typeface CoreSansMediumItalic = null;

    public GetFontstyle(Context context) {
        this.context = context;
    }

    //CoreSansBold TypeFace
    public static Typeface CoreSansBold()
    {
        if(CoreSansBold == null)
        {
             CoreSansBold = Typeface.createFromAsset(context.getAssets(),
                    "fonts/CoreSansGRounded-Bold.ttf");
        }
        return CoreSansBold;
    }

    //CoreSansBold TypeFace
    public static Typeface CoreSansBoldItalic()
    {
        if(CoreSansBoldItalic == null)
        {
             CoreSansBoldItalic = Typeface.createFromAsset(context.getAssets(),
                    "fonts/CoreSansGRounded-Bolditalic.ttf");
        }
        return CoreSansBoldItalic;
    }

    //CoreSansBold TypeFace
    public static Typeface CoreSansExtraBold()
    {
        if(CoreSansExtraBold == null)
        {
             CoreSansExtraBold = Typeface.createFromAsset(context.getAssets(),
                    "fonts/CoreSansGRounded-ExtraBold.ttf");
        }
        return CoreSansExtraBold;
    }

    //CoreSansBold TypeFace
    public static Typeface CoreSansExtraBoldItalic()
    {
        if(CoreSansExtraBoldItalic == null)
        {
            CoreSansBold = Typeface.createFromAsset(context.getAssets(),
                    "fonts/CoreSansGRounded-ExtraBolditalic.ttf");
        }
        return CoreSansExtraBoldItalic;
    }

    //CoreSansBold TypeFace
    public static Typeface CoreSansMedium()
    {
        if(CoreSansMedium == null)
        {
            CoreSansMedium = Typeface.createFromAsset(context.getAssets(),
                    "fonts/CoreSansGRounded-Medium.ttf");
        }
        return CoreSansMedium;
    }

    //CoreSansBold TypeFace
    public static  Typeface CoreSansMediumItalic(Context context)
    {
        if(CoreSansMediumItalic == null)
        {
            CoreSansBold = Typeface.createFromAsset(context.getAssets(),
                    "fonts/CoreSansGRounded-Mediumitalic.ttf");
        }
        return CoreSansMediumItalic;
    }
}
