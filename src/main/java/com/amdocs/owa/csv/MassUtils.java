package com.amdocs.owa.csv;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import com.amdocs.owa.csv.entities.Attribute;
import com.amdocs.owa.csv.entities.MassLine;
import com.amdocs.owa.csv.entities.MassRequest;

public class MassUtils {
	
	public static MassRequest buildMassRequestFromText(String text){
		
		MassRequest massRequest = new MassRequest();
	    String[] textAsArray = text.split("\n");
	    if(!validateMassRequest(textAsArray))
	    {
	    	return null;
	    }
	   // console.log(textAsArray);
	    massRequest.setHeaderAttributesString(textAsArray[0]);
	    massRequest.setDetailsAttributesString(textAsArray[2]);
	    massRequest.setLinesAttributesString(textAsArray[4]);
	    String[] headerAttrs = textAsArray[0].split(",");
	    String[] headerValues = textAsArray[1].split(",");
	    String[] detailsAttrs = textAsArray[2].split(",");
	    String[] detailsValues = textAsArray[3].split(",");
	    String[] linesAttrs = textAsArray[4].split(",");
	    String[] lines = Arrays.copyOfRange(textAsArray,5,textAsArray.length);
	    for(int i =0 ; i<headerAttrs.length;i++)
	    {
	        if(headerAttrs[i] != null)
	        {
	            massRequest.getMassHeader().getAttributes().add(new Attribute(headerAttrs[i],headerValues[i]));
	        }
	        
	    }
	    for(int i =0 ; i<detailsAttrs.length;i++)
	    {
	        if(detailsAttrs[i] != null)
	        {
	            massRequest.getMassDetails().getAttributes().add(new Attribute(detailsAttrs[i],detailsValues[i]));
	        }                  
	    }

	    for(String line : lines){
	        String[] lineValues = line.split(",");
	        MassLine retLine = new MassLine();
	        for(int i =0 ; i<linesAttrs.length;i++){
	            if(linesAttrs[i] == "Request Line Number" || linesAttrs[i] == "RequestLineNumber"){
	                retLine.setLineNumber(Integer.valueOf(lineValues[i]));
	                
	            }
	            retLine.getAttributes().add(new Attribute(linesAttrs[i],lineValues[i]));
	            
	        }
	        massRequest.getMassLines().add(retLine);
	    }
	    return massRequest;
	}

	private static boolean validateMassRequest(String[] textAsArray) {
		
		String requestType = textAsArray[2].split(",")[2];
		try {
			Method validate  = MassUtils.class.getMethod("validate"+requestType, String[].class,boolean.class);
			try {
				return (Boolean)validate.invoke(textAsArray,true);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validateChangeConfiguration(String[] textAsArray, boolean ind)
	{
		return true;
	}

}
