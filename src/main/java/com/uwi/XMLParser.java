package com.uwi;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XMLParser implements QuestionParser {

    @Override
    public List<Question> parseQuestions(String filePath) throws IOException {
        Document document = loadXMLDocument(filePath);
        NodeList nodes = document.getElementsByTagName("QuestionItem");
        return extractQuestions(nodes);
    }

    private Document loadXMLDocument(String filePath) throws IOException {
        try {
            File file = new File(filePath);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            return document;

        } catch (Exception e) {
            throw new IOException("Error parsing XML", e);
        }
    }

    private List<Question> extractQuestions(NodeList nodes) {
        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Element qElem = (Element) nodes.item(i);
            questions.add(parseQuestion(qElem));
        }

        return questions;
    }

    private Question parseQuestion(Element qElem) {
        String category = getText(qElem, "Category");
        int value = Integer.parseInt(getText(qElem, "Value"));
        String questionText = getText(qElem, "QuestionText");

        Element optionsElem = (Element) qElem.getElementsByTagName("Options").item(0);

        String optionA = getText(optionsElem, "OptionA");
        String optionB = getText(optionsElem, "OptionB");
        String optionC = getText(optionsElem, "OptionC");
        String optionD = getText(optionsElem, "OptionD");

        String correctAnswer = getText(qElem, "CorrectAnswer");

        return new Question(
            category, value, questionText,
            optionA, optionB, optionC, optionD,
            correctAnswer
        );
    }

    private String getText(Element parent, String tagName) {
        return parent.getElementsByTagName(tagName).item(0).getTextContent();
    }
}
