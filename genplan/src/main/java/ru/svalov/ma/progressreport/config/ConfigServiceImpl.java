package ru.svalov.ma.progressreport.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

public class ConfigServiceImpl implements ConfigService {

    @Override
    public List<Board> getBoards(String xml) {
        if (xml == null) {
            throw new IllegalArgumentException("xml");
        }

        Trello trello = null;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader streamReader = factory.createXMLStreamReader(new StringReader(xml));
            JAXBContext contextB = JAXBContext.newInstance(Trello.class);
            Unmarshaller unmarshallerB = contextB.createUnmarshaller();
            JAXBElement<Trello> jaxbElementB = unmarshallerB.unmarshal(streamReader, Trello.class);
            trello = jaxbElementB.getValue();
        } catch (JAXBException | XMLStreamException e) {
            e.printStackTrace();
        }

        return Collections.unmodifiableList(trello.getBoards());
    }

}