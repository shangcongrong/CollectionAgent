package com.newcom.json;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;

public class DomParseXml {
	public List<String> getActivitynames(InputStream inputstream) throws Exception{
		List<String> list=new ArrayList<String>();
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder =factory.newDocumentBuilder();
		Document document=builder.parse(inputstream);
		Element element=document.getDocumentElement();
		
		NodeList activityNodeList=element.getElementsByTagName("activity");
		for(int i=0;i<activityNodeList.getLength();i++){
			Element activityElement = (Element) activityNodeList.item(i);
			String activityName=activityElement.getAttribute("android:name");
			list.add(activityName); 
		}
		
		return list;
		
	}
}
