package com.uwi;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XMLParser implements QuestionParser {

    @Override
    public List<Question> parseQuestions(String filePath) throws IOException { //  Loads an XML document from filePath, extracts all <QuestionItem> elements, and converts them into a list of Question objects

        Document doc = loadDoc(filePath);
        NodeList nodes = doc.getElementsByTagName("QuestionItem");
        return getQuestions(nodes);
    }

    public Document loadDoc(String filePath) throws IOException { // Loads an XML file into a Document object using DocumentBuilderFactory and DocumentBuilder

        try {
            File file = new File(filePath);

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document doc = builder.parse(file);
            doc.getDocumentElement();

            return doc; 

        } catch (Exception e) {
            throw new IOException("Error parsing XML", e);
        }
    }

    public List<Question> getQuestions(NodeList nodes) { // Iterates through a NodeList of question elements, converting each to a Question object

        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i=i+1) {
            Element element = (Element) nodes.item(i);
            questions.add(parseQuestion(element));
        }

        return questions;
    }

    public Question parseQuestion(Element element) { // Extracts data from an element, including category, value, question text, options, and correct answer, to create a Question

        String category = getText(element, "Category");
        int value = Integer.parseInt(getText(element, "Value"));
        String questionText = getText(element, "QuestionText");

        Element options = (Element) element.getElementsByTagName("Options").item(0);

        String optionA = getText(options, "OptionA");
        String optionB = getText(options, "OptionB");
        String optionC = getText(options, "OptionC");
        String optionD = getText(options, "OptionD");

        String correctAnswer = getText(element, "CorrectAnswer");

        return new Question(category, value, questionText, optionA, optionB, optionC, optionD, correctAnswer);
    } 

    public String getText(Element element, String tagName) { // To get text content of a child element by tag name
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }
}
