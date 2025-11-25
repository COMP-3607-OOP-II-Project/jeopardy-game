package com.uwi;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XMLParser implements QuestionParser {

    @Override
    public List<Question> parseQuestions(String filePath) throws IOException {

        Document doc = loadDoc(filePath);
        NodeList nodes = doc.getElementsByTagName("QuestionItem");
        return getQuestions(nodes);
    }

    private Document loadDoc(String filePath) throws IOException {

        try {
            File file = new File(filePath);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(file);
            doc.getDocumentElement();
            //.normalize()

            return doc;

        } catch (Exception e) {
            throw new IOException("Error parsing XML", e);
        }
    }

    private List<Question> getQuestions(NodeList nodes) {

        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i=i+1) {
            Element element = (Element) nodes.item(i);
            questions.add(parseQuestion(element));
        }

        return questions;
    }

    private Question parseQuestion(Element element) {

        String category = getText(element, "Category");
        int value = Integer.parseInt(getText(element, "Value"));
        String questionText = getText(element, "QuestionText");

        Element options = (Element) element.getElementsByTagName("Options").item(0);

        String optionA = getText(options, "OptionA");
        String optionB = getText(options, "OptionB");
        String optionC = getText(options, "OptionC");
        String optionD = getText(options, "OptionD");

        String correctAnswer = getText(element, "CorrectAnswer");

        return new Question(
            category, value, questionText, optionA, optionB, optionC, optionD, correctAnswer);
    } 

    private String getText(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }
}
