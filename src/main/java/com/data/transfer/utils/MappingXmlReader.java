package com.data.transfer.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.data.transfer.model.TableMapping;

public class MappingXmlReader {

	private NodeList tagNodes;
	private Node tagNode;
	private Element tagElement;

	public Document loadDoc() {
		Document doc = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse("classpath:data-mapping.xml");
			doc.getDocumentElement().normalize();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	public List<TableMapping> getTableMappingList() {
		List<TableMapping> tableMappingList = new ArrayList<TableMapping>();
		Document doc = loadDoc();
		tagNodes = doc.getElementsByTagName("table-mapping");
		for (int i = 0; i < tagNodes.getLength(); i++) {
			tagNode = tagNodes.item(i);
			if (tagNode.getNodeType() == Node.ELEMENT_NODE) {
				tagElement = (Element) tagNode;
				String sourceTable = tagElement.getElementsByTagName("table-source").item(0).getTextContent();
				String targetTable = tagElement.getElementsByTagName("table-target").item(0).getTextContent();
				TableMapping tableMapping = new TableMapping();
				NodeList coulumMapping = tagElement.getElementsByTagName("column-mapping");
				HashMap<String, String> hashMap = new HashMap<>();
				
				for (int j = 0; j < coulumMapping.getLength(); j++) {
					Node item = coulumMapping.item(j);
					if (item.getNodeType() == Node.ELEMENT_NODE) {
						Element el = (Element) item;
						String sourceColumn = el.getElementsByTagName("column-source").item(0).getTextContent();
						String targetColumn = el.getElementsByTagName("column-target").item(0).getTextContent();		
						hashMap.put(sourceColumn, targetColumn);
					}
				}
				tableMapping.setColumnMapping(hashMap);
				tableMapping.setSourceTable(sourceTable);
				tableMapping.setTargetTable(targetTable);
				tableMappingList.add(tableMapping);
			}

		}

		return tableMappingList;
	}

}
