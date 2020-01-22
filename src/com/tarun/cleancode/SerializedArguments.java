package com.tarun.cleancode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SerializedArguments {
	
	private HashMap<Character, Argument> serializedArgumentsMap;
	private HashMap<Character, ArgumentType> elementToArgumentTypeMap;
	
	public SerializedArguments() {
		this.serializedArgumentsMap = new HashMap<Character, Argument>();
		this.elementToArgumentTypeMap = new HashMap<Character, ArgumentType>();
	}
	
	public void serializeArguments(
		String schema,
		String[] argumentList) {
		List<String> schemaElements = getSchemaElements(schema);
		for(String schemaElement : schemaElements) {
			schemaElement = schemaElement.trim();
			validateSchemaElement(schemaElement);
			char elementId = getElementId(schemaElement);
			String argumentType = getArgumentType(schemaElement);
			setArgumentType(elementId, argumentType);
			if(argumentIsBoolean(elementId))
				serializedArgumentsMap.put(elementId, new BooleanArgument());
		}
		HashMap<Character, 
				ArrayList<String>> elementToArgumentsMap = parseArgumentList(argumentList);
		setArguments(elementToArgumentsMap);
		
	}
	
	private void setBooleanArgument(
		Character elementId) {
		BooleanArgument booleanArgument = (BooleanArgument)serializedArgumentsMap.get(elementId);
		booleanArgument.setValue(true);
		serializedArgumentsMap.put(elementId, booleanArgument);
	}
	private void setIntegerArgument(
		Character elementId,
		HashMap<Character, ArrayList<String>> elementToArgumentsMap) {
		IntegerArgument integerArgument = new IntegerArgument();
		try {
			integerArgument.setValue(Integer.parseInt(elementToArgumentsMap.get(elementId).get(0)));
			serializedArgumentsMap.put(elementId, integerArgument);
		}
		catch(NumberFormatException ne) {
			throw new InvalidArgumentType();
		}
	}
	private void setDoubleArgument(
		Character elementId,
		HashMap<Character, ArrayList<String>> elementToArgumentsMap) {
		DoubleArgument doubleArgument = new DoubleArgument();
		try {
			doubleArgument.setValue(Double.parseDouble(elementToArgumentsMap.get(elementId).get(0)));
			serializedArgumentsMap.put(elementId, doubleArgument);
		}
		catch(NumberFormatException ne) {
			throw new InvalidArgumentType();
		}
	}
	
	private void setStringArgument(
		Character elementId,
		HashMap<Character, ArrayList<String>> elementToArgumentsMap) {
		StringArgument stringArgument = new StringArgument();
		stringArgument.setValue(elementToArgumentsMap.get(elementId).get(0));
		serializedArgumentsMap.put(elementId, stringArgument);
	}
	
	private void setStringArrayArgument(
		Character elementId,
		HashMap<Character, ArrayList<String>> elementToArgumentsMap) {
		StringArrayArgument stringArrayArgument = new StringArrayArgument();
		stringArrayArgument.setValue(elementToArgumentsMap.get(elementId));
		serializedArgumentsMap.put(elementId, stringArrayArgument);
	}
	
	private void setMapArgument(
		Character elementId,
		HashMap<Character, ArrayList<String>> elementToArgumentsMap) {
		MapArgument mapArgument = new MapArgument();
		String mapEntriesString = elementToArgumentsMap.get(elementId).get(0);
		String[] mapEntries = mapEntriesString.split(",");
		HashMap<String,String> mapArgumentValue = new HashMap<String,String>();
		for(int i=0; i<mapEntries.length; i++) {
			String[] mapKeyValue = mapEntries[i].split(":");
			mapArgumentValue.put(mapKeyValue[0].trim(), mapKeyValue[1].trim());
		}
		mapArgument.setValue(mapArgumentValue);
		serializedArgumentsMap.put(elementId, mapArgument);
	}
	
	private void setArguments(
		HashMap<Character, ArrayList<String>> elementToArgumentsMap) {
		for(Character elementId : elementToArgumentsMap.keySet()) {
			if(argumentIsBoolean(elementId))
				setBooleanArgument(elementId);
			else if(argumentIsInteger(elementId)) 
				setIntegerArgument(elementId, elementToArgumentsMap);
			else if(argumentIsDouble(elementId))
				setDoubleArgument(elementId, elementToArgumentsMap);
			else if(argumentIsString(elementId))
				setStringArgument(elementId, elementToArgumentsMap);
			else if(argumentIsStringArray(elementId))
				setStringArrayArgument(elementId, elementToArgumentsMap);
			else if(argumentIsMap(elementId))
				setMapArgument(elementId, elementToArgumentsMap);
			else
				throw new InvalidArgumentType();
		}
	}
	
	private HashMap<Character, ArrayList<String>> parseArgumentList(
		String[] argumentList) {
		if(argumentListIsEmpty(argumentList))
			throw new InvalidArgumentList();
		HashMap<Character, 
			ArrayList<String>> elementToArgumentsMap = new HashMap<Character, 
																	ArrayList<String>>();
		int i = 0;
		char currentElementId = '\0';
		while(i < argumentList.length) {
			if(isNextArgument(argumentList[i])) {
				currentElementId = argumentList[i].charAt(1); 
				if(!elementToArgumentsMap.containsKey(currentElementId))
					elementToArgumentsMap.put(currentElementId, new ArrayList<String>());
				if(argumentIsBoolean(currentElementId))
					elementToArgumentsMap.get(currentElementId).add("true");
			}
			else
				elementToArgumentsMap.get(currentElementId).add(argumentList[i]);
			i++;
		}
		return elementToArgumentsMap;
	}
	
	private List<String> getSchemaElements(
		String schema) {
		return Arrays.asList(schema.split(","));
	}
	
	private void setArgumentType(
		char element,
		String argumentType) {
		if(argumentIsBoolean(argumentType))
			elementToArgumentTypeMap.put(element, ArgumentType.BOOLEAN);
		else if(argumentIsInteger(argumentType))
			elementToArgumentTypeMap.put(element,ArgumentType.INTEGER);
		else if(argumentIsDouble(argumentType))
			elementToArgumentTypeMap.put(element,ArgumentType.DOUBLE);
		else if(argumentIsString(argumentType))
			elementToArgumentTypeMap.put(element,ArgumentType.STRING);
		else if(argumentIsStringArray(argumentType))
			elementToArgumentTypeMap.put(element,ArgumentType.STRING_ARRAY);
		else if(argumentIsMap(argumentType))
			elementToArgumentTypeMap.put(element,ArgumentType.MAP);
		else
			throw new InvalidArgumentType();
	}
	

	private void validateSchemaElement(
		String schemaElement) {
		if(schemaElement.length() <= 0) throw new InvalidSchemaElement(); 
	}
	
	private char getElementId(
		String element) {
		return element.charAt(0);
	}
	
	private String getArgumentType(
		String schemaElement) {
		return schemaElement.substring(1);
	}
	
	private boolean argumentIsBoolean(
		String argumentType) {
		return (argumentType.length() == 0 || argumentType.isEmpty());
	}
	
	private boolean argumentIsBoolean(
		char elementId) {
		return elementToArgumentTypeMap.get(elementId) == ArgumentType.BOOLEAN;
	}
	
	private boolean argumentIsInteger(
		char elementId) {
		return elementToArgumentTypeMap.get(elementId) == ArgumentType.INTEGER;
	}
	
	private boolean argumentIsDouble(
		char elementId) {
		return elementToArgumentTypeMap.get(elementId) == ArgumentType.DOUBLE;
	}
	
	private boolean argumentIsString(
		char elementId) {
		return elementToArgumentTypeMap.get(elementId) == ArgumentType.STRING;
	}
	
	private boolean argumentIsStringArray(
		char elementId) {
		return elementToArgumentTypeMap.get(elementId) == ArgumentType.STRING_ARRAY;
	}
	
	private boolean argumentIsMap(char elementId) {
		return elementToArgumentTypeMap.get(elementId) == ArgumentType.MAP;
	}
	
	private boolean argumentIsInteger(
		String argumentType) {
		return argumentType.equals("#");
	}
	
	private boolean argumentIsDouble(
		String argumentType) {
		return argumentType.equals("##");
	}
	
	private boolean argumentIsString(
		String argumentType) {
		return argumentType.equals("*");
	}
	
	private boolean argumentIsStringArray(
		String argumentType) {
		return argumentType.equals("[*]");
	}
	
	private boolean argumentIsMap(
		String argumentType) {
		return argumentType.equals("&");
	}
	
	private boolean argumentListIsEmpty(
		String[] argumentList) {
		return argumentList.length == 0;
	}
	
	private boolean isNextArgument(
		String element) {
		return (element.charAt(0) == '-');
	}
	

}
