// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2014  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import com.ibm.eacm.preference.ColorPref;

/**
 * this is the LAF theme for EACM, if user changes background color 
 * this theme will use it.  This is only valid with the Metal cross platform LAF
 * 
 * @author Wendy Stimpson
 *
 */
//$Log: EACMTheme.java,v $
//Revision 1.1  2015/01/05 19:15:34  stimpsow
//use Theme for background colors
//
public class EACMTheme extends DefaultMetalTheme {
	/*from oceantheme
	private static final ColorUIResource PRIMARY1 =
			new ColorUIResource(0x6382BF);
	private static final ColorUIResource PRIMARY2 =
			new ColorUIResource(0xA3B8CC);
	private static final ColorUIResource PRIMARY3 =
			new ColorUIResource(0xB8CFE5);
	private static final ColorUIResource SECONDARY1 =
			new ColorUIResource(0x7A8A99);
	private static final ColorUIResource SECONDARY2 =
			new ColorUIResource(0xB8CFE5);
	private static final ColorUIResource SECONDARY3 =
			new ColorUIResource(0xEEEEEE);
			*/

	private  ColorUIResource primary1 = super.getPrimary1(); 
	private  ColorUIResource primary2 = super.getPrimary2(); 
	private  ColorUIResource primary3 = super.getPrimary3(); 

	private  ColorUIResource secondary1 = super.getSecondary1(); 
	private  ColorUIResource secondary2 = super.getSecondary2(); 
	private  ColorUIResource secondary3 = super.getSecondary3(); 
	


	 /*   private  ColorUIResource black = new ColorUIResource(222, 222, 222);
	    private  ColorUIResource white = new ColorUIResource(0, 0, 0);

	    protected ColorUIResource getBlack() { return black; }
	    protected ColorUIResource getWhite() { return white; }*/
	    
	/*
	 * 
   private static final ColorUIResource primary1 = new ColorUIResource(
                              102, 102, 153);
    private static final ColorUIResource primary2 = new ColorUIResource(
    						  153, 153, 204);
    private static final ColorUIResource primary3 = new ColorUIResource(
                              204, 204, 255);
    private static final ColorUIResource secondary1 = new ColorUIResource(
                              102, 102, 102);
    private static final ColorUIResource secondary2 = new ColorUIResource(
                              153, 153, 153);
    private static final ColorUIResource secondary3 = new ColorUIResource(
                              204, 204, 204);

    */
	
	EACMTheme(){
		// if the user hasn't change background, use defaults
		if((!ColorPref.canOverrideColor()) || ColorPref.getBackgroundColor().equals(secondary3)){
			resetDefaults();
		}else{
			updateColors();
		}
	}
	/**
     * Returns the primary 1 color. This returns a color with rgb values
     *
     * @return the primary 1 color
     */
    protected ColorUIResource getPrimary1() { 
    	return primary1;
    } 

    /**
     * Returns the primary 2 color. This returns a color with rgb values
     *
     * @return the primary 2 color
     */
    protected ColorUIResource getPrimary2() { 
    	return primary2;
    }

    /**
     * Returns the primary 3 color. This returns a color with rgb values
     *
     * @return the primary 3 color
     */
    protected ColorUIResource getPrimary3() { 
    	return primary3;
    }

    /**
     * Returns the secondary 1 color. This returns a color with rgb values
     *
     * @return the secondary 1 color
     */
    protected ColorUIResource getSecondary1() { 
    	return secondary1;
    }

    /**
     * Returns the secondary 2 color. This returns a color with rgb values
     *
     * @return the secondary 2 color
     */
    protected ColorUIResource getSecondary2() { 
    	return secondary2;
    }

    /**
     * Returns the secondary 3 color. This returns a color with rgb values
     *
     * @return the secondary 3 color
     */
    protected ColorUIResource getSecondary3() { 
    	return secondary3;
    }

    /**
     * user has turned off the background setting, restore base theme
     */
    public void resetDefaults(){
    	primary1 = super.getPrimary1(); 
    	primary2 = super.getPrimary2(); 
    	primary3 = super.getPrimary3(); 

    	secondary1 = super.getSecondary1(); 
    	secondary2 = super.getSecondary2(); 
    	secondary3 = super.getSecondary3();
    }
    
    /**
     * user has set the background setting
     */
    public void updateColors(){
    	//this is used for most controls
		secondary3 = (ColorUIResource) ColorPref.getBackgroundColor();
		
		//this is used for the scroll bars
		primary2 = new ColorUIResource(secondary3.getRed()/2, secondary3.getGreen()/2, secondary3.getBlue());
    }
    
	/* (non-Javadoc)
	 * @see javax.swing.plaf.metal.DefaultMetalTheme#getName()
	 */
	public String getName() { return "EACM_Steel"; }
	
}
